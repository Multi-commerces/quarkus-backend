package fr.webmaker.commons.mapper;

import java.io.IOException;

import org.openapitools.jackson.nullable.JsonNullable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JsonNullableDeserializer extends JsonDeserializer<JsonNullable<?>> {

	@Override
	public JsonNullable<?> deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		
		if(ctxt.getNodeFactory().missingNode().asBoolean())
		{
			return JsonNullable.undefined();
		} else if(ctxt.getNodeFactory().nullNode().asBoolean())
		{
			return JsonNullable.of(null);
		}
		
		
		return JsonNullable.of(p.getText());
	}

	
    
   

}