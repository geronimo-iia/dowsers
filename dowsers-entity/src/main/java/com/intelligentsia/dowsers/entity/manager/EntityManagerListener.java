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
package com.intelligentsia.dowsers.entity.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.google.common.base.Preconditions;
import com.intelligentsia.dowsers.entity.manager.EntityManager.Listener;

/**
 * EntityManagerListener implement an asynchrony {@link Listener}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class EntityManagerListener implements EntityManager.Listener {

	/**
	 * The ExecutorService used to handle event delivery to the event handlers.
	 */
	private final ExecutorService executorService;

	/**
	 * The {@link Listener} instance.
	 */
	private final EntityManager.Listener listener;

	/**
	 * Default constructor sets up the executorService property to use the
	 * {@link Executors#newCachedThreadPool()} implementation. The configured
	 * ExecutorService will have a custom ThreadFactory such that the threads
	 * returned will be daemon threads (and thus not block the application from
	 * shutting down).
	 */
	public EntityManagerListener(final EntityManager.Listener listener) {
		this(Executors.newCachedThreadPool(new ThreadFactory() {
			private final ThreadFactory delegate = Executors.defaultThreadFactory();

			public Thread newThread(final Runnable runnable) {
				final Thread thread = delegate.newThread(runnable);
				thread.setDaemon(true);
				return thread;
			}
		}), listener);
	}

	/**
	 * Build a new instance of EntityManagerListener.java.
	 * 
	 * @param executorService
	 * @param listener
	 * @throws NullPointerException
	 */
	public EntityManagerListener(final ExecutorService executorService, final EntityManager.Listener listener) throws NullPointerException {
		super();
		this.executorService = Preconditions.checkNotNull(executorService);
		this.listener = Preconditions.checkNotNull(listener);
	}

	@Override
	public <T> void entityInstantiated(final T entity) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				listener.entityInstantiated(entity);
			}
		});
	}

	@Override
	public <T> void entityFinded(final T entity) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				listener.entityFinded(entity);
			}
		});
	}

	@Override
	public <T> void entityStored(final T entity) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				listener.entityStored(entity);
			}
		});
	}

	@Override
	public <T> void entityRemoved(final T entity) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				listener.entityRemoved(entity);
			}
		});
	}

}
