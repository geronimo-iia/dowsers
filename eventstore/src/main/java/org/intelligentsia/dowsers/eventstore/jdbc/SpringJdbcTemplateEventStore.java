package org.intelligentsia.dowsers.eventstore.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.intelligentsia.cqrs.eventstore.ConcurrencyException;
import org.intelligentsia.cqrs.eventstore.EmptyResultException;
import org.intelligentsia.cqrs.eventstore.EventSerializer;
import org.intelligentsia.cqrs.eventstore.EventSink;
import org.intelligentsia.cqrs.eventstore.EventSource;
import org.intelligentsia.cqrs.eventstore.EventStore;
import org.intelligentsia.dowsers.event.definitiontore.StreamEverExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.google.common.annotations.VisibleForTesting;

public class SpringJdbcTemplateEventStore<E> implements EventStore<E> {

	private final SimpleJdbcTemplate jdbcTemplate;

	private final EventSerializer<E> eventSerializer;

	@Autowired
	public SpringJdbcTemplateEventStore(final SimpleJdbcTemplate jdbcTemplate, final EventSerializer<E> eventSerializer) {
		this.jdbcTemplate = jdbcTemplate;
		this.eventSerializer = eventSerializer;
	}

	@VisibleForTesting
	@PostConstruct
	void initialize() {
		jdbcTemplate.update("drop table event if exists");
		jdbcTemplate.update("drop table event_stream if exists");
		jdbcTemplate.update("create table event_stream(id varchar primary key, type varchar not null, version bigint not null, timestamp timestamp not null, next_event_sequence bigint not null)");
		jdbcTemplate.update("create table event(event_stream_id varchar not null, sequence_number bigint not null, version bigint not null, timestamp timestamp not null, data varchar not null, "
				+ "primary key (event_stream_id, sequence_number), foreign key (event_stream_id) references event_stream (id))");
	}

	@Override
	public void createEventStream(final UUID streamId, final EventSource<E> source) throws StreamEverExistsException {
		try {
			final long version = source.getVersion();
			final long timestamp = source.getTimestamp();
			final List<? extends E> events = source.getEvents();
			jdbcTemplate.update("insert into event_stream (id, type, version, timestamp, next_event_sequence) values (?, ?, ?, ?, ?)", streamId.toString(), source.getType(), version, new Date(timestamp), events.size());
			saveEvents(streamId, version, timestamp, 0, events);
		} catch (final DataAccessException e) {
			throw new StreamEverExistsException(streamId, e);
		}
	}

	@Override
	public void storeEventsIntoStream(final UUID streamId, final long expectedVersion, final EventSource<E> source) throws EmptyResultException, ConcurrencyException, IllegalArgumentException {
		final long version = source.getVersion();
		final long timestamp = source.getTimestamp();
		final List<? extends E> events = source.getEvents();

		final EventStream stream = getEventStream(streamId);
		final int count = jdbcTemplate.update("update event_stream set version = ?, timestamp = ?, next_event_sequence = ? where id = ? and version = ?", version, new Date(timestamp), stream.getNextEventSequence() + events.size(),
				streamId.toString(), expectedVersion);
		if (count != 1) {
			throw new ConcurrencyException(streamId, "actual: " + stream.getVersion() + "; expected: " + expectedVersion);
		}
		if (version < stream.getVersion()) {
			throw new IllegalArgumentException("version cannot decrease");
		}
		if (timestamp < stream.getTimestamp()) {
			throw new IllegalArgumentException("timestamp cannot decrease");
		}

		saveEvents(streamId, version, timestamp, stream.getNextEventSequence(), events);
	}

	@Override
	public void loadEventsFromLatestStreamVersion(final UUID streamId, final EventSink<E> sink) throws EmptyResultException {
		final EventStream stream = getEventStream(streamId);
		final List<StoredEvent<E>> storedEvents = loadEventsUptoVersion(stream, stream.getVersion());

		sendEventsToSink(stream, storedEvents, sink);
	}

	@Override
	public void loadEventsFromExpectedStreamVersion(final UUID streamId, final long expectedVersion, final EventSink<E> sink) throws EmptyResultException, ConcurrencyException {
		final EventStream stream = getEventStream(streamId);
		if (stream.getVersion() != expectedVersion) {
			throw new ConcurrencyException(streamId, "actual: " + stream.getVersion() + "; expected: " + expectedVersion);
		}
		final List<StoredEvent<E>> storedEvents = loadEventsUptoVersion(stream, stream.getVersion());

		sendEventsToSink(stream, storedEvents, sink);
	}

	@Override
	public void loadEventsFromStreamUptoVersion(final UUID streamId, final long version, final EventSink<E> sink) throws EmptyResultException {
		final EventStream stream = getEventStream(streamId);
		final List<StoredEvent<E>> storedEvents = loadEventsUptoVersion(stream, version);

		sendEventsToSink(stream, storedEvents, sink);
	}

