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
package com.intelligentsia.dowsers.entity;

import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Reference generate {@link Entity} or attribute's {@link Entity}
 * reference.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum Reference {
	;

	public static URI newReference(final Entity entity) throws URISyntaxException {
		return newReference(entity, "identity");
	}

	public static URI newReference(final Entity entity, final String attributeName) throws URISyntaxException {
		if (Proxy.isProxyClass(entity.getClass())) {
			EntityProxy entityProxy = (EntityProxy) Proxy.getInvocationHandler(entity);
			return new URI("urn", "dowsers:" + entityProxy.getInterfaceName().getName() + ':' + attributeName, entity.identity());
		}
		return new URI("urn", "dowsers:" + entity.getClass().getName() + ':' + attributeName, entity.identity());
	}

	public static String getIdentity(URI uri) {
		return uri.getFragment();
	}

	public static String getEntityPart(URI uri) {
		String ssp = uri.getSchemeSpecificPart();
		return ssp.substring(ssp.indexOf(':') + 1, ssp.lastIndexOf(':'));
	}

	public static String getAttributPart(URI uri) {
		String ssp = uri.getSchemeSpecificPart();
		return ssp.substring(ssp.lastIndexOf(':') + 1);
	}

}
