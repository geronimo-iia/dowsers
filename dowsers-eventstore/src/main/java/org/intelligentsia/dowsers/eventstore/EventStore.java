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
/**
 * 
 */
package org.intelligentsia.dowsers.eventstore;

import java.util.Collection;

import org.intelligentsia.dowsers.domain.ConcurrencyException;

/**
 * EventStore stores and reads ordered streams of events.
 * 
 * <EventType> base type of event.
 * 
 * Previously design of this service use Sink and Source abstraction. We decide
 * to remove them for thoses reason:
 * <ul>
 * <li>Methods behaviour is more understandable</li>
 * <li>Source and Sink did not preserve inner modification</li>
 * <li>Source and Sink force creation of small intermediate objets that methods
 * declaration didn't</li>
 * </ul>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EventStore<EventType> {

	/**
	 * Creates a new event stream. The stream is initialized with the data and
	 * events provided by source.
	 * 
	 * @param identity
	 *            the stream id of the stream to create.
	 * @param initialVersion
	 *            initial stream Version
	 * @param name
	 *            name of this stream (could be a class name)
	 * @param initialEvents
	 *            initial events.
	 * @throws StreamEverExistsException
	 *             a stream with the specified id already exists.
	 */
	public void create(String identity, long initialVersion, String name, Collection<EventType> initialEvents) throws StreamEverExistsException;

	/**
	 * Adds the events from source to the specified stream.
	 * 
	 * @param identity
	 *            stream identity.
	 * @param expectedVersion
	 *            expected version of stream
	 * @param events
	 *            events to store
	 * @throws EmptyResultException
	 *             if the specified stream does not exist.
	 * @throws ConcurrencyException
	 *             thrown when the expected version does not match the actual
	 *             version of the stream.
	 * @throws IllegalArgumentException
	 *             if expectedVersion is strictly lower or equal than current in
	 *             store
	 * @return final version of this stream
	 */
	public long store(String identity, final long expectedVersion, Collection<EventType> events) throws EmptyResultException, ConcurrencyException, IllegalArgumentException;

	/**
	 * Loads the events associated with the stream into the provided sink.
	 * 
	 * @param identity
	 *            the stream identity
	 * @param events
	 *            collection of events to send event to.
	 * @throws EmptyResultException
	 *             if the specified stream does not exist.
	 * @return version of this stream
	 */
	public long loadFromLatestVersion(String identity, Collection<EventType> events) throws EmptyResultException;

	/**
	 * Determine current version of specified events stream.
	 * 
	 * @param identity
	 *            the stream identity
	 * @return version of specified stream
	 * @throws EmptyResultException
	 *             if the specified stream does not exist.
	 */
	public long currentVersion(String identity) throws EmptyResultException;

	/**
	 * Loads the events associated with the stream into the provided sink. Only
	 * the events that existed before and at the requested version are loaded.
	 * 
	 * @param identity
	 *            the stream identity
	 * @param version
	 *            the version (inclusive) of the event stream to load.
	 * @param events
	 *            collection of events to send event to.
	 * @throws EmptyResultException
	 *             no stream with the specified id exists or the version is
	 *             lower than the initial version of the stream.
	 */
	public void loadUptoVersion(String identity, long version, Collection<EventType> events) throws EmptyResultException;
	
	/**
	 * Loads the events associated with the stream into the provided sink. Only
	 * the events that existed after the requested version are loaded.
	 * 
	 * @param identity
	 *            the stream identity
	 * @param version
	 *            the version (exclusive) of the event stream to load.
	 * @param events
	 *            collection of events to send event to.
	 * @throws EmptyResultException
	 *             no stream with the specified id exists or the version is
	 *             lower than the initial version of the stream.
	 */
	public void loadFromVersion(String identity, long version, Collection<EventType> events) throws EmptyResultException;
	

}