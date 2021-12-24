package fr.webmaker.microservices.catalog.categories.response;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import fr.webmaker.commons.PagingData;
import fr.webmaker.commons.data.SingleCompositeData;
import fr.webmaker.commons.response.CollectionResponse;

public class CollectionResponseSerializer extends JsonSerializer<CollectionResponse<?, ?>> {

	@Override
    public void serialize(CollectionResponse<?,?> value, 
      JsonGenerator gen,
      SerializerProvider serializers) 
      throws IOException, JsonProcessingException {
 
    	gen.writeStartObject();   
    	
    	if (value.getPage() != null)
			gen.writeObjectField("paging", value.getPage());
		else
			gen.writeObjectField("paging", new PagingData());
		
		gen.writeFieldName("collection");
		gen.writeStartArray();
        for (SingleCompositeData<?, ?> singleResponse : value.getCollection()) {
        	gen.writeStartObject();
        	gen.writeObjectField(singleResponse.getIdentifier().getId().toString(),  singleResponse);
			gen.writeEndObject();
		}
		gen.writeEndArray();

		

		gen.writeEndObject();
    }
}