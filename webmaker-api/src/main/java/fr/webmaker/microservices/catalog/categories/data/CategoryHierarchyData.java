package fr.webmaker.microservices.catalog.categories.data;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.webmaker.commons.identifier.LangID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CategoryHierarchyData implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identifiant
	 */
	protected LangID identifier;

	/**
	 * Données
	 */
	protected CategoryData category;


	/**
	 * Sous-catégories
	 */
	@JsonProperty("subCategories")
	private List<CategoryHierarchyData> subCategories;


	public CategoryHierarchyData(LangID identifier, CategoryData data) {
		super();
		this.identifier = identifier;
		this.category = data;
	}

}
