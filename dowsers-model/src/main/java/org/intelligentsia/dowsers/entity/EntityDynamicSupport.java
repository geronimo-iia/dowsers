package org.intelligentsia.dowsers.entity;

import java.util.Iterator;
import java.util.Map;

import org.intelligentsia.dowsers.entity.meta.MetaAttribute;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContext;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * EntityDynamicSupport extends {@link EntitySupport} and implements an
 * {@link Entity} with a dynamic set of Attribute.
 * 
 * * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityDynamicSupport extends EntitySupport {

	/**
	 * Map of attributes.
	 */
	private final Map<String, Object> attributes;

	/**
	 * Build a new instance of EntityDynamicSupport.java.
	 * 
	 * @param identity
	 * @param metaEntityContext
	 * @throws NullPointerException
	 */
	public EntityDynamicSupport(final String identity, final MetaEntityContext metaEntityContext) throws NullPointerException {
		super(identity, metaEntityContext);
		attributes = Maps.newHashMap();
		final Iterator<String> iterator = metaEntityContext.getMetaAttributeNames();
		while (iterator.hasNext()) {
			final MetaAttribute attribute = metaEntityContext.getMetaAttribute(iterator.next());
			// TODO clone default value
			attributes.put(attribute.getName(), attribute.getDefaultValue());
		}
	}

	/**
	 * Build a new instance of BaseEntity.java.
	 * 
	 * @param identity
	 *            entity's identity.
	 * @param metaEntityContext
	 *            {@link MetaEntityContext} associated with this instance.
	 * @param attributes
	 *            Map of attributes
	 * @throws NullPointerException
	 *             if one of parameter is null
	 */
	public EntityDynamicSupport(final String identity, final MetaEntityContext metaEntityContext, final Map<String, Object> attributes) throws NullPointerException {
		super(identity, metaEntityContext);
		this.attributes = Preconditions.checkNotNull(attributes);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Value> Value attribute(final String name) throws NullPointerException {
		return (Value) attributes.get(Preconditions.checkNotNull(name));
	}

	@Override
	public <Value> Entity attribute(final String name, final Value value) throws NullPointerException {
		attributes.put(Preconditions.checkNotNull(name), value);
		return this;
	}
}
