package fr.commerces.microservices.catalog.products.relations.pricing;

import static fr.commerces.commons.resources.ConstApi.MEDIA_JSON_API;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path(ProductPricingApi.PATH)
@Produces(MEDIA_JSON_API)
@Consumes(MEDIA_JSON_API)
@Tag(name = "Ressource Produits - Prix", description = "Ressource pour la gestion des prix du produit")
public interface ProductPricingApi {
	
	public static final String PATH = "/products/{productId}/pricing";	

	/**
	 * GET Resource - Expédition produit
	 * @param productId
	 * @return
	 */
	@GET @Path("/")
	@Operation(operationId = "getProductPricing", 
		summary = "Recherche les informations d'expédition d'un produit.", 
		description = "Retourne les informations d'expédition du produit.")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK] - Opération de recherche effectuée avec succès"),
			@APIResponse(responseCode = "401", description = "[NOK] - Une identification est nécessaire"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun produit ne correspond aux paramètres fournis") 
	})
	Response getProductPricing(
			@Parameter(description = "Identifiant du produit") 
			@PathParam("productId") Long productId
	);
	
	@PATCH
	@Path("/")
	@Operation(operationId = "patchProductPricing", summary = "Modification expédition produit", description = "Opération de modification des informations de base d'un produit existant.")
	@APIResponses(value = { 
			@APIResponse(responseCode = "204", description = "[OK] - Opération d'enregistrement effectuée avec succès"),
			@APIResponse(responseCode = "401", description = "[NOK] - Une identification est nécessaire"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun produit ne correspond aux paramètres fournis") 
	})
	Response patchProductPricing(/*
			 * Identifiant
			 */
			@Parameter(description = "Identifiant du produit") 
			@PathParam(value = "productId") Long productId, 
			byte[] data);

}