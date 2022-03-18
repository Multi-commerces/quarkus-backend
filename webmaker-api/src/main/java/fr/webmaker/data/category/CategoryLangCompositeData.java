package fr.webmaker.data.category;

import com.github.jasminb.jsonapi.Link;
import com.github.jasminb.jsonapi.Links;
import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Type(value = "categoryLang", path = "../")
public class CategoryLangCompositeData extends CategoryLangData {
	
	@Relationship(value = "category")
	protected CategoryCompositeData category;

	@Override
	public Links getLinks() {
		Link link = new Link(
				String.join("/", "categories", String.valueOf(categoryId), "langs", languageCode.toString()));

		links = new com.github.jasminb.jsonapi.Links();
		links.addLink("self", link);

		return super.getLinks();
	}

}
