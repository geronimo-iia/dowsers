package org.intelligentsia.dowsers.model;

import org.intelligentsia.dowsers.model.meta.MetaEntityContext;

public class EntityBeanSupport extends EntitySupport {

	public EntityBeanSupport(String identity, MetaEntityContext metaEntityContext) throws NullPointerException {
		super(identity, metaEntityContext);
	}

	@Override
	public <Value> Value attribute(String name) throws NullPointerException {
		return null;
	}

	@Override
	public <Value> Entity attribute(String name, Value value) throws NullPointerException {
		return this;
	}

}
