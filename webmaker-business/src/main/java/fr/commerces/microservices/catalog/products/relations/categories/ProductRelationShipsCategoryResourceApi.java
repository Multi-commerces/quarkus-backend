package fr.commerces.microservices.catalog.products.relations.categories;

import static fr.commerces.commons.resources.ConstApi.CODE200GET;
import static fr.commerces.commons.resources.ConstApi.CODE204DELETE;
import static fr.commerces.commons.resources.ConstApi.CODE404GET;
import static fr.commerces.commons.resources.ConstApi.MEDIA_JSON_API;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import fr.webmaker.data.category.CategoryData;
import fr.webmaker.restfull.hateos.schema.IShemaBaseCollection;
import fr.webmaker.restfull.hateos.schema.IShemaData;

/**
 * Interface resource API pour la relation produit-catégorie
 * 
 * @author Julien ILARI
 *
 */
@Path("/products/{productId}/relationships/categories}")
@Produces(MEDIA_JSON_API)
@Consumes(MEDIA_JSON_API)
@Tag(name = "Ressource Produits - relationships Catégories", description = "Ressource de gestion des relations produit-catégorie")
public interface ProductRelationShipsCategoryResourceApi {
	

	interface ShemaCollectionData {
		IShemaData<CategoryData>[] getData();
	}
	
	interface ShemaSingleData  {
		IShemaData<CategoryData> getData();
	}
	
	
	@Operation(operationId = "getRelationShipsProductCategories", 
			summary = "Recherche les catégories du produit", 
			description = "Opération de recherche des catégories d'un produit")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = CODE200GET, 
					content = @Content(mediaType = MEDIA_JSON_API, 
					schema = @Schema(implementation = IShemaBaseCollection.class))),
			@APIResponse(responseCode = "404", description = CODE404GET) })
	@GET @Path("/")
	Response getRelationShipsProductCategories(@PathParam("productId") long productId);

	@Operation(operationId = "patchRelationShipsProductCategories", 
			summary = "Remplace chaque catégorie d'un produit", 
			description = "Opération de remplacement des catégories d'un produit")
	@PATCH @Path("/")
	void patchRelationShipsProductCategories(@PathParam("productId") long productId,
			@RequestBody(content = {
			@Content(mediaType = MEDIA_JSON_API,
					schema = @Schema(implementation = IShemaBaseCollection.class)) }) byte[] flux);
	
	@Operation(operationId = "deleteRelationShipsProductCategories", 
			summary = "Retirer des catégories du produit", 
			description = "Opération de suppression des membres de la relation des catégories d'un produit")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = CODE204DELETE, 
					content = @Content(mediaType = MEDIA_JSON_API, 
					schema = @Schema(implementation = IShemaBaseCollection.class))),
			@APIResponse(responseCode = "404", description = CODE404GET) })
	@DELETE @Path("/")
	Response deleteRelationShipsProductCategories(@PathParam("productId") long productId,
			@RequestBody(content = {
					@Content(mediaType = MEDIA_JSON_API,
							schema = @Schema(implementation = IShemaBaseCollection.class)) }) byte[] flux);
	
	
	
	
	
	
	
	
	
	
	
	
}
