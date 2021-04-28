package fr.commerces.services.products.ressources.products;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import fr.commerces.services._transverse.response.CollectionResponse;
import fr.commerces.services.products.data.ProductData;

/**
 * Interface resource API pour les produits
 * @author Julien ILARI
 *
 */
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Resource Produits", description = "Resource de gestion des produits")
public interface ProductResourceApi {

	@Path("/{productId}/languages/{languageCode}")
	@GET
	@Operation(operationId = "getProductById", summary = "Recherche un produit", description = "Retourne les informations du produit.")
	@Tag(ref = "Resource Produits")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK] - Opération de recherche effectuée avec succès"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun porduit trouvé avec les paramètres fournis") 
	})
	Response getProductById(
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
			@PathParam("productId") Long id);

	/* ############################################################################################################# */

	@GET
	@Path("/") 
	@Operation(operationId = "getProducts", summary = "Recherche un produit", 
		description = "Retourne les informations des produits dans un langue précise (par défaut celle du client).")
	@Tag(ref = "Resource Produits")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK] - Opération de recherche effectuée avec succès"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun porduit trouvé avec les critères de recherche") 
	})
	CollectionResponse<ProductData, Long> getProducts(
			/*
			 * language
			 */
			@Parameter(description = "Langue des produits (langue par défaut 'français')") 
			@QueryParam("languageCode") 
			@DefaultValue("fr") 
			@NotNull String languageCode,
			/*
			 * page
			 */
			@Parameter(description = "Numéro de page") 
			@QueryParam(value = "page") 
			@DefaultValue("1") 
			@NotNull Integer page,
			/*
			 * size
			 */
			@Parameter(description = "Taille de la page (min 1 et max 100)") 
			@QueryParam(value = "size") 
			@DefaultValue("20") 
			@NotNull Integer size);

	/* ############################################################################################################# */

	@RolesAllowed({ "gestionnaire" })
	@POST
	@Path("/languages/{languageCode}") 
	@Operation(operationId = "createProduct", summary = "Création produit", description = "Demande la création d'un nouveau produit .")
	@Tag(ref = "Resource Produits")
	@APIResponses(value = { 
			@APIResponse(responseCode = "201", description = "[OK] - Opération de création effectuée avec succès") 
	})
	Response createProduct(
			/*
			 * language
			 */
			@Parameter(description = "Code de la langue") 
			@PathParam("languageCode") 
			@DefaultValue("fr") String languageCode,
			/*
			 * Product Data
			 */
			@NotNull @Valid ProductData data);

	/* ############################################################################################################# */

	@RolesAllowed({ "gestionnaire" })
	@PUT
	@Path("/{productId}/languages/{languageCode}")
	@Operation(operationId = "updateProduct", summary = "Modification produit", description = "Demande la modification d'un produit existant.")
	@Tag(ref = "Resource Produits")
	Response updateProduct(
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
			@NotNull @Valid ProductData data);

	@RolesAllowed({ "gestionnaire" })
	@DELETE
	@Path("/{productId}/languages/{languageCode}")
	@Operation(operationId = "deleteProductLang", summary = "Suppression produit d'une langue", description = "Demande la suppression d'un produit existant dans une langue spécifique.")
	@Tag(ref = "Resource Produits")
	@APIResponses(value = { 
			@APIResponse(responseCode = "204", description = "[OK] - Opération de suppression effectuée avec succès"),
			@APIResponse(responseCode = "404", description = "[NOK] - Suppression du produit impossible car introuvable")
	})
	Response deleteProductLang(
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
			@PathParam(value = "productId") Long productId);

}
