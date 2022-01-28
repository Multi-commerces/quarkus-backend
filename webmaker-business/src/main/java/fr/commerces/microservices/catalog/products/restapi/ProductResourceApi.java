package fr.commerces.microservices.catalog.products.restapi;

import static fr.commerces.commons.resources.ConstApi.CODE200GET;
import static fr.commerces.commons.resources.ConstApi.CODE200PATCH;
import static fr.commerces.commons.resources.ConstApi.CODE201POST;
import static fr.commerces.commons.resources.ConstApi.CODE204DELETE;
import static fr.commerces.commons.resources.ConstApi.CODE404DELETE;
import static fr.commerces.commons.resources.ConstApi.MEDIA_JSON_API;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
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

import fr.commerces.microservices.catalog.products.model.ProductRelation;
import fr.webmaker.data.product.ProductData;
import fr.webmaker.restfull.hateos.schema.IShemaData;
import fr.webmaker.restfull.model.PageRequest;

/**
 * Interface resource API pour les produits
 * 
 * @author Julien ILARI
 *
 */
@Path(ProductResourceApi.PATH)
@Produces(MEDIA_JSON_API)
@Consumes(MEDIA_JSON_API)
@Tag(name = "Ressource Produits", description = "Ressource de gestion des produits")
public interface ProductResourceApi {

	public static final String PATH = "/products";
	
	public static final String PATH_ID = "productId";
	public static final String PARAM_ID_DESC = "Identifiant du produit";
	

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
					description = "[OK] - Opération de recherche effectuée avec succès", 
					content = @Content(mediaType = MEDIA_JSON_API, schema = @Schema(implementation = ShemaCollectionData.class))), })
	@GET @Path("/")
	Response getJsonApiProducts(
		@BeanParam @Valid PageRequest page,
		@Parameter(description = "Inclusion de ressources connexes")
		@QueryParam(value = "include")  List<ProductRelation> include,
		@QueryParam(value = "includes")  ProductRelation[] includes,
		@Parameter(description = "Liste des relations à inclure dans les données de la ressource principale", allowEmptyValue = false)
		@QueryParam(value = "relationships") 
		List<ProductRelation> relationships);

	
	/*
	 * #############################################################################
	 */

	@Operation(operationId = "getProductById", summary = "Recherche un produit", description = "Opération de recherche d'un produit")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", 
					description = "[OK] - Opération de recherche effectuée avec succès", 
					content = @Content(mediaType = MEDIA_JSON_API, schema = @Schema(implementation = ShemaSingleData.class))),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun porduit trouvé avec les critères de recherche") })
	@GET @Path("/{" + PATH_ID + "}")
	Response getProductById(@Parameter(description = PARAM_ID_DESC, example = "10000003") @PathParam("productId") long id,
			@QueryParam(value = "relationships") 
			@Parameter(description = "Liste des relations à inclure dans la réponse", example = "LANGS", allowEmptyValue = false) 
			@NotEmpty List<ProductRelation> relationships);

	/*
	 * #############################################################################
	 */

	@Operation(operationId = "createProduct", 
			summary = "Création d'un produit", 
			description = "Opération de création d'un nouveau produit")
	@APIResponses(value = { @APIResponse(responseCode = "201", description = CODE201POST)})
	@POST @Path("/")
	Response createProduct(@RequestBody(content = {
			@Content(schema = @Schema(implementation = ShemaSingleData.class)) }) byte[] flux);
	
	@Operation(operationId = "patchProduct", 
			summary = "Mise à jour partielle d'un produit", 
			description = "Opération de modification (partielle) d'un produit existant")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = CODE200PATCH)})
	@PATCH
	@Path("/{" + PATH_ID + "}")
	Response patchProduct(
			@Parameter(description = PARAM_ID_DESC) @PathParam(value = "productId") long productId,
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
	@DELETE @Path("/{" + PATH_ID + "}")
	void deleteProduct(@Parameter(description = PARAM_ID_DESC) @PathParam(value = "productId") long productId);
	
	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
