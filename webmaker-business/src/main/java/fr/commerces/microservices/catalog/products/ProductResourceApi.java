package fr.commerces.microservices.catalog.products;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;
import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.microservices.catalog.products.lang.ProductLangData;
import fr.webmaker.commons.request.PageRequest;

/**
 * Interface resource API pour les produits
 * @author Julien ILARI
 *
 */
@Path(ProductResourceApi.PATH)
@Produces("application/vnd.api+json")
@Consumes("application/vnd.api+json")
@Tag(name = "Resource Produits", description = "Resource de gestion des produits")
public interface ProductResourceApi {

	public static final String PATH = "/products";
	
	@Path("/{productId}")
	@GET
	@Operation(operationId = "getProductById", summary = "Recherche un produit", description = "Retourne les informations du produit.")
	@Tag(ref = "Resource Produits")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK] - Opération de recherche effectuée avec succès"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun porduit trouvé avec les paramètres fournis") 
	})
	@Produces("application/vnd.api+json")
	@Consumes("application/vnd.api+json")
	byte[] getProductById(
			/*
			 * Langue @HeaderParam(HttpHeaders.CONTENT_LANGUAGE) String contentLanguage
			 */
			@Parameter(description = "Langue du produit") 
			@QueryParam("languageCode") 
			@DefaultValue("fr") 
			@NotNull LanguageCode languageCode,
			/*
			 * Identifiant
			 */
			@Parameter(description = "Identifiant du produit") 
			@PathParam("productId") Long id
			) throws DocumentSerializationException;

	/* ############################################################################################################# */

	@GET
	@Path("/") 
	@Operation(operationId = "getProducts", summary = "Recherche les produits", 
		description = "Retourne les informations des produits dans un langue précise (par défaut celle du client).")
	@Tag(ref = "Resource Produits")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK] - Opération de recherche effectuée avec succès"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun porduit trouvé avec les critères de recherche") 
	})

	@Produces("application/vnd.api+json")
	@Consumes("application/vnd.api+json")
	byte[] getProducts(
			/*
			 * language
			 */
			@Parameter(description = "Langue des produits (langue par défaut 'français')") 
			@QueryParam("languageCode") 
			@DefaultValue("fr") 
			@NotNull
			String languageCode,
			@BeanParam @Valid PageRequest page) throws DocumentSerializationException;

	/* ############################################################################################################# */

//	@RolesAllowed({ "gestionnaire" })
	@POST
	@Operation(operationId = "createProduct", summary = "Création d'un produit", description = "Opération de création d'un nouveau produit .")
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
			@NotNull @Valid ProductLangData data);

	/* ############################################################################################################# */

	
	@DELETE
	@Path("/{productId}")
	@Operation(operationId = "deleteProductLang", summary = "Suppression (douce) d'un produit", description = "Opération de suppression d'un produit existant dans une langue spécifique.")
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
			@QueryParam("languageCode") 
			@DefaultValue("fr") String languageCode,
			/*
			 * Identifiant
			 */
			@Parameter(description = "Identifiant du produit") 
			@PathParam(value = "productId") Long productId);
	
	
	@Operation(
			operationId = "getCategories", 
			summary = "Recherche les catégories du produit", 
			description = "Retourne les informations des catégories.")
	@Path("/{productId}/categories")
	@GET
	byte[] getCategories(@PathParam("productId") @NotNull Long productId) throws DocumentSerializationException;
	

}
