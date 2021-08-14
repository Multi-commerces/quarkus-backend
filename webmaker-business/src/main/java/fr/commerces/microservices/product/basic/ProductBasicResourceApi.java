package fr.commerces.microservices.product.basic;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
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

import fr.webmaker.product.data.ProductBasicData;

/**
 * Interface resource API pour les produits
 * @author Julien ILARI
 *
 */
@Path(ProductBasicResourceApi.PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Resource Produits - Données de base", description = "Resource de gestion des produits")
public interface ProductBasicResourceApi {

	public static final String PATH = "/products/{productId}/languages/{languageCode}/basic";

	@Path("/")
	@GET
	@Operation(operationId = "getProductBasicById", summary = "Recherche un produit", description = "Retourne les informations du produit.")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK] - Opération de recherche effectuée avec succès"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun porduit trouvé avec les paramètres fournis") 
	})
	Response getProductBasicById(
			/*
			 * Langue
			 */
			@Parameter(description = "Langue du produit") 
			@PathParam("languageCode") 
			@DefaultValue("fr") String languageCode,
			/*
			 * Identifiant
			 */
			@Parameter(description = "Identifiant du produit") 
			@PathParam("productId") Long id
			);


	
	@PATCH
	@Path("/")
	@Operation(operationId = "updateProduct", summary = "Modification de base produit", description = "Opération de modification des informations de base d'un produit existant.")
	@APIResponses(value = { 
			@APIResponse(responseCode = "204", description = "[OK] - Opération de mise à jour effectuée avec succès"),
			@APIResponse(responseCode = "404", description = "[NOK] - Porduit non trouvé avec les critères de recherche") 
	})
	public Response patchProductBasic(	
			/*
			 * language
			 */
			@Parameter(description = "Code de la langue") 
			@PathParam("languageCode") 
			@DefaultValue("fr") String languageCode,
			/*
			 * Identifiant
			 */
			@Parameter(description = "Identifiant du produit") 
			@PathParam(value = "productId") Long productId, 
			/*
			 * Data
			 */
			@NotNull @Valid ProductBasicData data);

	
	

}
