package fr.commerces.microservices.catalog.categories;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.neovisionaries.i18n.LanguageCode;

import fr.webmaker.microservices.catalog.categories.response.CategoryHierarchyCollectionResponse;
import fr.webmaker.microservices.catalog.categories.response.CategoryHierarchyResponse;

@Tag(name = "Ressource Catégories", description = "Ressource pour la gestion des catégories")
@Path("/languages/{languageCode}/categories")
@RequestScoped
public class CategoryResource {

	@Inject
	CategoryManager manager;

	@Path("/")
	@GET
	public CategoryHierarchyCollectionResponse getCategories(
			@Parameter(description = "Langue des catégories recherchées") 
			@PathParam("languageCode") String lang,
			@Parameter(description = "Inclure les sous-catégories") 
			@QueryParam("includeSubCategories") Boolean includeSubCategories) 
	{
		final List<CategoryHierarchyResponse> dataByIdentifier = manager.findCategoryHierarchy(LanguageCode.fr);
	
		//new CollectionResponse<CategoryData, LongID>(new ArrayList<>(dataByIdentifier));
		return new CategoryHierarchyCollectionResponse(LanguageCode.fr, dataByIdentifier);
	}

}