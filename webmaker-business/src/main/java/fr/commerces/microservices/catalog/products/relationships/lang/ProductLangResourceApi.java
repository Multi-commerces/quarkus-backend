package fr.commerces.microservices.catalog.products.relationships.lang;

import static fr.commerces.commons.resources.ConstApi.CODE200GET;
import static fr.commerces.commons.resources.ConstApi.CODE201POST;
import static fr.commerces.commons.resources.ConstApi.CODE204DELETE;
import static fr.commerces.commons.resources.ConstApi.CODE204PUT;
import static fr.commerces.commons.resources.ConstApi.CODE404DELETE;
import static fr.commerces.commons.resources.ConstApi.CODE404GET;
import static fr.commerces.commons.resources.ConstApi.MEDIA_JSON_API;

import java.util.List;

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
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.LanguageCode;

import fr.webmaker.data.product.ProductLangData;
import fr.webmaker.restfull.hateos.schema.IShemaData;

/**
 * Interface resource API pour les produits
 * @author Julien ILARI
 *
 */
@Path(ProductLangResourceApi.PATH)
@Produces(MEDIA_JSON_API)
@Consumes(MEDIA_JSON_API)
@Tag(name = "Ressource Produits - Langues", description = "Ressource de gestion sur la traduction des produits")
public interface ProductLangResourceApi {

	public static final String PATH = "/products/{productId}/languages";
	
	interface ShemaCollectionData {
		@JsonProperty("data")
		List<IShemaData<ProductLangData>> getData();
	}
	
	interface ShemaSingleData {
		@JsonProperty("data")
		IShemaData<ProductLangData> getData();
	}
	
	/* ############################################################################################################# */

		
	@Operation(operationId = "getProductLangById", 
		summary = "Recherche un produit dans une langue", 
		description = "Retourne les informations du produit portant sur une langue.")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = CODE200GET,
					content = @Content(schema = @Schema(implementation = ShemaCollectionData.class))),					
			@APIResponse(responseCode = "404", description = CODE404DELETE) 
	})
	@GET @Path("/{languageCode}")
	byte[] getProductLangById(
			/*
			 * Langue 
			 */
			@Parameter(description = "Langue du produit") 
			@PathParam("languageCode") 
			@DefaultValue("fr") 
			@NotNull LanguageCode languageCode,
			/*
			 * Identifiant
			 */
			@Parameter(description = "Identifiant du produit") 
			@PathParam("productId") Long id);

	/* ############################################################################################################# */

	@GET
	@Path("/") 
	@Operation(operationId = "getProductLangs", 
	summary = "Recherche les produits", 
		description = "Retourne les informations des produits dans un langue précise (par défaut celle du client).")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = CODE200GET),
			@APIResponse(responseCode = "404", description = CODE404GET) 
	})
	byte[] getProductLangs(
			@Parameter(description = "Identifiant du produit") 
			@PathParam("productId") @NotNull Long productId);

	/* ############################################################################################################# */

//	@RolesAllowed({ "gestionnaire" })
	@POST
	@Path("/{languageCode}") 
	@Operation(operationId = "createProductLang", summary = "Création d'un produit", description = "Opération de création d'un nouveau produit .")
	@APIResponses(value = { 
			@APIResponse(responseCode = "201", description = CODE201POST) 
	})
	Response createProductLang(
			/*
			 * language
			 */
			@Parameter(description = "Code de la langue") 
			@PathParam("languageCode") 
			@DefaultValue("fr") LanguageCode languageCode,
			/*
			 * Identifiant
			 */
			@Parameter(description = "Identifiant du produit") 
			@PathParam(value = "productId") Long productId, 
			/*
			 * Product flux JSON:API
			 */
			@RequestBody(content = {
					@Content(schema = @Schema(implementation = ShemaSingleData.class)) }) byte[] flux);

	/* ############################################################################################################# */
	
	@PUT
	@Path("/{languageCode}")
	@Operation(operationId = "putProductLang", 
		summary = "Modification d'un produit", 
		description = "Opération de modification d'un produit existant.")
	@APIResponses(value = { 
			@APIResponse(responseCode = "204", description = CODE204PUT) 
	})
	Response putProductLang(
			/*
			 * language
			 */
			@Parameter(description = "Code de la langue") 
			@PathParam("languageCode") 
			@DefaultValue("fr") LanguageCode languageCode,
			/*
			 * Identifiant
			 */
			@Parameter(description = "Identifiant du produit") 
			@PathParam(value = "productId") Long productId, 
			/*
			 * Data
			 */
			@NotNull @Valid ProductLangData data);

	
	@DELETE
	@Path("/{languageCode}")
	@Operation(operationId = "deleteProductLang", 
		summary = "Suppression d'un produit", 
		description = "Opération de suppression d'un produit existant dans une langue spécifique.")
	@APIResponses(value = { 
			@APIResponse(responseCode = "204", description = CODE204DELETE),
			@APIResponse(responseCode = "404", description = CODE404DELETE)
	})
	Response deleteProductLang(
			/*
			 * language
			 */
			@Parameter(description = "Code de la langue") 
			@PathParam("languageCode") 
			@DefaultValue("fr") LanguageCode languageCode,
			/*
			 * Identifiant
			 */
			@Parameter(description = "Identifiant du produit") 
			@PathParam(value = "productId") Long productId);	

}
