/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at
 
         http://www.apache.org/licenses/LICENSE-2.0
 
       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */
package org.intelligentsia.dowsers.command;

/**
 * CommandHandlerRegistry register command handlers instance.
 * 
 * A command handler subscribe to a particular command like this:
 * 
 * <pre>
 * // handler for CreateAddressBookCommand request
 * &#064;Subscribe
 * public void onCreateAddressBookCommand(CreateAddressBookCommand command) {
 * 	// do something
 * }
 * </pre>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface CommandHandlerRegistry {

	/**
	 * Register a new command handler instance.
	 * 
	 * @param commandHandler
	 *            command handler instance
	 * @throws NullPointerException
	 *             if commandHandler is null
	 */
	public void register(final Object commandHandler) throws NullPointerException;
}
