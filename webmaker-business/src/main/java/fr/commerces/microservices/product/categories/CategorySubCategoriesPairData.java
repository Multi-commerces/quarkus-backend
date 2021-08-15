package fr.commerces.microservices.product.categories;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategorySubCategoriesPairData implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private CategoryData category;

	public Map<Long, CategorySubCategoriesPairData> subCategories;

}
