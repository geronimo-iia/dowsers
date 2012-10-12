package org.intelligentsia.dowsers.entity;

import org.intelligentsia.dowsers.entity.meta.MetaEntityContext;

/**
 * EntityBeanSupport extends {@link EntitySupport}. This is teh base class for
 * all POJO {@link Entity}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityBeanSupport extends EntitySupport {

	public EntityBeanSupport(final String identity, final MetaEntityContext metaEntityContext) throws NullPointerException {
		super(identity, metaEntityContext);
	}

	@Override
	public <Value> Value attribute(final String name) throws NullPointerException {
		// TODO implements this
		return null;
	}

	@Override
	public <Value> Entity attribute(final String name, final Value value) throws NullPointerException {
		// TODO implements this
		return this;
	}

}
