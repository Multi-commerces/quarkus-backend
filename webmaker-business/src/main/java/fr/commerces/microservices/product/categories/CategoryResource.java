package fr.commerces.microservices.product.categories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Ressource Catégories", description = "Ressource pour la gestion des catégories")
@Path("/languages/{languageCode}/categories")
@RequestScoped
public class CategoryResource {

	@Inject
	CategoryManager manager;

	@Path("/")
	@GET
	public List<CategorySubCategoriesPairResponse> getCategoriesV2(
			@Parameter(description = "Langue des catégories recherchées") @PathParam("languageCode") String lang) {
		final Map<Long, CategorySubCategoriesPairData> items = manager.findCategoryHierarchy();

		// Réponse API - embedded
		final List<CategorySubCategoriesPairResponse> response = new ArrayList<>();
		items.entrySet().stream().forEach(entry -> {
			final CategorySubCategoriesPairResponse dataResponse = new CategorySubCategoriesPairResponse(entry.getKey(),
					lang, entry.getValue().getCategory());
			dataResponse.setSubCategories(entry.getValue().getSubCategories());
			response.add(dataResponse);
		});

		return response;
	}

}