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
package org.intelligentsia.dowsers.model;

import java.util.Iterator;

import com.google.common.base.Preconditions;

/**
 * {@link MetaEntityExtended} aggregate two {@link MetaEntityDefinition}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaEntityExtended extends MetaEntityDefinition {

	private final MetaEntityDefinition extendedMetaEntityDefinition;

	/**
	 * Build a new instance of MetaEntityExtended.java.
	 * 
	 * @param metaEntityDefinition
	 * @param extendedMetaEntityDefinition
	 * @throws NullPointerException
	 * @throws IllegalStateException
	 */
	public MetaEntityExtended(MetaEntityDefinition metaEntityDefinition, MetaEntityDefinition extendedMetaEntityDefinition) throws NullPointerException, IllegalStateException {
		super(metaEntityDefinition);
		this.extendedMetaEntityDefinition = Preconditions.checkNotNull(extendedMetaEntityDefinition);
		Preconditions.checkState(metaEntityDefinition.getName().equals(extendedMetaEntityDefinition.getName()));
	}

	@Override
	public boolean contains(String name) throws NullPointerException, IllegalArgumentException {
		return super.contains(name) ? Boolean.TRUE : extendedMetaEntityDefinition.contains(name);
	}

	@Override
	public MetaProperty getMetaProperty(String name) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		MetaProperty metaProperty = super.getMetaProperty(name);
		if (metaProperty == null) {
			metaProperty = extendedMetaEntityDefinition.getMetaProperty(name);
		}
		return metaProperty;
	}

	@Override
	public Iterator<MetaProperty> getMetaProperties() {
		final Iterator<MetaProperty> base = super.getMetaProperties();
		return new Iterator<MetaProperty>() {
			boolean switched = false;
			Iterator<MetaProperty> current = base;

			@Override
			public void remove() {
				throw new IllegalStateException("Forbidden operation");
			}

			@Override
			public MetaProperty next() {
				return current.next();
			}

			@Override
			public boolean hasNext() {
				boolean result = current.hasNext();
				if (!result && !switched) {
					current = extendedMetaEntityDefinition.getMetaProperties();
					switched = true;
					result = current.hasNext();
				}
				return result;
			}
		};
	}

	/**
	 * @return extended {@link MetaEntityDefinition}.
	 */
	public MetaEntityDefinition getExtendedMetaEntityDefinition() {
		return extendedMetaEntityDefinition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((extendedMetaEntityDefinition == null) ? 0 : extendedMetaEntityDefinition.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MetaEntityExtended other = (MetaEntityExtended) obj;
		if (extendedMetaEntityDefinition == null) {
			if (other.extendedMetaEntityDefinition != null)
				return false;
		} else if (!extendedMetaEntityDefinition.equals(other.extendedMetaEntityDefinition))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MetaEntityExtended [ " + super.toString() + ", " + extendedMetaEntityDefinition + "]";
	}

}