	@Override
	public void loadEventsFromStreamUptoTimestamp(final UUID streamId, final long timestamp, final EventSink<E> sink) throws EmptyResultException {
		final EventStream stream = getEventStream(streamId);
		final List<StoredEvent<E>> storedEvents = loadEventsUptoTimestamp(stream, timestamp);

		sendEventsToSink(stream, storedEvents, sink);
	}

	private void saveEvents(final UUID streamId, final long version, final long timestamp, int nextEventSequence, final List<? extends E> events) {
		for (final E event : events) {
			jdbcTemplate.update("insert into event(event_stream_id, sequence_number, version, timestamp, data) values (?, ?, ?, ?, ?)", streamId.toString(), nextEventSequence++, version, new Date(timestamp), eventSerializer.serialize(event));
		}
	}

	private EventStream getEventStream(final UUID streamId) throws EmptyResultException {
		try {
			return jdbcTemplate.queryForObject("select type, version, timestamp, next_event_sequence from event_stream where id = ?", new EventStreamRowMapper(streamId), streamId.toString());
		} catch (final DataAccessException e) {
			throw new EmptyResultException(streamId, e);
		}
	}

	private List<StoredEvent<E>> loadEventsUptoVersion(final EventStream stream, final long version) {
		final List<StoredEvent<E>> storedEvents = jdbcTemplate.query("select version, timestamp, data from event where event_stream_id = ? and version <= ? order by sequence_number", new StoredEventRowMapper(), stream.getId().toString(), version);
		if (storedEvents.isEmpty()) {
			throw new EmptyResultException("no events found for stream " + stream.getId() + " for version " + version);
		}
		return storedEvents;
	}

	private List<StoredEvent<E>> loadEventsUptoTimestamp(final EventStream stream, final long timestamp) {
		final List<StoredEvent<E>> storedEvents = jdbcTemplate.query("select version, timestamp, data from event where event_stream_id = ? and timestamp <= ? order by sequence_number", new StoredEventRowMapper(), stream.getId().toString(), new Date(
				timestamp));
		if (storedEvents.isEmpty()) {
			throw new EmptyResultException("no events found for stream " + stream.getId() + " for timestamp " + timestamp);
		}
		return storedEvents;
	}

	private void sendEventsToSink(final EventStream stream, final List<StoredEvent<E>> storedEvents, final EventSink<E> sink) {
		final List<E> events = new ArrayList<E>(storedEvents.size());
		for (final StoredEvent<E> storedEvent : storedEvents) {
			events.add(storedEvent.getEvent());
		}
		final StoredEvent<E> lastEvent = storedEvents.get(storedEvents.size() - 1);

		sink.setType(stream.getType());
		sink.setVersion(lastEvent.getVersion());
		sink.setTimestamp(lastEvent.getTimestamp());
		sink.setEvents(events);
	}

	/**
	 * 
	 * EventStreamRowMapper.
	 * 
	 * 
	 * 
	 */
	private final class EventStreamRowMapper implements RowMapper<EventStream> {
		private final UUID streamId;

		private EventStreamRowMapper(final UUID streamId) {
			this.streamId = streamId;
		}

		@Override
		public EventStream mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			return new EventStream(streamId, rs.getString("type"), rs.getLong("version"), rs.getTimestamp("timestamp").getTime(), rs.getInt("next_event_sequence"));
		}
	}

	/**
	 * 
	 * StoredEventRowMapper.
	 * 
	 * 
	 */
	private final class StoredEventRowMapper implements RowMapper<StoredEvent<E>> {
		@Override
		public StoredEvent<E> mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			return new StoredEvent<E>(rs.getLong("version"), rs.getTimestamp("timestamp").getTime(), eventSerializer.deserialize(rs.getString("data")));
		}
	}

	/**
	 * 
	 * EventStream.
	 * 
	 * 
	 * 
	 */
	public static class EventStream {

		private final UUID id;
		private final String type;
		private final long version;
		private final long timestamp;
		private final int nextEventSequence;

		public EventStream(final UUID id, final String type, final long version, final long timestamp, final int nextEventSequence) {
			this.id = id;
			this.type = type;
			this.version = version;
			this.timestamp = timestamp;
			this.nextEventSequence = nextEventSequence;
		}

		public UUID getId() {
			return id;
		}

		public String getType() {
			return type;
		}

		public long getVersion() {
			return version;
		}

		public long getTimestamp() {
			return timestamp;
		}

		public int getNextEventSequence() {
			return nextEventSequence;
		}

	}

	/**
	 * 
	 * StoredEvent.
	 * 
	 * 
	 * 
	 * @param <E>
	 */
	public static class StoredEvent<E> {

		private final long version;
		private final long timestamp;
		private final E event;

		public StoredEvent(final long version, final long timestamp, final E event) {
			this.version = version;
			this.timestamp = timestamp;
			this.event = event;
		}

		public long getVersion() {
			return version;
		}

		public long getTimestamp() {
			return timestamp;
		}

		public E getEvent() {
			return event;
		}

	}

}
