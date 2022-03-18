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
public class CategoryCompositeData extends CategoryData {

	@Relationship(value = "parentCategory", path = "/" + RELATIONS + "/parent", relatedPath = "/parent")
	private CategoryData parentCategory;

	@Relationship(value = "subCategories", path = "/" + RELATIONS + "/children", relatedPath = "/children")
	private List<CategoryCompositeData> subCategories = Collections.emptyList();

	@Relationship(value = "categoryLangs", path = "/" + RELATIONS + "/langs", relatedPath = "/langs")
	private List<CategoryLangData> categoryLangs = Collections.emptyList();
	
}
