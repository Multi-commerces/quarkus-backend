package fr.webmaker.microservices.catalog.categories.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.webmaker.commons.identifier.LongID;
import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.categories.data.CategoryData;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryHierarchyResponse extends SingleResponse<CategoryData, LongID> implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("subCategories")
	private List<CategoryHierarchyResponse> subCategories;
	
	public CategoryHierarchyResponse()
	{
		
	}

	public CategoryHierarchyResponse(CategoryData category) {
		super();
		this.data = category;
	}
	
	

}
