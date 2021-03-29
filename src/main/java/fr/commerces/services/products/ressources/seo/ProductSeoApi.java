/*
 * Copyright 2020 Lunatech S.A.S
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.commerces.services.products.ressources.seo;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

import fr.commerces.services._transverse.GenericResponse;
import fr.commerces.services.products.data.ProductSeoData;

@Path("/products/{productId}/seo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Resource Produits SEO (Search Engine Optimization)", description = "Resource de gestion des produits (SEO)")
public interface ProductSeoApi {

	/* ############################################################################################################# */
	
	@GET
	@Path("/")
	@Operation(operationId = "getProductSeoById", 
		summary = "Recherche les informations SEO d'un produit (dans toutes les langues).", 
		description = "Retourne les informations SEO du produit.")
	@Tag(ref = "Resource Produits SEO")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK] - Opération de recherche effectuée avec succès"),
			@APIResponse(responseCode = "401", description = "[NOK] - Une identification est nécessaire"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun produit ne correspond aux paramètres fournis") 
	})
	GenericResponse<ProductSeoData, Long> getProductSeoById(
			/*
			 * Identifiant du produit
			 */
			@PathParam("productId") Long productId
			);

	/* ############################################################################################################# */
	
	@GET
	@Path("/languages/{languageCode}")
	@Operation(operationId = "getProductSEOs", 
		summary = "Recherche les informations SEO d'un produit (dans une seule langue).", 
		description = "Retourne les informations du SEO produit.")
	@Tag(ref = "Resource Produits SEO")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK] - Opération de recherche effectuée avec succès"),
			@APIResponse(responseCode = "401", description = "[NOK] - Une identification est nécessaire"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun produit trouvé avec les critères de sélection fournis") 
	})
	Collection<GenericResponse<ProductSeoData, Long>> getProductSEOs(
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
			@PathParam(value = "productId") Long productId
			);

	/* ############################################################################################################# */
	
	@POST
	@Path("/languages/{languageCode}")
	@Operation(operationId = "createProductSeo", 
		summary = "Création SEO produit (dans une seule langue)", 
		description = "Demande la création SEO pour le produit.")
	@Tag(ref = "Resource Produits SEO")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK] - Opération d'enregistrement effectuée avec succès"),
			@APIResponse(responseCode = "401", description = "[NOK] - Une identification est nécessaire"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun produit trouvé avec les critères de sélection fournis"),
			@APIResponse(responseCode = "409", description = "[NOK] - Un SEO existe déjà pour le produit sur la langue cible") 
	})
	Response createProductSeo(
			@NotNull @Valid ProductSeoData data);

	/* ############################################################################################################# */
	
	@PUT
	@Path("/languages/{languageCode}")
	@Operation(operationId = "updateProductSeo", 
		summary = "Modification SEO produit (dans une seule langue)", 
		description = "Demande la modification SEO d'un produit.")
	@Tag(ref = "Resource Produits SEO")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK] - Opération d'enregistrement effectuée avec succès"),
			@APIResponse(responseCode = "401", description = "[NOK] - Une identification est nécessaire"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun produit trouvé avec les critères de sélection fournis") 
	})
	Response updateProductSeo(
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
			 * SEO Data
			 */
			@NotNull @Valid ProductSeoData seo);

}
