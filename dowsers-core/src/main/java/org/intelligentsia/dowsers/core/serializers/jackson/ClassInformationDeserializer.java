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
package org.intelligentsia.dowsers.core.serializers.jackson;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.intelligentsia.dowsers.core.reflection.ClassInformation;

/**
 * 
 * ClassInformationDeserializer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ClassInformationDeserializer extends StdDeserializer<ClassInformation> {

	public ClassInformationDeserializer() {
		super(ClassInformation.class);
	}

	@Override
	public ClassInformation deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String description = null;

		if (jp.hasCurrentToken()) {
			if (jp.getCurrentToken().equals(JsonToken.START_OBJECT)) {
				jp.nextValue();
				description = jp.getText();
				jp.nextToken();
			}
		}

		return description != null ? ClassInformation.parse(description) : null;
	}

}
