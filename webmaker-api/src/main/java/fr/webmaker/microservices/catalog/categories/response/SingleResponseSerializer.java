package fr.webmaker.microservices.catalog.categories.response;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import fr.webmaker.commons.response.SingleResponse;

public class SingleResponseSerializer extends JsonSerializer<SingleResponse<?, ?>> {

	@Override
	public void serialize(SingleResponse<?, ?> singleResponse, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		
		gen.writeStartObject();
		Field[] fields = Stream.concat(Arrays.stream(singleResponse.getClass().getDeclaredFields()),
				Arrays.stream(SingleResponse.class.getDeclaredFields())).toArray(Field[]::new);		
	
		Stream.of(fields).forEach(field -> {
			JsonProperty annotation = field.getAnnotation(JsonProperty.class);
			final String fieldName;
			if (annotation != null) {
				fieldName = annotation.value();
			} else {
				fieldName = field.getName();
			}
			
			if("serialVersionUID".equals(fieldName))
			{
				return;
			}

			try {
				field.setAccessible(true);
				Object obj = field.get(singleResponse);
				gen.writeObjectField(fieldName, obj);
			} catch (Exception e) {
				// Ignore !
			}
		});		

		gen.writeEndObject();
	}

}