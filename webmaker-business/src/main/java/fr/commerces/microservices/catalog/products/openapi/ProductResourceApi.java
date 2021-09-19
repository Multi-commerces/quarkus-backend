package fr.commerces.microservices.catalog.products.openapi;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
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

import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.microservices.catalog.products.data.ProductData;
import fr.webmaker.microservices.catalog.products.id.ProductID;

/**
 * Interface resource API pour les produits
 * @author Julien ILARI
 *
 */
@Path(ProductResourceApi.PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Resource Produits", description = "Resource de gestion des produits")
public interface ProductResourceApi {

	public static final String PATH = "/languages/{languageCode}/products";
	
	@Path("/{productId}")
	@GET
	@Operation(operationId = "getProductById", summary = "Recherche un produit", description = "Retourne les informations du produit.")
	@Tag(ref = "Resource Produits")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK] - Opération de recherche effectuée avec succès"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun porduit trouvé avec les paramètres fournis") 
	})
	Response getProductById(
			/*
			 * Langue @HeaderParam(HttpHeaders.CONTENT_LANGUAGE) String contentLanguage
			 */
			@Parameter(description = "Langue du produit") 
			@PathParam("languageCode") 
			@DefaultValue("fr") String languageCode,
			/*
			 * Identifiant
			 */
			@Parameter(description = "Identifiant du produit") 
			@PathParam("productId") Long id,
			/*
			 * language
			 */
			@Parameter(description = "Permet d'indiquer d'inclure seulement les informations de base du produit") 
			@QueryParam("includeBasic") 
			@DefaultValue("true") 
			Boolean includeBasic
			);

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
	@RolesAllowed(value = { "ADMIN" })
	CollectionResponse<ProductData, ProductID> getProducts(
			/*
			 * language
			 */
			@Parameter(description = "Langue des produits (langue par défaut 'français')") 
			@PathParam("languageCode") 
			@DefaultValue("fr") 
			@NotNull
			String languageCode,
			/*
			 * page
			 */
			@Parameter(description = "Numéro de page") 
			@QueryParam(value = "page") 
			@DefaultValue("1") 
			Integer page,
			/*
			 * size
			 */
			@Parameter(description = "Taille de la page (min 1 et max 100)") 
			@QueryParam(value = "size") 
			@DefaultValue("20") 
			Integer size);

	/* ############################################################################################################# */

//	@RolesAllowed({ "gestionnaire" })
	@POST
	@Path("/") 
	@Operation(operationId = "createProduct", summary = "Création produit", description = "Opération de création d'un nouveau produit .")
	@Tag(ref = "Resource Produits")
	@APIResponses(value = { 
			@APIResponse(responseCode = "201", description = "[OK] - Opération de création effectuée avec succès") 
	})
	Response createProductLang(
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

//	@RolesAllowed({ "gestionnaire" })
	@PATCH
	@Path("/{productId}")
	@Operation(operationId = "updateProduct", summary = "Modification produit", description = "Opération de modification d'un produit existant.")
	@Tag(ref = "Resource Produits")
	@APIResponses(value = { 
			@APIResponse(responseCode = "204", description = "[OK] - Opération de mise à jour effectuée avec succès") 
	})
	Response patchProductLang(
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

	
//	@RolesAllowed({ "gestionnaire" })
	@DELETE
	@Path("/{productId}")
	@Operation(operationId = "deleteProductLang", summary = "Suppression produit d'une langue", description = "Opération de suppression d'un produit existant dans une langue spécifique.")
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
