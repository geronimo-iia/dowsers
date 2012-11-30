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
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.intelligentsia.dowsers.core.reflection.ClassInformation;
import org.intelligentsia.keystone.kernel.api.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.manager.EntityManager;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.reference.References;

/**
 * ViewManagerControler.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class ViewManagerControler {

	/**
	 * {@link EntityManager} instance.
	 */
	private final EntityManager entityManager;
	/**
	 * {@link ViewManager} instance.
	 */
	private final ViewManager viewManager;
	/**
	 * {@link Behavior} instance.
	 */
	private final Behavior behavior;

	/**
	 * Build a new instance of ViewManagerControler.
	 * 
	 * @param entityManager
	 * @param viewManager
	 * @param behavior
	 * @throws NullPointerException
	 *             if one of parameters is null
	 */
	public ViewManagerControler(final EntityManager entityManager, final ViewManager viewManager, final Behavior behavior) throws NullPointerException {
		super();
		this.entityManager = Preconditions.checkNotNull(entityManager);
		this.viewManager = Preconditions.checkNotNull(viewManager);
		this.behavior = Preconditions.checkNotNull(behavior);
	}

	/**
	 * Process all views according {@link Behavior}.
	 */
	public void process() {
		switch (behavior) {
		case BACKGROUND:
			// single daemon thread
			Executors.newSingleThreadExecutor(new ThreadFactory() {
				private final ThreadFactory delegate = Executors.defaultThreadFactory();

				@Override
				public Thread newThread(final Runnable runnable) {
					final Thread thread = delegate.newThread(runnable);
					thread.setDaemon(true);
					return thread;
				}
			}).execute(new ViewManagerFeed(entityManager, viewManager));
			break;
		case FOREGROUND:
			// wait for finish
			new ViewManagerFeed(entityManager, viewManager).run();
			break;
		default:
			// do nothing
			break;
		}

	}

	/**
	 * Behavior.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 */
	public static enum Behavior {
		NO_FEED, BACKGROUND, FOREGROUND
	}

	/**
	 * ViewManagerFeed implements feed process (Runnable).
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
	 */
	private static class ViewManagerFeed implements Runnable {

		/**
		 * Logger on ViewManagerControler.
		 */
		private final Logger logger = LoggerFactory.getLogger(ViewManagerControler.class);

		private final EntityManager entityManager;

		private final ViewManager viewManager;

		public ViewManagerFeed(final EntityManager entityManager, final ViewManager viewManager) throws NullPointerException {
			super();
			this.entityManager = Preconditions.checkNotNull(entityManager);
			this.viewManager = Preconditions.checkNotNull(viewManager);
		}

		@Override
		public void run() {
			logger.info("ViewManagerControler start");
			final Collection<Reference> references = viewManager.getEntities();
			int processed = 0;
			for (final Reference reference : references) {
				try {
					logger.info(StringUtils.format("processing %", reference));
					process(reference);
					processed++;
					logger.info(StringUtils.format("processed %s on %s", processed, references.size()));
				} catch (final Throwable throwable) {
					logger.error("ViewManagerControler feed error '{}'", reference, throwable);
				}
			}
			logger.info("ViewManagerControler done");
		}

		private void process(final Reference reference) {
			// clean up view store
			viewManager.drop(reference);
			// loading information
			final String entityClassName = reference.getEntityClassName();
			final ClassInformation classInformation = ClassInformation.parse(entityClassName);
			// loading collection
			final Iterable<Reference> iterable = entityManager.find(classInformation.getType());
			for (final Reference ref : iterable) {
				try {
					final Entity entity = References.discover(entityManager.find(ref));
					viewManager.update(entity);
				} catch (final Throwable throwable) {
					logger.error(StringUtils.format("ViewManagerControler feed error '%s': %s", ref, throwable.getMessage()), throwable);
				}
			}
		}
	}

}
