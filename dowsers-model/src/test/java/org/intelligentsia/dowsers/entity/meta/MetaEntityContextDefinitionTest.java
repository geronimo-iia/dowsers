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

import static org.junit.Assert.fail;

import java.util.LinkedList;

import org.intelligentsia.keystone.api.artifacts.Version;
import org.junit.Test;

/**
 * MetaEntityContextDefinitionTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaEntityContextDefinitionTest {

	@Test
	public void checkConstructorConstraint() {
		try {
			new MetaEntityContextDefinition(null, null, null, null);
			fail("NullPointerException attended");
		} catch (final NullPointerException exception) {
			// ok
		}
		try {
			new MetaEntityContextDefinition("", null, null, null);
			fail("IllegalArgumentException attended");
		} catch (final IllegalArgumentException exception) {
			// ok
		}
		try {
			new MetaEntityContextDefinition("aaa", null, null, null);
			fail("NullPointerException attended");
		} catch (final NullPointerException exception) {
			// ok
		}
		try {
			new MetaEntityContextDefinition("aaa", new Version(1), null, null);
			fail("NullPointerException attended");
		} catch (final NullPointerException exception) {
			// ok
		}
		new MetaEntityContextDefinition("aaa", new Version(1), new LinkedList<MetaAttribute>(), null);
	}

}
