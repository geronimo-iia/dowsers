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
package com.intelligentsia.dowsers.entity.store;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.common.base.Throwables;
import com.intelligentsia.dowsers.entity.store.fs.FileEntityStore;

/**
 * <code>FileEntityStoreTest</code>.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * 
 */
public class FileEntityStoreTest extends StoreBaseTest {

	@Override
	public EntityStore instanciateEntityStore() {
		return new FileEntityStore(getRoot(), entityMapper);
	}

	public static File getRoot() {
		// Calculates the root directory of the demo project.
		URI uri;
		try {
			uri = FileEntityStoreTest.class.getResource("FileEntityStoreTest.class").toURI();
		} catch (final URISyntaxException e) {
			throw Throwables.propagate(e);
		}
		final String path = new File(uri.getPath()).getAbsolutePath();
		final String subPath = "\\target\\test-classes\\com\\intelligentsia\\dowsers\\entity\\store\\FileEntityStoreTest.class";
		final File root = new File(new File(path.substring(0, path.length() - subPath.length())), File.separator + "target" + File.separator + "output");
		return root;
	}
}
