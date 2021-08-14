package fr.commerces.microservices.product.stocks;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import fr.webmaker.product.data.ProductStockData;

@Path(ProductStockApi.PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Resource Produits - Stocks", description = "Resource de gestion des stocks du produit")
public interface ProductStockApi {
	
	public static final String PATH = "/products/{productId}/stocks";
	/**
	 * GET Resource
	 * @param productId
	 * @return
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
			ProductStockData data);

}