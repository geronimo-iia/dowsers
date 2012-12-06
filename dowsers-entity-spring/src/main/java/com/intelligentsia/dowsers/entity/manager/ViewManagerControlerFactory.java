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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;

import com.intelligentsia.dowsers.entity.view.ViewManager;
import com.intelligentsia.dowsers.entity.view.ViewManagerControler;
import com.intelligentsia.dowsers.entity.view.ViewManagerControler.Behavior;

public class ViewManagerControlerFactory implements FactoryBean<ViewManagerControler>, BeanFactoryAware {

	private BeanFactory beanFactory;

	private Behavior behavior = Behavior.NO_FEED;
	private ViewManager viewManager;

	private EntityManager entityManager;

	@Override
	public ViewManagerControler getObject() throws Exception {
		if (entityManager == null) {
			entityManager = beanFactory.getBean(EntityManager.class);
		}
		if (viewManager == null) {
			viewManager = beanFactory.getBean(ViewManager.class);
		}
		return new ViewManagerControler(entityManager, viewManager, behavior);
	}

	@Override
	public Class<?> getObjectType() {
		return ViewManagerControler.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Override
	public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public Behavior getBehavior() {
		return behavior;
	}

	public void setBehavior(final Behavior behavior) {
		this.behavior = behavior;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ViewManager getViewManager() {
		return viewManager;
	}

	public void setViewManager(final ViewManager viewManager) {
		this.viewManager = viewManager;
	}

}
