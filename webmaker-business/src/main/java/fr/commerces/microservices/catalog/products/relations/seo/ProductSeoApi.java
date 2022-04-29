package fr.commerces.microservices.catalog.products.relations.seo;

import static fr.commerces.commons.resources.ConstApi.MEDIA_JSON_API;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
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

import com.neovisionaries.i18n.LanguageCode;

@Path(ProductSeoApi.PATH)
@Produces(MEDIA_JSON_API)
@Consumes(MEDIA_JSON_API)
@Tag(name = "Ressource Produits - SEO (Search Engine Optimization)", description = "Ressource pour la gestion SEO des produits (multi-langues)")
public interface ProductSeoApi {
	
	public static final String PATH = "/products/{productId}/languages/{languageCode}/seo";

	/**
	 * GET Resource Produits - SEO
	 * @param productId
	 * @return
	 */
	@GET @Path("/")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK] - Opération de recherche effectuée avec succès"),
			@APIResponse(responseCode = "401", description = "[NOK] - Une identification est nécessaire"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun produit ne correspond aux paramètres fournis")
	})
	@Operation(operationId = "getProductSeos", 
		summary = "Informations SEO d'un produit (dans toutes les langues).", 
		description = "Rechercher les informations SEO d'un produit et dans toutes les langues.")
	Response getProductSeos(
			/*
			 * Identifiant du produit
			 */
			@PathParam("productId") long productId,
			/*
			 * Langue
			 */
			@PathParam(value = "languageCode") 
			@DefaultValue("undefined")
			LanguageCode languageCode
			);
	
	@Path("/relationships")
	@GET
	Response getRelationShipsProductSeo(@Parameter(description = "Identifiant du produit") 
	@PathParam(value = "productId") long productId,
	@PathParam(value = "languageCode") 
	@DefaultValue("undefined")
	LanguageCode languageCode);
	
	/**
	 * GET Resource Produits - SEO
	 * @param languageCode
	 * @param productId
	 * @return
	 */
	@GET
	@Path("/")
	@Operation(operationId = "getProductSeo", 
		summary = "Recherche les informations SEO d'un produit (dans une seule langue).", 
		description = "Retourne les informations SEO propre au produit pour une langue.")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK] - Opération de recherche effectuée avec succès"),
			@APIResponse(responseCode = "401", description = "[NOK] - Une identification est nécessaire"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun produit trouvé avec les critères de sélection fournis") 
	})
	Response getProductSeo(	
			/*
			 * Identifiant
			 */
			@Parameter(description = "Identifiant du produit") 
			@PathParam(value = "productId") long productId,
			/*
			 * language
			 */
			@Parameter(description = "Code de la langue") 
			@PathParam("languageCode") 
			@DefaultValue("fr") LanguageCode languageCode
			);
	
	/**
	 * PUT Resource Produits - SEO
	 * @param languageCode
	 * @param productId
	 * @param seo
	 * @return
	 */
	@PATCH
	@Path("/")
	@Operation(operationId = "updateProductSeo", 
		summary = "Modification SEO produit (dans une seule langue)", 
		description = "Demande la modification SEO d'un produit.")
	@APIResponses(value = { 
			@APIResponse(responseCode = "204", description = "[OK] - Opération d'enregistrement effectuée avec succès"),
			@APIResponse(responseCode = "401", description = "[NOK] - Une identification est nécessaire"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun produit trouvé avec les critères de sélection fournis") 
	})
	Response patchProductSeo(
			/*
			 * Identifiant
			 */
			@Parameter(description = "Identifiant du produit") 
			@PathParam(value = "productId") long productId,
			/*
			 * language
			 */
			@Parameter(description = "Code de la langue") 
			@PathParam("languageCode") 
			@DefaultValue("fr") LanguageCode languageCode,
			/*
			 * SEO Data
			 */
			byte[] seo);

}
