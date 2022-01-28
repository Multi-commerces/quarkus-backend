package fr.commerces.microservices.catalog.categories.data;

import java.util.List;

import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;

import fr.commerces.microservices.catalog.categories.lang.CategoryLangData;
import io.smallrye.common.constraint.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Type(value = "category", path = "/categories/{id}")
@NoArgsConstructor
@Getter
@Setter
public class CategoryRelationData extends CategoryData {

	public static final String RELATION_LANG = "langs";
	public static final String RELATION_SUB_CATEGORIES = "children";
	public static final String RELATION_PARENT_CATEGORY = "parent";

	@Nullable
	@Relationship(value = RELATION_LANG, path = RELATIONS + "/" + RELATION_LANG, relatedPath = RELATION_LANG)
	protected List<CategoryLangData> categoryLangs;
	
	@Nullable
	@Relationship(value = RELATION_PARENT_CATEGORY, path = "/" + RELATIONS + "/"
			+ RELATION_PARENT_CATEGORY, relatedPath = RELATION_PARENT_CATEGORY)
	private CategoryRelationData parent;

	@Nullable
	@Relationship(value = RELATION_SUB_CATEGORIES, path = "/" + RELATIONS + "/"
			+ RELATION_SUB_CATEGORIES, relatedPath = RELATION_SUB_CATEGORIES)
	private List<CategoryRelationData> subCategories;
}
