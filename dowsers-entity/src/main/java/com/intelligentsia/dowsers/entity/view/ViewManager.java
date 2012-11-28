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
package com.intelligentsia.dowsers.entity.view;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.manager.EntityManager;
import com.intelligentsia.dowsers.entity.manager.EntityManagerListener;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;

/**
 * ViewManager.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class ViewManager {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Multimap<Reference, View> views;

	/**
	 * Build a new instance of ViewManager. If a {@link EntityManager} instance
	 * is provided a listener will be added.
	 * 
	 * @param collection
	 *            collection of view
	 * @param entityManager
	 *            {@link EntityManager} instance to listen
	 * @param asynch
	 *            if true all event will be processed asynchronously
	 * @throws NullPointerException
	 *             if collection is null
	 */
	public ViewManager(final Collection<View> collection, final EntityManager entityManager, final boolean asynch) throws NullPointerException {
		super();
		Preconditions.checkNotNull(collection);
		views = ArrayListMultimap.create();
		for (final View view : collection) {
			if (!view.entities().isEmpty()) {
				views.put(view.entities().get(0), view);
			}
		}
		// register listener
		if (entityManager != null) {
			if (asynch) {
				entityManager.addListener(new EntityManagerListener(new ViewManagerListener(this)));
			} else {
				entityManager.addListener(new ViewManagerListener(this));
			}
		}
	}

	protected <T extends Entity> void entityStored(final T entity) {
		for (final View view : views.get(entity.identity())) {
			try {
				view.compute(entity);
			} catch (final Throwable throwable) {
				if (logger.isWarnEnabled()) {
					logger.warn(new StringBuilder("Compute view '").append(view.name()).append("', entity '").append(entity.identity()).append("': ").append(throwable.getMessage()).toString(), throwable);
				}
			}
		}
	}

	protected <T extends Entity> void entityRemoved(final T entity) {
		final Reference id = entity.identity();
		for (final View view : views.get(id)) {
			try {
				view.remove(id);
			} catch (final Throwable throwable) {
				if (logger.isWarnEnabled()) {
					logger.warn(new StringBuilder("Remove view '").append(view.name()).append("', entity '").append(entity.identity()).append("': ").append(throwable.getMessage()).toString(), throwable);
				}
			}
		}
	}

	/**
	 * ViewManagerListener.
	 */
	public static class ViewManagerListener implements EntityManager.Listener {
		/**
		 * {@link ViewManager} instance.
		 */
		private final ViewManager manager;

		/**
		 * Build a new instance of ViewManager.
		 * 
		 * @param manager
		 * @throws NullPointerException
		 *             if manager is null
		 */
		public ViewManagerListener(final ViewManager manager) throws NullPointerException {
			super();
			this.manager = Preconditions.checkNotNull(manager);
		}

		@Override
		public <T> void entityInstantiated(final T entity) {
			// do nothing
		}

		@Override
		public <T> void entityFinded(final T entity) {
			// do nothing
		}

		@Override
		public <T> void entityStored(final T entity) {
			manager.entityStored(References.discover(entity));
		}

		@Override
		public <T> void entityRemoved(final T entity) {
			manager.entityRemoved(References.discover(entity));
		}
	}

}
