package fr.commerces.microservices.catalog.categories.data;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryHierarchyData implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private CategoryData category;

	public Map<Long, CategoryHierarchyData> subCategories;

	public CategoryHierarchyData(CategoryData category) {
		super();
		this.category = category;
	}
	
	

}
