package org.intelligentsia.dowsers.entity;

import org.intelligentsia.dowsers.entity.meta.MetaEntityContext;

public class EntityBeanSupport extends EntitySupport {

	public EntityBeanSupport(final String identity, final MetaEntityContext metaEntityContext) throws NullPointerException {
		super(identity, metaEntityContext);
	}

	@Override
	public <Value> Value attribute(final String name) throws NullPointerException {
		return null;
	}

	@Override
	public <Value> Entity attribute(final String name, final Value value) throws NullPointerException {
		return this;
	}

}
