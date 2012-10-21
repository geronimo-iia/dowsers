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