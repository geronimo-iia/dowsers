package com.intelligentsia.dowsers.entity.serializer;

import java.io.IOException;

import org.intelligentsia.keystone.api.artifacts.Version;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * VersionSerializer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class VersionSerializer extends StdSerializer<Version> {

	public VersionSerializer() {
		super(Version.class);
	}

	@Override
	public void serialize(final Version value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonGenerationException {
		if (value != null) {
			jgen.writeString(Version.format(value));
		} else {
			jgen.writeNull();
		}
	}
}