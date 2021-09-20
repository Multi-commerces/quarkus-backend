package fr.webmaker.microservices.catalog.categories.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import fr.webmaker.commons.identifier.LangID;
import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.categories.data.CategoryData;
import fr.webmaker.microservices.catalog.categories.data.CategoryHierarchyData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonRootName("category")
@Data @NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CategoryHierarchySingleResponse extends SingleResponse<CategoryData, LangID> implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("subCategories")
	private List<CategoryHierarchyData> subCategories;

	public CategoryHierarchySingleResponse(final CategoryHierarchyData rootCategory) {
		super(rootCategory.getIdentifier(), rootCategory.getCategory());
		this.subCategories = rootCategory.getSubCategories();
	}

}
