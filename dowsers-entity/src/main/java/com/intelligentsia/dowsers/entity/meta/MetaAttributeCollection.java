package com.intelligentsia.dowsers.entity.meta;

import java.util.Iterator;

import com.google.common.collect.ImmutableCollection;

public class MetaAttributeCollection implements Iterable<MetaAttribute> {

	private ImmutableCollection<MetaAttribute> attributes;

	public MetaAttributeCollection() {
		super();
	}

	public MetaAttributeCollection(ImmutableCollection<MetaAttribute> attributes) {
		super();
		this.attributes = attributes;
	}

	public ImmutableCollection<MetaAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(ImmutableCollection<MetaAttribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public Iterator<MetaAttribute> iterator() {
		return attributes.iterator();
	}

}
