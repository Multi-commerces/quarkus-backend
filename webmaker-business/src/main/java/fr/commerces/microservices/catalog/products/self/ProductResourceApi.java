package fr.commerces.microservices.catalog.products.self;

import static fr.commerces.commons.resources.ConstApi.CODE200GET;
import static fr.commerces.commons.resources.ConstApi.CODE201POST;
import static fr.commerces.commons.resources.ConstApi.CODE204DELETE;
import static fr.commerces.commons.resources.ConstApi.CODE20XPATCH;
import static fr.commerces.commons.resources.ConstApi.CODE404DELETE;
import static fr.commerces.commons.resources.ConstApi.MEDIA_JSON_API;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import fr.commerces.commons.resources.ConstApi;
import fr.commerces.microservices.catalog.products.self.model.ProductInclude;
import fr.commerces.microservices.catalog.products.self.model.ProductLanguageCode;
import fr.webmaker.data.product.ProductData;
import fr.webmaker.restfull.hateos.schema.IShemaData;
import fr.webmaker.restfull.model.PageRequest;

/**
 * Interface - API pour les produits
 * 
 * @author Julien ILARI
 *
 */
@Produces(ConstApi.MEDIA_JSON_API)
@Consumes(ConstApi.MEDIA_JSON_API)
@Path("/products")
@Tag(name = "Ressource Produits", description = "Ressource de gestion des produits")
public interface ProductResourceApi {
	
	public static String PATCH = "/products";
	
	interface ShemaCollectionData {
		IShemaData<ProductData>[] getData();
	}
	
	public abstract class ShemaSingleData  {
		public abstract IShemaData<ProductData> getData();
	}
	
	/*
	 * #############################################################################
	 */

	@Operation(operationId = "getJsonApiProducts", summary = "Recherche les produits", description = CODE200GET)
	@APIResponses(value = {
			@APIResponse(responseCode = "200",
					description = CODE200GET, 
					content = @Content(mediaType = MEDIA_JSON_API, 
					schema = @Schema(implementation = ShemaCollectionData.class))), })
	@GET @Path("/")
	Response getJsonApiProducts(
		
		@BeanParam @Valid PageRequest page,
		
		@Parameter(allowEmptyValue = true,
				description = "Code de langue (code ISO à 2 lettres)")
		@QueryParam("lang") 
		List<ProductLanguageCode> languageCode,
		
		@Parameter(description = "Liste des relations à inclure dans les données de la ressource principale", allowEmptyValue = true)
		@QueryParam(value = "include") 
		List<ProductInclude> include);
	
	/*
	 * #############################################################################
	 */

	@Operation(operationId = "getProductById", summary = "Recherche un produit", description = "Opération de recherche d'un produit")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", 
					description = "[OK] - Opération de recherche effectuée avec succès", 
					content = @Content(mediaType = MEDIA_JSON_API,
					schema = @Schema(implementation = ShemaSingleData.class))),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun porduit trouvé avec les critères de recherche") })
	@GET @Path("/{productId}")
	Response getProductById(
		@Parameter(description = "Identifiant du produit", example = "10000003") 
		@PathParam("productId") Long id,
		@QueryParam(value = "relationships") 
		@Parameter(description = "Liste des relations à inclure dans la réponse", example = "LANGS", allowEmptyValue = true) 
		List<ProductInclude> relationships);

	/*
	 * #############################################################################
	 */

	@POST 
	@Path("/")
	@Operation(operationId = "createProduct", summary = "Création d'un produit", 
			description = "Opération de création d'un nouveau produit (langue par défaut des produit)")
	@APIResponses(value = { @APIResponse(responseCode = "201", description = CODE201POST)})
	Response createProduct(@RequestBody(content = {
			@Content(schema = @Schema(implementation = ShemaSingleData.class)) }) byte[] flux);
	
	
	@PATCH
	@Path("/{productId}")
	@Operation(operationId = "patchProduct", summary = "Mise à jour partielle d'un produit", 
			description = "Opération de modification (partielle) d'un produit existant")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = CODE20XPATCH)})
	Response patchProduct(
			@Parameter(description = "Identifiant du produit") @PathParam(value = "productId") long productId,
			@RequestBody(content = {
					@Content(schema = @Schema(implementation = ShemaSingleData.class)) }) byte[] flux);

	/*
	 * #############################################################################
	 */

	@Operation(operationId = "deleteProduct", 
			summary = "Suppression d'un produit", 
			description = "Opération de suppression d'un produit existant avec toutes les relations")
	@APIResponses(value = { @APIResponse(responseCode = "204", description = CODE204DELETE),
			@APIResponse(responseCode = "404", description = CODE404DELETE) })
	@DELETE @Path("/{" + "productId" + "}")
	void deleteProduct(@Parameter(description = "Identifiant du produit") @PathParam(value = "productId") long productId);


	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
