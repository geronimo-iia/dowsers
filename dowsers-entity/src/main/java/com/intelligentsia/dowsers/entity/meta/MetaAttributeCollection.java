package com.intelligentsia.dowsers.entity.meta;

import java.io.Serializable;
import java.util.Iterator;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * MetaAttributeCollection is a {@link ImmutableCollection} of
 * {@link MetaAttribute}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaAttributeCollection implements Iterable<MetaAttribute>, Serializable {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -4410480905712842651L;

	/**
	 * {@link ImmutableCollection} {@link MetaAttribute}.
	 */
	private final ImmutableCollection<MetaAttribute> metaAttributes;

	/**
	 * Build a new instance of <code>MetaAttributeCollection</code>. For
	 * serialization purpose.
	 */
	@SuppressWarnings("unused")
	private MetaAttributeCollection() {
		super();
		metaAttributes = ImmutableSet.of();
	}

	/**
	 * Build a new instance of <code>MetaAttributeCollection</code>.
	 * 
	 * @param metaAttributes
	 */
	public MetaAttributeCollection(final ImmutableCollection<MetaAttribute> metaAttributes) {
		super();
		this.metaAttributes = Preconditions.checkNotNull(metaAttributes);
	}

	@Override
	public Iterator<MetaAttribute> iterator() {
		return metaAttributes.iterator();
	}

	public ImmutableCollection<MetaAttribute> getMetaAttributes() {
		return metaAttributes;
	}

	public boolean contains(final Object object) {
		return metaAttributes.contains(object);
	}

	public ImmutableList<MetaAttribute> asList() {
		return metaAttributes.asList();
	}

	public boolean isEmpty() {
		return metaAttributes.isEmpty();
	}

	public int size() {
		return metaAttributes.size();
	}

}
