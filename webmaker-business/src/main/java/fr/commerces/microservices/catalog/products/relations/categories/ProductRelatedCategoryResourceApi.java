package fr.commerces.microservices.catalog.products.relations.categories;

import static fr.commerces.commons.resources.ConstApi.CODE200GET;
import static fr.commerces.commons.resources.ConstApi.MEDIA_JSON_API;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import fr.webmaker.data.category.CategoryData;
import fr.webmaker.restfull.hateos.schema.IShemaData;

/**
 * Interface resource API pour la relation produit-catégorie
 * 
 * @author Julien ILARI
 *
 */
@Path("/products/{productId}/categories")
@Produces(MEDIA_JSON_API)
@Consumes(MEDIA_JSON_API)
@Tag(name = "Ressource Produits - Catégories", description = "Ressource de gestion des relations produit-catégorie")
public interface ProductRelatedCategoryResourceApi {
	

	interface ShemaCollectionData {
		IShemaData<CategoryData>[] getData();
	}
	
	interface ShemaSingleData  {
		IShemaData<CategoryData> getData();
	}
	
	
	@Tag(ref = "Ressource Produits - Catégories")
	@Operation(operationId = "getRelatedProductCategories", 
			summary = "Recherche les catégories du produit", 
			description = "Opération de recherche des catégories d'un produit")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = CODE200GET, 
			content = @Content(mediaType = MEDIA_JSON_API, 
			schema = @Schema(implementation = ShemaCollectionData.class))),
			@APIResponse(responseCode = "404", description = "") })
	@GET @Path("/")
	Response getRelatedProductCategories(
			@Parameter(description = "Liste des catégories d'un produit") 
			@PathParam("productId") long productId);
	
	
	
	
	
	
	
	
	
	
	
	
}
