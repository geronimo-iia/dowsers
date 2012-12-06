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

	private final Multimap<Reference, View> views = ArrayListMultimap.create();

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
	 */
	public ViewManager(final Collection<View> collection, final EntityManager entityManager, final boolean asynch) {
		super();
		if (collection != null) {
			for (final View view : collection) {
				add(view);
			}
		}
		if (entityManager != null) {
			register(entityManager, asynch);
		}
	}

	/**
	 * Build a new instance of ViewManager.
	 * 
	 * @param collection
	 */
	public ViewManager(final Collection<View> collection) {
		this(collection, null, false);
	}

	/**
	 * Build a new instance of ViewManager.
	 */
	public ViewManager() {
		this(null);
	}

	/**
	 * @param reference
	 * @return a Collection<View> for specified reference.
	 */
	public Collection<View> getViews(final Reference reference) {
		return views.get(reference);
	}

	/**
	 * @return a {@link Collection} of {@link Reference} which used to build
	 *         each {@link View}.
	 */
	public Collection<Reference> getEntities() {
		return views.keySet();
	}

	/**
	 * Add a view onto this {@link ViewManager}.
	 * 
	 * @param view
	 * @throws NullPointerException
	 */
	public void add(final View view) throws NullPointerException {
		Preconditions.checkNotNull(view);
		if (!view.entities().isEmpty()) {
			views.put(view.entities().get(0), view);
		}
	}

	/**
	 * Register this {@link ViewManager} instance onto specified
	 * {@link EntityManager}.
	 * 
	 * @param entityManager
	 * @param asynch
	 *            if true view process will be asynchrony
	 * @throws NullPointerException
	 *             if entityManager is null
	 */
	public void register(final EntityManager entityManager, final boolean asynch) throws NullPointerException {
		Preconditions.checkNotNull(entityManager);
		// register listener
		if (asynch) {
			entityManager.addListener(new EntityManagerListener(new ViewManagerListener(this)));
		} else {
			entityManager.addListener(new ViewManagerListener(this));
		}
	}

	/**
	 * Dropped by {@link ViewManagerControler}.
	 * 
	 * @param entityClassReference
	 */
	void drop(final Reference entityClassReference) {
		for (final View view : views.get(entityClassReference)) {
			view.viewStore().drop();
		}
	}

	/**
	 * Called by {@link ViewManagerControler}.
	 * 
	 * @param entity
	 */
	<T extends Entity> void update(final T entity) {
		entityStored(entity);
	}

	protected <T extends Entity> void entityStored(final T entity) {
		final Reference id = entity.identity().getEntityClassReference();
		for (final View view : views.get(id)) {
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
		final Reference id = entity.identity().getEntityClassReference();
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
