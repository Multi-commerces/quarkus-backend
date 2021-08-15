package fr.commerces.microservices.product.categories;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Réprésente une seul occurence de réponse pour la catégorie
 * 
 * @author Julien ILARI
 */
@Getter
@Setter
public class CategorySubCategoriesPairResponse extends CategoryResponse {

	private static final long serialVersionUID = 1L;
	
	public Map<Long, CategorySubCategoriesPairData> subCategories;

	public CategorySubCategoriesPairResponse(Long id, String languageCode, CategoryData data) {
		super(id, languageCode, data);
	}
}
