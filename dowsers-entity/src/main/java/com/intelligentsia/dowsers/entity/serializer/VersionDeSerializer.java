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
package com.intelligentsia.dowsers.entity.serializer;

import java.io.IOException;

import org.intelligentsia.keystone.api.artifacts.Version;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * 
 * VersionDeSerializer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class VersionDeSerializer extends StdDeserializer<Version> {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 1916647538058301330L;

	public VersionDeSerializer() {
		super(Version.class);
	}

	@Override
	public Version deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
		if (jp.hasCurrentToken()) {
			final String value = jp.getText();
			if (value != null) {
				return Version.parse(value);
			}
		}
		return null;
	}
}