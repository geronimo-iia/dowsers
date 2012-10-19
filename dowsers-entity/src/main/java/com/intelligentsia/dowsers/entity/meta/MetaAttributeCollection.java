package com.intelligentsia.dowsers.entity.meta;

import java.io.Serializable;
import java.util.Iterator;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;

public class MetaAttributeCollection implements Iterable<MetaAttribute>, Serializable {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -4410480905712842651L;
	
	private ImmutableCollection<MetaAttribute> metaAttributes;

	public MetaAttributeCollection() {
		super();
	}

	public MetaAttributeCollection(ImmutableCollection<MetaAttribute> metaAttributes) {
		super();
		this.metaAttributes = metaAttributes;
	}

	@Override
	public Iterator<MetaAttribute> iterator() {
		return metaAttributes.iterator();
	}

	public ImmutableCollection<MetaAttribute> getMetaAttributes() {
		return metaAttributes;
	}

	public void setMetaAttributes(ImmutableCollection<MetaAttribute> metaAttributes) {
		this.metaAttributes = metaAttributes;
	}

	public boolean contains(Object object) {
		return metaAttributes.contains(object);
	}

	public ImmutableList<MetaAttribute> asList() {
		return metaAttributes.asList();
	}

}
