package fr.commerces.microservices.catalog.categories.response;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.microservices.catalog.categories.data.CategoryHierarchyData;
import fr.webmaker.commons.identifier.LongID;
import fr.webmaker.commons.response.MapResponse;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategoryHierarchyResponse extends MapResponse<LongID, CategoryHierarchyData> {

	private LanguageCode languageCode;
	
}
