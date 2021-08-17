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

package fr.commerces.microservices.catalog.products.deliveries;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
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

import fr.webmaker.microservices.catalog.products.data.ProductShippingData;



@Path(ProductShippingApi.PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Resource Produits - Expédition", description = "Resource pour la gestion d'expédition du produit")
public interface ProductShippingApi {
	
	enum INCLUDE { 
		NONE, TRANSPORTERS;  
	}

	public static final String PATH = "/products/{productId}/shipping";	
	
	/**
	 * GET Resource - Expédition produit
	 * @param productId
	 * @return
	 */
	@GET @Path("/")
	@Operation(operationId = "getProductShipping", 
		summary = "Recherche les informations d'expédition d'un produit.", 
		description = "Retourne les informations d'expédition du produit.")
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK] - Opération de recherche effectuée avec succès"),
			@APIResponse(responseCode = "401", description = "[NOK] - Une identification est nécessaire"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun transporteur ne correspond aux paramètres fournis") 
	})
	Response getProductShipping(
			@Parameter(description = "Identifiant du produit") 
			@PathParam("productId") Long productId, 
			
			@Parameter(description = "Inclure plus de donées que seulement un identifiant (valeur possible : transporters)") 
			@DefaultValue("NONE") @QueryParam(value = "include") INCLUDE include
			);

	/* ############################################################################################################# */
	
	
	@PATCH
	@Path("/")
	@Operation(operationId = "patchProductShipping", summary = "Modification expédition produit", description = "Opération de modification des informations de base d'un produit existant.")
	@APIResponses(value = { 
			@APIResponse(responseCode = "204", description = "[OK] - Opération d'enregistrement effectuée avec succès"),
			@APIResponse(responseCode = "401", description = "[NOK] - Une identification est nécessaire"),
			@APIResponse(responseCode = "404", description = "[NOK] - Aucun transporteur ne correspond aux paramètres fournis") 
	})
	Response patchProductShipping(/*
			 * Identifiant
			 */
			@Parameter(description = "Identifiant du produit") 
			@PathParam(value = "productId") Long productId, 
			ProductShippingData data);

}