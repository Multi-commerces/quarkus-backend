package fr.webmaker.data.category;

import java.util.Collections;
import java.util.List;

import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Type(value = "category", path = "/categories/{id}")
public class CategoryRelationData extends CategoryData {

	public static final String RELATION_SUB_CATEGORIES = "children";
	public static final String RELATION_PARENT_CATEGORY = "parent";
	
	@Relationship(value = RELATION_PARENT_CATEGORY, path = "/" + RELATIONS + "/"
			+ RELATION_PARENT_CATEGORY, relatedPath = RELATION_PARENT_CATEGORY)
	private CategoryData parentCategory;

	@Relationship(value = RELATION_SUB_CATEGORIES, path = "/" + RELATIONS + "/"
			+ RELATION_SUB_CATEGORIES, relatedPath = RELATION_SUB_CATEGORIES)
	private List<CategoryRelationData> subCategories = Collections.emptyList();
}
