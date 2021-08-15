package fr.commerces.microservices.product.categories;

import fr.webmaker.common.response.SingleResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * Réprésente une seul occurence de réponse pour la catégorie
 * 
 * @author Julien ILARI
 */
@Getter
@Setter
public class CategoryResponse extends SingleResponse<CategoryData, CategoryID> {

	private static final long serialVersionUID = 1L;

	public CategoryResponse(Long id, String languageCode, CategoryData data) {
		super();
		this.identifier = new CategoryID(id, languageCode);
		this.data = data;
	}

	public CategoryResponse() {
		this(null, null, null);
	}

}
