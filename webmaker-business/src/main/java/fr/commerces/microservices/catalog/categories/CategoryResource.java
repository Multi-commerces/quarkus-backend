package fr.commerces.microservices.catalog.categories;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.neovisionaries.i18n.LanguageCode;

import fr.webmaker.commons.identifier.LangID;
import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.categories.data.CategoryData;
import fr.webmaker.microservices.catalog.categories.response.CategoryHierarchySingleResponse;

@Tag(name = "Ressource Catégories", description = "Ressource pour la gestion des catégories")
@Path("/languages/{languageCode}/categories")
@RequestScoped
public class CategoryResource {

	@Inject
	CategoryManager manager;

	@Path("/")
	@GET
	public CollectionResponse<CategoryData, LangID> getCategories(
			@Parameter(description = "Langue des catégories recherchées") 
			@PathParam("languageCode") String lang,
			@Parameter(description = "Inclure les sous-catégories") 
			@QueryParam("includeSubCategories") Boolean includeSubCategories) 
	{
		// Collection SingleResponse
		List<SingleResponse<CategoryData, LangID>> data = manager.findCategoryHierarchy(LanguageCode.fr).stream()
			.map(categoryHierarchy -> new CategoryHierarchySingleResponse(categoryHierarchy))
			.collect(Collectors.toList());
		
		return new CollectionResponse<CategoryData, LangID>(data);
	}

}