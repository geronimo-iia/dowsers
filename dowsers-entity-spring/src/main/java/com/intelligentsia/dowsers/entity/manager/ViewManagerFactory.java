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

import java.util.List;

import org.springframework.beans.factory.FactoryBean;

import com.intelligentsia.dowsers.entity.view.View;
import com.intelligentsia.dowsers.entity.view.ViewManager;
import com.intelligentsia.dowsers.entity.view.ViewManagerControler;
import com.intelligentsia.dowsers.entity.view.ViewManagerControler.Behavior;

/**
 * ViewManagerFactory implements {@link FactoryBean} for {@link ViewManager}
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class ViewManagerFactory implements FactoryBean<ViewManager> {

	private Behavior behavior = Behavior.NO_FEED;
	private List<View> views;
	private EntityManager entityManager;
	private boolean asynch = true;

	@Override
	public ViewManager getObject() throws Exception {
		// build ViewManager
		ViewManager viewManager = new ViewManager(views, entityManager, asynch);
		// do init
		new ViewManagerControler(entityManager, viewManager, behavior).process();
		// ok retrun
		return viewManager;
	}

	@Override
	public Class<?> getObjectType() {
		return ViewManager.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public Behavior getBehavior() {
		return behavior;
	}

	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}

	public List<View> getViews() {
		return views;
	}

	public void setViews(List<View> views) {
		this.views = views;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public boolean isAsynch() {
		return asynch;
	}

	public void setAsynch(boolean asynch) {
		this.asynch = asynch;
	}

}
