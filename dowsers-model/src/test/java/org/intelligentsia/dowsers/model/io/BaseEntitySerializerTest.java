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
package org.intelligentsia.dowsers.model.io;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.util.VersionUtil;
import org.intelligentsia.dowsers.core.io.JacksonSerializer;
import org.intelligentsia.dowsers.model.BaseEntity;
import org.intelligentsia.dowsers.model.EntityManagerUnit;
import org.intelligentsia.dowsers.model.MockMetaEntityContextRepository;
import org.intelligentsia.dowsers.model.SampleEntity;
import org.intelligentsia.dowsers.model.factory.BaseEntityFactorySupport;
import org.intelligentsia.dowsers.model.factory.EntityFactoryProxySupport;
import org.intelligentsia.dowsers.model.meta.MetaEntityContextRepository;
import org.junit.Test;

/**
 * BaseEntitySerializerTest. 
 *
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class BaseEntitySerializerTest {

	// TODO must do this with an entity manager...
	public void testSerialization() throws JsonGenerationException, JsonMappingException, IOException {
		final EntityManagerUnit entityManagerUnit = new EntityManagerUnit(new EntityFactoryProxySupport(new BaseEntityFactorySupport(new MockMetaEntityContextRepository())));
		final SampleEntity sampleEntity = entityManagerUnit.newInstance(SampleEntity.class);
		sampleEntity.setName("Hello John");
		sampleEntity.setDescription("a blablablabalbablbalablabb");
		final ObjectMapper mapper = JacksonSerializer.getMapper();
		final SimpleModule testModule = new SimpleModule("BaseEntityJacksonSerializer", VersionUtil.versionFor(mapper.getClass()));
		testModule.addSerializer(new BaseEntityJsonSerializer());
		mapper.registerModule(testModule);
		final StringWriter writer = new StringWriter();
		mapper.writeValue(writer, sampleEntity);
		System.err.println(writer.toString());
	}

	@Test
	public void testSerializationBase() throws JsonGenerationException, JsonMappingException, IOException {
		final MetaEntityContextRepository repository = new MockMetaEntityContextRepository();

		final BaseEntity sampleEntity = new BaseEntity("1", repository.find(SampleEntity.class));
		sampleEntity.setProperty("name", "a name");
		sampleEntity.setProperty("description", "a description");
		final ObjectMapper mapper = JacksonSerializer.getMapper();
		final SimpleModule testModule = new SimpleModule("BaseEntityJacksonSerializer", VersionUtil.versionFor(mapper.getClass()));
		testModule.addSerializer(new BaseEntityJsonSerializer());
		mapper.registerModule(testModule);

		final StringWriter writer = new StringWriter();
		mapper.writeValue(writer, sampleEntity);
		System.err.println(writer.toString());

	}

}
