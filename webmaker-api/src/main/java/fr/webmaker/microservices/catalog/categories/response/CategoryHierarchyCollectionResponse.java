package fr.webmaker.microservices.catalog.categories.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.neovisionaries.i18n.LanguageCode;

import fr.webmaker.commons.identifier.LongID;
import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.microservices.catalog.categories.data.CategoryData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryHierarchyCollectionResponse extends CollectionResponse<CategoryData, LongID> implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private LanguageCode languageCode;

	public CategoryHierarchyCollectionResponse() {
		super();
	}
	
	public CategoryHierarchyCollectionResponse(LanguageCode languageCode, List<CategoryHierarchyResponse> data) {
		super(new ArrayList<>(data));
		this._paging = null;
		this.languageCode = languageCode;
	}
	
	
}
