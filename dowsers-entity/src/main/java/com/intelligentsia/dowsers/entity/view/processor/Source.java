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
package com.intelligentsia.dowsers.entity.view.processor;

import java.util.Iterator;

import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.view.Processor;

/**
 * Source transform an Entity in an Item with aliasing possibility.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Source implements Processor {

	private String alias;

	/**
	 * Build a new instance of Source.
	 */
	public Source() {
		this("");
	}

	/**
	 * Build a new instance of Source.
	 * 
	 * @param alias
	 *            alias to prefix all attribute name
	 */
	public Source(final String alias) {
		super();
		if ((alias == null) || "".equals(alias)) {
			this.alias = "";
		} else {
			this.alias = alias + ".";
		}
	}

	@Override
	public Item apply(final Entity entity) {
		final Item item = new Item();
		final Iterator<String> iterator = entity.attributeNames().iterator();
		while (iterator.hasNext()) {
			final String name = iterator.next();
			item.put(alias + name, entity.attribute(name));
		}
		item.put(alias + Reference.IDENTITY, entity.identity());
		return item;
	}

}
