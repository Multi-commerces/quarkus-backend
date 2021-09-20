package fr.webmaker.microservices.catalog.categories.response;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import fr.webmaker.commons.identifier.LangID;
import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.categories.data.CategoryData;

public class CategoryHierarchyCollectionResponseSerializer extends JsonSerializer<CategoryHierarchyCollectionResponse> {
	@Override
	public void serialize(CategoryHierarchyCollectionResponse arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException {
		
		arg1.writeStartObject();
		arg1.writeFieldName("collection");
		arg1.writeStartArray();
        for (SingleResponse<CategoryData, LangID> singleResponse : arg0.getCollection()) {
        	arg1.writeStartObject();
        	arg1.writeObjectField("identifier", singleResponse.getIdentifier());
			arg1.writeObjectField("data", singleResponse.getData());
			arg1.writeObjectField("subCategories", ((CategoryHierarchySingleResponse) singleResponse).getSubCategories());
			arg1.writeObjectField("links", singleResponse.getLinks());
			arg1.writeEndObject();
		}
		arg1.writeEndArray();

		if (arg0.getPage() != null)
			arg1.writeObjectField("paging", arg0.getPage());

		arg1.writeEndObject();
	}
}