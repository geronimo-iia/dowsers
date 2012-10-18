package com.intelligentsia.dowsers.entity.serializer;

import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerFactory;
import org.codehaus.jackson.map.ser.StdSerializerProvider;

public class EntityProxySerializerProvider extends StdSerializerProvider {

	public EntityProxySerializerProvider() {
		super();
	}

	public EntityProxySerializerProvider(SerializationConfig config, StdSerializerProvider src, SerializerFactory f) {
		super(config, src, f);
	}

	
}

