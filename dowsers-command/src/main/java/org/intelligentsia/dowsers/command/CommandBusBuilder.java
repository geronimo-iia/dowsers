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
package org.intelligentsia.dowsers.command;

import java.util.UUID;
import java.util.concurrent.Executors;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

/**
 * CommandBusBuilder act as a builder for CommandBus.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum CommandBusBuilder {
	;

	/**
	 * @return a new synchone CommandBus instance without history.
	 */
	public static CommandBus newCommandBus() {
		return new CommandBus(new EventBus(UUID.randomUUID().toString()));
	}

	/**
	 * @param commandHistory
	 *            history instance to use.
	 * @return a new synchone CommandBus instance with history.
	 */
	public static CommandBus newCommandBus(final CommandHistory commandHistory) {
		return new CommandBus(new EventBus(UUID.randomUUID().toString()), commandHistory);
	}

	/**
	 * @return a new asynchone CommandBus instance without history (@see
	 *         {@link Executors#newCachedThreadPool}).
	 */
	public static CommandBus newAsyncCommandBus() {
		return new CommandBus(new AsyncEventBus(UUID.randomUUID().toString(), Executors.newCachedThreadPool()));
	}

	/**
	 * @param commandHistory
	 *            history instance to use.
	 * @return a new asynchone CommandBus instance with history (@see
	 *         {@link Executors#newCachedThreadPool}).
	 */
	public static CommandBus newAsyncCommandBus(final CommandHistory commandHistory) {
		return new CommandBus(new AsyncEventBus(UUID.randomUUID().toString(), Executors.newCachedThreadPool()), commandHistory);
	}
}
