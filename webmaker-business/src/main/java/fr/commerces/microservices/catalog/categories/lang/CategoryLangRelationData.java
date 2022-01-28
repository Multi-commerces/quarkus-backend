package fr.commerces.microservices.catalog.categories.lang;

import com.github.jasminb.jsonapi.Link;
import com.github.jasminb.jsonapi.Links;
import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;

import fr.commerces.microservices.catalog.categories.basic.CategoryResource;
import fr.webmaker.data.category.CategoryRelationData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@Type(value = "categoryLang", path = "../")
public class CategoryLangRelationData extends CategoryLangData {

	public static final String RELATION_CATEGORY = "categories";

	@Relationship(value = "category")
	protected CategoryRelationData category;

	@Override
	public Links getLinks() {
		Link link = new Link(
				String.join("/", CategoryResource.PATH, String.valueOf(categoryId), "langs", languageCode.toString()));

		links = new com.github.jasminb.jsonapi.Links();
		links.addLink("self", link);

		return super.getLinks();
	}

}
