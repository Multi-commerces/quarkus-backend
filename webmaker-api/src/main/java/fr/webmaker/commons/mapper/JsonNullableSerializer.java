package fr.webmaker.commons.mapper;

import java.io.IOException;

import org.openapitools.jackson.nullable.JsonNullable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonNullableSerializer extends JsonSerializer<JsonNullable<String>> {

	@Override
	public void serialize(JsonNullable<String> value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		gen.writeObject(value == null ? null : value.isPresent() ? value.get() : null);
		
	}
    
   

}