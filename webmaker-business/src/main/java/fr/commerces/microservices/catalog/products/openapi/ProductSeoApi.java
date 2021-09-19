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

package fr.commerces.microservices.catalog.products.openapi;

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

import fr.webmaker.commons.identifier.Identifier;
import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.products.data.ProductSeoData;

@Path(ProductSeoApi.PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Resource Produits - SEO (Search Engine Optimization)", description = "Resource pour la gestion SEO des produits (multi-langues)")
public interface ProductSeoApi {
	
	public static final String PATH = "/products/{productId}/seo";

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
	CollectionResponse<ProductSeoData, Identifier<Long>> getProductSeos(
			/*
			 * Identifiant du produit
			 */
			@PathParam("productId") Long productId
			);
	
	/**
	 * GET Resource Produits - SEO
	 * @param languageCode
	 * @param productId
	 * @return
	 */
	@GET
	@Path("/languages/{languageCode}")
	@Operation(operationId = "getProductSeo", 
		summary = "Recherche les informations SEO d'un produit (dans une seule langue).", 
		description = "Retourne les informations SEO propre au produit pour une langue.")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK] - Opération de recherche effectuée avec succès"),
			@APIResponse(responseCode = "401", description = "[NOK] - Une identification est nécessaire"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun produit trouvé avec les critères de sélection fournis") 
	})
	SingleResponse<ProductSeoData, Identifier<Long>> getProductSeo(
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
	
	/**
	 * PUT Resource Produits - SEO
	 * @param languageCode
	 * @param productId
	 * @param seo
	 * @return
	 */
	@PATCH
	@Path("/languages/{languageCode}")
	@Operation(operationId = "updateProductSeo", 
		summary = "Modification SEO produit (dans une seule langue)", 
		description = "Demande la modification SEO d'un produit.")
	@APIResponses(value = { 
			@APIResponse(responseCode = "204", description = "[OK] - Opération d'enregistrement effectuée avec succès"),
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
