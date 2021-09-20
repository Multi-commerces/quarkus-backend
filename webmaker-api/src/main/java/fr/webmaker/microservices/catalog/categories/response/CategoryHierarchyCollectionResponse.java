package fr.webmaker.microservices.catalog.categories.response;

import java.util.List;

import fr.webmaker.commons.identifier.LangID;
import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.microservices.catalog.categories.data.CategoryData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper=true)
//@JsonSerialize(using = CategoryHierarchyCollectionResponseSerializer.class)
//@JsonSerialize(using = CollectionResponseSerializer.class)
public class CategoryHierarchyCollectionResponse extends CollectionResponse<CategoryData, LangID> {
	
	public CategoryHierarchyCollectionResponse(final List<CategoryHierarchySingleResponse> collection) {
		super();
		this.collection.addAll(collection);
	}
	
}
