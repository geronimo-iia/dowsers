/**
 *        Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 *
 */
package org.intelligentsia.dowsers.eventstore;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.intelligentsia.dowsers.eventstore.EmptyResultException;
import org.intelligentsia.dowsers.eventtore.StreamEverExistsException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractEventStoreTest {

	private static final UUID ID_1 = UUID.randomUUID();
	private static final UUID ID_2 = UUID.randomUUID();

	private static final long T1 = 1000;
	private static final long T2 = 2000;

	private EventStore<String> subject;

	protected abstract EventStore<String> createSubject();

	@Before
	public void setUp() {
		subject = createSubject();
	}

	@Test
	public void should_create_event_stream_with_initial_version_and_events() {
		final List<String> events = Arrays.asList("foo", "bar");
		final FakeEventSource source = new FakeEventSource("type", 0, AbstractEventStoreTest.T1, events);
		final FakeEventSink sink = new FakeEventSink("type", 0, AbstractEventStoreTest.T1, events);

		subject.createEventStream(AbstractEventStoreTest.ID_1, source);
		subject.loadEventsFromLatestStreamVersion(AbstractEventStoreTest.ID_1, sink);

		sink.verify();
	}

	@Test
	public void should_fail_to_create_stream_with_duplicate_id() {
		subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 0, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar")));
		try {
			subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 0, AbstractEventStoreTest.T2, Arrays.asList("baz")));
			Assert.fail("StreamEverExistsException expected");
		} catch (final StreamEverExistsException expected) {
		}
	}

	@Test
	public void should_store_events_into_stream() {
		subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 0, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar")));
		subject.storeEventsIntoStream(AbstractEventStoreTest.ID_1, 0, new FakeEventSource("type", 1, AbstractEventStoreTest.T2, Arrays.asList("baz")));

		final FakeEventSink sink = new FakeEventSink("type", 1, AbstractEventStoreTest.T2, Arrays.asList("foo", "bar", "baz"));
		subject.loadEventsFromLatestStreamVersion(AbstractEventStoreTest.ID_1, sink);

		sink.verify();
	}

	@Test
	public void should_load_events_from_specific_stream_version() {
		subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 0, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar")));
		subject.storeEventsIntoStream(AbstractEventStoreTest.ID_1, 0, new FakeEventSource("type", 1, AbstractEventStoreTest.T2, Arrays.asList("baz")));

		final FakeEventSink sink = new FakeEventSink("type", 1, AbstractEventStoreTest.T2, Arrays.asList("foo", "bar", "baz"));
		subject.loadEventsFromExpectedStreamVersion(AbstractEventStoreTest.ID_1, 1, sink);

		sink.verify();
	}

	@Test
	public void should_fail_to_load_events_from_specific_stream_version_when_expected_version_does_not_match_actual_version() {
		subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 0, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar")));
		subject.storeEventsIntoStream(AbstractEventStoreTest.ID_1, 0, new FakeEventSource("type", 1, AbstractEventStoreTest.T2, Arrays.asList("baz")));

		try {
			subject.loadEventsFromExpectedStreamVersion(AbstractEventStoreTest.ID_1, 0, new FakeEventSink("type", 1, AbstractEventStoreTest.T2, Arrays.asList("foo", "bar", "baz")));
			Assert.fail("ConcurrencyException expected");
		} catch (final ConcurrencyException expected) {
		}
	}

	@Test
	public void should_store_separate_event_logs_for_different_event_streams() {
		subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 0, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar")));
		subject.createEventStream(AbstractEventStoreTest.ID_2, new FakeEventSource("type", 0, AbstractEventStoreTest.T2, Arrays.asList("baz")));

		final FakeEventSink sink1 = new FakeEventSink("type", 0, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar"));
		subject.loadEventsFromExpectedStreamVersion(AbstractEventStoreTest.ID_1, 0, sink1);
		final FakeEventSink sink2 = new FakeEventSink("type", 0, AbstractEventStoreTest.T2, Arrays.asList("baz"));
		subject.loadEventsFromExpectedStreamVersion(AbstractEventStoreTest.ID_2, 0, sink2);

		sink1.verify();
		sink2.verify();
	}

	@Test
	public void should_fail_to_store_events_into_stream_when_versions_do_not_match() {
		subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 1, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar")));
		try {
			subject.storeEventsIntoStream(AbstractEventStoreTest.ID_1, 0, new FakeEventSource("type", 1, AbstractEventStoreTest.T2, Arrays.asList("baz")));
			Assert.fail("ConcurrencyException expected");
		} catch (final ConcurrencyException expected) {
		}
	}

	@Test
	public void should_check_optimistic_locking_error_before_decreasing_version_or_timestamp() {
		subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 5, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar")));
		try {
			subject.storeEventsIntoStream(AbstractEventStoreTest.ID_1, 4, new FakeEventSource("type", 3, AbstractEventStoreTest.T2, Arrays.asList("baz")));
			Assert.fail("ConcurrencyException expected");
		} catch (final ConcurrencyException expected) {
		}
	}

	@Test
	public void should_fail_to_store_events_into_stream_when_new_version_is_before_previous_version() {
		subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 5, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar")));
		try {
			subject.storeEventsIntoStream(AbstractEventStoreTest.ID_1, 5, new FakeEventSource("type", 4, AbstractEventStoreTest.T2, Arrays.asList("baz")));
			Assert.fail("IllegalArgumentException expected");
		} catch (final IllegalArgumentException expected) {
		}
	}

	@Test
	public void should_fail_to_store_events_into_stream_when_new_timestamp_is_before_previous_timestamp() {
		subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 0, AbstractEventStoreTest.T2, Arrays.asList("foo", "bar")));
		try {
			subject.storeEventsIntoStream(AbstractEventStoreTest.ID_1, 0, new FakeEventSource("type", 1, AbstractEventStoreTest.T1, Arrays.asList("baz")));
			Assert.fail("IllegalArgumentException expected");
		} catch (final IllegalArgumentException expected) {
		}
	}

	@Test
	public void should_fail_to_load_events_when_event_stream_version_does_not_match() {
		subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 0, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar")));
		subject.storeEventsIntoStream(AbstractEventStoreTest.ID_1, 0, new FakeEventSource("type", 1, AbstractEventStoreTest.T2, Arrays.asList("baz")));

		try {
			subject.loadEventsFromExpectedStreamVersion(AbstractEventStoreTest.ID_1, 0, new FakeEventSink("type", 1, AbstractEventStoreTest.T2, Arrays.asList("foo", "bar", "baz")));
			Assert.fail("ConcurrencyException expected");
		} catch (final ConcurrencyException expected) {
		}

	}

	@Test
	public void should_fail_to_store_events_into_non_existing_event_stream() {
		try {
			subject.storeEventsIntoStream(AbstractEventStoreTest.ID_1, 0, new FakeEventSource("type", 0, AbstractEventStoreTest.T1, Arrays.asList("foo")));
			Assert.fail("EmptyResultException expected");
		} catch (final EmptyResultException expected) {
		}
	}

	@Test
	public void should_fail_to_load_events_from_non_existing_event_stream() {
		try {
			subject.loadEventsFromExpectedStreamVersion(AbstractEventStoreTest.ID_1, 0, new FakeEventSink("type", 0, AbstractEventStoreTest.T1, Arrays.asList("foo")));
			Assert.fail("EmptyResultException expected");
		} catch (final EmptyResultException expected) {
		}
	}

	@Test
	public void should_load_events_from_stream_at_specific_version() {
		subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 0, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar")));
		subject.storeEventsIntoStream(AbstractEventStoreTest.ID_1, 0, new FakeEventSource("type", 1, AbstractEventStoreTest.T2, Arrays.asList("baz")));

		final FakeEventSink sink = new FakeEventSink("type", 0, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar"));
		subject.loadEventsFromStreamUptoVersion(AbstractEventStoreTest.ID_1, 0, sink);

		sink.verify();
	}

	@Test
	public void should_load_all_events_from_stream_when_specified_version_is_higher_than_actual_version() {
		subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 0, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar")));
		subject.storeEventsIntoStream(AbstractEventStoreTest.ID_1, 0, new FakeEventSource("type", 1, AbstractEventStoreTest.T2, Arrays.asList("baz")));

		final FakeEventSink sink = new FakeEventSink("type", 1, AbstractEventStoreTest.T2, Arrays.asList("foo", "bar", "baz"));
		subject.loadEventsFromStreamUptoVersion(AbstractEventStoreTest.ID_1, 3, sink);

		sink.verify();
	}

	@Test
	public void should_fail_to_load_events_from_stream_when_requested_version_is_before_first_event_version() {
		subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 2, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar")));
		try {
			subject.loadEventsFromStreamUptoVersion(AbstractEventStoreTest.ID_1, 1, new FakeEventSink("type", 0, AbstractEventStoreTest.T2, Arrays.asList("foo", "bar")));
			Assert.fail("EmptyResultException expected");
		} catch (final EmptyResultException expected) {
		}
	}

	@Test
	public void should_load_events_from_stream_at_specific_timestamp() {
		final long t = AbstractEventStoreTest.T1 + 250;

		subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 0, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar")));
		subject.storeEventsIntoStream(AbstractEventStoreTest.ID_1, 0, new FakeEventSource("type", 1, AbstractEventStoreTest.T2, Arrays.asList("baz")));

		final FakeEventSink sink = new FakeEventSink("type", 0, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar"));
		subject.loadEventsFromStreamUptoTimestamp(AbstractEventStoreTest.ID_1, t, sink);

		sink.verify();
	}

	@Test
	public void should_fail_to_load_events_from_stream_when_request_timestamp_is_before_first_event_timestamp() {
		subject.createEventStream(AbstractEventStoreTest.ID_1, new FakeEventSource("type", 0, AbstractEventStoreTest.T2, Arrays.asList("foo", "bar")));
		try {
			subject.loadEventsFromStreamUptoTimestamp(AbstractEventStoreTest.ID_1, AbstractEventStoreTest.T1, new FakeEventSink("type", 0, AbstractEventStoreTest.T1, Arrays.asList("foo", "bar")));
			Assert.fail("EmptyResultException expected");
		} catch (final EmptyResultException expected) {
		}
	}

	public static class FakeEventSource implements EventSource<String> {

		private final String type;
		private final long version;
		private final long timestamp;
		private final List<String> events;

		public FakeEventSource(final String type, final long version, final long timestamp, final List<String> events) {
			this.type = type;
			this.version = version;
			this.timestamp = timestamp;
			this.events = events;
		}

		@Override
		public String getType() {
			return type;
		}

		@Override
		public long getVersion() {
			return version;
		}

		@Override
		public long getTimestamp() {
			return timestamp;
		}

		@Override
		public List<? extends String> getEvents() {
			return events;
		}

	}

	public static final class FakeEventSink implements EventSink<String> {
		private final String expectedType;
		private final long expectedVersion;
		private final long expectedTimestamp;
		private final List<String> expectedEvents;
		private String actualType;
		private long actualVersion = -1;
		private long actualTimestamp = -1;
		private Iterable<? extends String> actualEvents;

		public FakeEventSink(final String expectedType, final long expectedVersion, final long expectedTimestamp, final List<String> expectedEvents) {
			this.expectedType = expectedType;
			this.expectedVersion = expectedVersion;
			this.expectedTimestamp = expectedTimestamp;
			this.expectedEvents = expectedEvents;
		}

		@Override
		public void setType(final String actualType) {
			this.actualType = actualType;
		}

		@Override
		public void setVersion(final long actualVersion) {
			Assert.assertNotNull("type must be set before version", actualType);
			this.actualVersion = actualVersion;
		}

		@Override
		public void setTimestamp(final long actualTimestamp) {
			Assert.assertNotNull("type must be set before version", actualType);
			this.actualTimestamp = actualTimestamp;
		}

		@Override
		public void setEvents(final Iterable<? extends String> actualEvents) {
			Assert.assertNotNull("type must be set before events", actualType);
			this.actualEvents = actualEvents;
		}

		public void verify() {
			Assert.assertEquals(expectedType, actualType);
			Assert.assertEquals(expectedVersion, actualVersion);
			Assert.assertEquals(expectedTimestamp, actualTimestamp);
			Assert.assertEquals(expectedEvents, actualEvents);
		}

	}

}
