package fr.commerces.services.products.ressources.products;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
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

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.services._transverse.GenericResponse;
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

	/**
	 * Opération de recherche par identifant
	 * @param acceptLanguage
	 * @param id
	 * @return
	 */
	@Path("/{productId}")
	@GET
	@Operation(operationId = "getProductById", summary = "Recherche un produit", description = "Retourne les informations du produit.")
	@Tag(ref = "Resource Produits")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = "Produit trouvé"),
			@APIResponse(responseCode = "404", description = "Aucun porduit trouvé avec l'identifiant fourni") })
	GenericResponse<ProductData, Long> getProductById(
			@Parameter(description = "Langue du produit (par défaut langue du client)") 
			@QueryParam("lang") LanguageCode acceptLanguage,
			/*
			 * Identifiant
			 */
			@Parameter(description = "Identifiant du produit") 
			@PathParam("productId") Long id);

	/* ############################################################################################################# */
	
	/**
	 * Opération de recherche
	 * @param language
	 * @param page
	 * @param size
	 * @return
	 */
	@GET
	@Path("/") 
	@Operation(operationId = "getProducts", summary = "Recherche un produit", 
		description = "Retourne les informations des produits dans un langue précise (par défaut celle du client).")
	@Tag(ref = "Resource Produits")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "Produits trouvés"),
			@APIResponse(responseCode = "404", description = "Aucun porduit trouvé avec les critères de recherche") 
	})
	Collection<GenericResponse<ProductData, Long>> getProducts(
			/*
			 * language
			 */
			@Parameter(description = "Langue des produits (par défaut langue du client)") 
			@QueryParam("lang")  Optional<LanguageCode> language,
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
	
	/**
	 * Opération de création
	 * @param language
	 * @param data
	 * @return
	 */
	@RolesAllowed({ "gestionnaire" })
	@POST
	@Path("/") 
	@Operation(operationId = "createProduct", summary = "Création produit", description = "Demande la création d'un nouveau produit .")
	@Tag(ref = "Resource Produits")
	@APIResponses(value = { @APIResponse(responseCode = "201", description = "Création du produit [OK]") })
	Response createProduct(ProductData data);

	/* ############################################################################################################# */
	
	/**
	 * Opération de mise à jour
	 * @param productId
	 * @param variation
	 * @return
	 */
	@RolesAllowed({ "gestionnaire" })
	@PUT
	@Path("/{productId}")
	@Operation(operationId = "updateProduct", summary = "Modification produit", description = "Demande la modification d'un produit existant.")
	@Tag(ref = "Resource Produits")
	Response updateProduct(
			/*
			 * Identifiant
			 */
			@Parameter(description = "Identifiant du produit") 
			@PathParam(value = "productId") Long productId, 
			ProductData variation);

}
