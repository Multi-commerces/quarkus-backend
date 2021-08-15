package fr.commerces.microservices.catalog.categories;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.microservices.catalog.categories.data.CategoryHierarchyData;
import fr.commerces.microservices.catalog.categories.response.CategoryHierarchyResponse;
import fr.webmaker.commons.identifier.LongID;

@Tag(name = "Ressource Catégories", description = "Ressource pour la gestion des catégories")
@Path("/languages/{languageCode}/categories")
@RequestScoped
public class CategoryResource {

	@Inject
	CategoryManager manager;

	@Path("/")
	@GET
	public CategoryHierarchyResponse getCategoriesV(
			@Parameter(description = "Langue des catégories recherchées") 
			@PathParam("languageCode") String lang,
			@Parameter(description = "Inclure les sous-catégories") 
			@QueryParam("includeSubCategories") Boolean includeSubCategories) 
	{
		final Map<LongID, CategoryHierarchyData> dataByIdentifier = manager.findCategoryHierarchy(LanguageCode.fr);

		CategoryHierarchyResponse response = new CategoryHierarchyResponse();
		response.setLanguageCode(LanguageCode.fr);
		response.setDataByIdentifier(dataByIdentifier);
		
		return response;
	}

}