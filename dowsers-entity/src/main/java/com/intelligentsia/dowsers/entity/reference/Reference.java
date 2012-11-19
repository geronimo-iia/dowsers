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
package com.intelligentsia.dowsers.entity.reference;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.intelligentsia.dowsers.entity.Entity;

/**
 * A reference follow urn scheme: urn:dowsers:XXXX:YYYY#IIII, where
 * <ul>
 * <li>XXXX represent entity class name</li>
 * <li>YYYY represent an attribute name</li>
 * <li>IIII represent instance identifier</li>
 * <li>YYYY and IIII are not mandatory</li>
 * </ul>
 * 
 * <code>urn:dowsers:com.intelligentsia.dowsers.entity.model.Person:identity#4ca1ea7f-2dfe-4b8a-9008-97f3e30a36be</code>
 * represent:
 * <ul>
 * <li>a reference on a Person instance with
 * '4ca1ea7f-2dfe-4b8a-9008-97f3e30a36be' as identifier.</li>
 * <li>a reference on attribute named 'identity' of a Person instance with
 * '4ca1ea7f-2dfe-4b8a-9008-97f3e30a36be' as identifier.</li>
 * </ul>
 * 
 * <code>urn:dowsers:com.intelligentsia.dowsers.entity.model.Person:name#4ca1ea7f-2dfe-4b8a-9008-97f3e30a36be</code>
 * represent:
 * <ul>
 * <li>a reference on attribute named 'name' of a Person instance with
 * '4ca1ea7f-2dfe-4b8a-9008-97f3e30a36be' as identifier.</li>
 * </ul>
 * 
 * <code>urn:dowsers:com.intelligentsia.dowsers.entity.model.Person:</code>
 * represent:
 * <ul>
 * <li>a reference on all entity of class Person.</li>
 * </ul>
 * 
 * Note: Why not use {@link URI} ?
 * <ul>
 * <li>Avoid try catching {@link URISyntaxException}...</li>
 * <li>Many {@link URI} instantiation when using with EntityStore and other...</li>
 * </ul>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public final class Reference implements Comparable<Reference>, Serializable {

	private static final long serialVersionUID = -385235333185254142L;

	private static final transient char URN_IDENTIFIER_SEPARATOR = '#';
	private static final transient char URN_SEPARATOR = ':';
	private static final transient String URN_DOWSERS = "urn:dowsers:";

	public static final transient String IDENTITY = "identity";

	private final transient String className;
	private final transient String attributeName;
	private final transient String identity;

	/**
	 * The string form of this {@link Reference}.
	 * 
	 * @serial
	 */
	public volatile String value = null;

	/**
	 * Create a {@link Reference} on entity type.
	 * 
	 * @param clazz
	 *            entity class
	 * @return a {@link Reference} instance of entity class
	 * @throws NullPointerException
	 *             if clazz is null
	 */
	public static Reference newReferenceOnEntityClass(final Class<?> clazz) throws NullPointerException {
		return new Reference(clazz);
	}

	/**
	 * Create a {@link Reference} on specific entity instance.
	 * 
	 * @param clazz
	 *            entity class
	 * @param identity
	 * @return a {@link Reference} of entity instance.
	 * @throws NullPointerException
	 *             if clazz or identity is null
	 * @throws IllegalArgumentException
	 *             if identity is empty
	 */
	public static Reference newReference(final Class<?> clazz, final String identity) throws NullPointerException, IllegalArgumentException {
		return new Reference(clazz, identity);
	}

	/**
	 * Build a new instance of <code>Reference</code> of entity class.
	 * 
	 * @param clazz
	 *            entity class
	 * @throws NullPointerException
	 *             if clazz is null
	 */
	public Reference(final Class<?> clazz) throws NullPointerException {
		this(clazz, "", null);
	}

	/**
	 * Build a new instance of <code>Reference</code> of entity instance.
	 * 
	 * @param clazz
	 *            entity class
	 * @param identity
	 * @throws NullPointerException
	 *             if clazz or identity is null
	 * @throws IllegalArgumentException
	 *             if identity is empty
	 */
	public Reference(final Class<?> clazz, final String identity) throws NullPointerException, IllegalArgumentException {
		this(clazz, IDENTITY, Preconditions.checkNotNull(identity));
		Preconditions.checkArgument(!"".equals(identity));
	}

	/**
	 * Build a new instance of <code>Reference</code> of attribute's entity.
	 * 
	 * @param clazz
	 *            entity class
	 * @param attributeName
	 *            attribute name
	 * @param identity
	 *            identity
	 * @throws NullPointerException
	 *             if clazz is null
	 */
	public Reference(final Class<?> clazz, final String attributeName, final String identity) throws NullPointerException {
		this(Preconditions.checkNotNull(clazz).getName(), attributeName, identity);
	}

	/**
	 * Build a new instance of <code>Reference</code>.
	 * 
	 * @param className
	 *            entity class name
	 * @param attributeName
	 *            attribute name
	 * @param identity
	 *            identity
	 * @throws IllegalArgumentException
	 *             if className is null
	 */
	private Reference(final String className, final String attributeName, final String identity) throws IllegalArgumentException {
		Preconditions.checkArgument(className != null);
		this.className = className;
		this.attributeName = attributeName == null ? "" : attributeName;
		this.identity = identity;
		defineString();
	}

	/**
	 * @return true if this instance is an identifier ( attribute name is
	 *         'identity' and identity field is not null)
	 */
	public boolean isIdentifier() {
		return IDENTITY.equals(attributeName) && (identity != null);
	}

	/**
	 * @return entity class name
	 */
	public String getEntityClassName() {
		return className;
	}

	/**
	 * @return a {@link Reference} instance on Entity class.
	 */
	public Reference getEntityClassReference() {
		return new Reference(className, "", null);
	}

	/**
	 * @return attribute name or an empty string if this reference represent a
	 *         class of {@link Entity}
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * @return identity part of this {@link Reference}, or null if this
	 *         {@link Reference} represent a class of {@link Entity} or an
	 *         attribute class.
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * @return an {@link URI} representation of this {@link Reference}.
	 */
	public URI toURI() {
		try {
			return new URI(value);
		} catch (final URISyntaxException e) {
			throw Throwables.propagate(e);
		}
	}

	/**
	 * Parse an {@link URI}.
	 * 
	 * @param uri
	 * @return a {@link Reference} instance
	 * @throws NullPointerException
	 *             if uri is null
	 * @throws IllegalArgumentException
	 *             if uri is not a {@link Reference}.
	 */
	public static Reference parseURI(final URI uri) throws NullPointerException, IllegalArgumentException {
		return parseString(Preconditions.checkNotNull(uri).toString());
	}

	/**
	 * Parse a {@link String} representation of {@link Reference}.
	 * 
	 * @param urn
	 * @return a {@link Reference} instance
	 * @throws NullPointerException
	 *             if urn is null
	 * @throws IllegalArgumentException
	 *             if urn is not a {@link Reference}.
	 */
	public static Reference parseString(final String urn) throws NullPointerException, IllegalArgumentException {
		Preconditions.checkArgument(Preconditions.checkNotNull(urn).length() > URN_DOWSERS.length());
		final int lastSeparator = urn.lastIndexOf(URN_SEPARATOR);
		Preconditions.checkArgument(lastSeparator >= 0);
		final String className = urn.substring(URN_DOWSERS.length(), lastSeparator);
		final int index = urn.indexOf(URN_IDENTIFIER_SEPARATOR);
		final String attributeName = index < 0 ? urn.substring(urn.lastIndexOf(URN_SEPARATOR) + 1) : urn.substring(urn.lastIndexOf(URN_SEPARATOR) + 1, index);
		final String identity = index < 0 ? null : urn.substring(index + 1);
		return new Reference(className, attributeName, identity);
	}

	@Override
	public int compareTo(final Reference o) {
		return value.compareTo(o.toString());
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Reference other = (Reference) obj;
		return Objects.equal(value, other.value);
	}

	/**
	 * Build inner value.
	 */
	private void defineString() {
		if (value != null) {
			return;
		}
		final StringBuilder builder = new StringBuilder(URN_DOWSERS).append(className).append(URN_SEPARATOR);
		if (attributeName != null) {
			builder.append(attributeName);
		}
		if (identity != null) {
			builder.append(URN_IDENTIFIER_SEPARATOR).append(identity);
		}
		value = builder.toString();
	}

}
