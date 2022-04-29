package fr.commerces.microservices.catalog.products.relations.stocks;

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

import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;

@Path(ProductStockApi.PATH)
@Produces(MEDIA_JSON_API)
@Consumes(MEDIA_JSON_API)
@Tag(name = "Ressource Produits - Stock", description = "Ressource de gestion du stock produit")
public interface ProductStockApi {
	
	public static final String PATH = "/products/{productId}/stock";
	
	/**
	 * GET Resource
	 * @param productId
	 * @return
	 * @throws DocumentSerializationException 
	 */
	@GET
	@Path("/")
	@Operation(operationId = "getProductStock", summary = "Stock produit", description = "Opération de recherche des informations de stock du produit.")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "[OK] - Opération de recherche effectuée avec succès"),
			@APIResponse(responseCode = "401", description = "[NOK] - Une identification est nécessaire"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun stock ne correspond aux paramètres fournis") })
	Response getProductStock(
			@Parameter(description = "Identifiant du produit") @PathParam("productId") Long productId);
	
	/**
	 * PATCH Resource
	 * @param productId
	 * @param data
	 * @return
	 */
	@PATCH
	@Path("/")
	@Operation(operationId = "patchProductStock", summary = "Modification stock produit", description = "Opération de modification des informations du stock d'un produit existant.")
	@APIResponses(value = {
			@APIResponse(responseCode = "204", description = "[OK] - Opération d'enregistrement effectuée avec succès"),
			@APIResponse(responseCode = "401", description = "[NOK] - Une identification est nécessaire"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun stock ne correspond aux paramètres fournis") })
	Response patchProductStock(
			@Parameter(description = "Identifiant du produit") @PathParam(value = "productId") Long productId,
			byte[] data);

}