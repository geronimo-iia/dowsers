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
package org.intelligentsia.dowsers.entity.meta;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.intelligentsia.keystone.api.artifacts.Version;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * DefaultEntityDescriptor.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultEntityDescriptor implements EntityDescriptor {

	private final String name;

	private final Version domainVersion;

	private final boolean extendable;

	private final Version extendedDomainVersion;

	private final Map<String, PropertyDescriptor> propertyDescriptors;

	private final Set<String> domainPropertyName;

	private final Set<String> extendedPropertyName;

	/**
	 * Build a new instance of DefaultEntityDescriptor.java.
	 * 
	 * @param name
	 *            entity name
	 * @param domainVersion
	 *            entity domain version
	 * @param extendable
	 *            is extendible ?
	 * @param extendedDomainVersion
	 *            entity extended domain version
	 * @param propertyDescriptors
	 *            collection of {@link PropertyDescriptor}
	 * @param domainPropertyName
	 *            set of property name define by domain
	 * @param extendedPropertyName
	 *            set of property name define by extended domain
	 * @throws NullPointerException
	 *             if name, domainVersion, propertyDescriptors or
	 *             domainPropertyName is null
	 * @throws IllegalArgumentException
	 *             if name is empty
	 * @throws IllegalStateException
	 *             if extendible and either extendedDomainVersion or
	 *             extendedPropertyName is null. If name of domainPropertyName
	 *             and extendedPropertyName have no {@link PropertyDescriptor}
	 *             associated.
	 */
	public DefaultEntityDescriptor(String name, Version domainVersion, boolean extendable, Version extendedDomainVersion, Collection<PropertyDescriptor> propertyDescriptors, Set<String> domainPropertyName, Set<String> extendedPropertyName)
			throws NullPointerException, IllegalArgumentException, IllegalStateException {
		super();
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		this.name = name;
		this.domainVersion = Preconditions.checkNotNull(domainVersion);
		this.extendable = extendable;
		this.extendedDomainVersion = extendedDomainVersion;
		this.domainPropertyName = Preconditions.checkNotNull(domainPropertyName);
		this.extendedPropertyName = extendedPropertyName;
		// check extendable
		if (extendable) {
			Preconditions.checkState(extendedDomainVersion != null, "extendedDomainVersion must be set");
			Preconditions.checkState(extendedPropertyName != null, "extendedPropertyName must be set");
		}
		// property Descriptors
		Preconditions.checkNotNull(propertyDescriptors);
		this.propertyDescriptors = Maps.newHashMap();
		for (PropertyDescriptor descriptor : propertyDescriptors) {
			this.propertyDescriptors.put(descriptor.getName(), descriptor);
		}
		// property name check
		for (String item : domainPropertyName) {
			if (!this.propertyDescriptors.containsKey(item)) {
				throw new IllegalStateException("No PropertyDescriptor for key: " + item);
			}
		}
		for (String item : extendedPropertyName) {
			if (!this.propertyDescriptors.containsKey(item)) {
				throw new IllegalStateException("No PropertyDescriptor for key: " + item);
			}
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Version getDomainVersion() {
		return domainVersion;
	}

	@Override
	public boolean isExtendable() {
		return extendable;
	}

	@Override
	public Version getExtendedDomainVersion() throws IllegalStateException {
		Preconditions.checkState(extendable);
		return extendedDomainVersion;
	}

	@Override
	public boolean contains(String name) throws NullPointerException, IllegalArgumentException {
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		return propertyDescriptors.containsKey(name);
	}

	@Override
	public PropertyDescriptor getPropertyDescriptor(String name) throws NullPointerException, IllegalArgumentException, IllegalStateException {
		Preconditions.checkArgument(!"".equals(Preconditions.checkNotNull(name)));
		PropertyDescriptor descriptor = propertyDescriptors.get(name);
		Preconditions.checkState(descriptor != null);
		return descriptor;
	}

	@Override
	public Iterator<PropertyDescriptor> iterator() {
		return propertyDescriptors.values().iterator();
	}

	@Override
	public Iterator<String> getDomainPropertyName() {
		return domainPropertyName.iterator();
	}

	@Override
	public Iterator<String> getExtendedPropertyName() {
		return extendedPropertyName.iterator();
	}

}
