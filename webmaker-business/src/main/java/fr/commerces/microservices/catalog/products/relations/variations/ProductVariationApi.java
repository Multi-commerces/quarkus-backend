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

package fr.commerces.microservices.catalog.products.relations.variations;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;

import fr.commerces.commons.resources.ConstApi;
import fr.webmaker.data.product.ProductVariationData;

@Path(ProductVariationApi.PATH)
@Produces(ConstApi.MEDIA_JSON_API)
@Consumes(ConstApi.MEDIA_JSON_API)
@Tag(name = "Ressource Produits - Variations", description = "Ressource de gestion des variations produit")
public interface ProductVariationApi {

	public static final String PATH = "/products/{productId}/variations";

	@Path("/")
	@GET
	@Operation(summary = "Rechercher variations du produit", 
		description = "Opération de recherche les variations du produit.", 
		operationId = "getVariations")
	Response getVariations(@PathParam(value = "productId") Long productId) throws DocumentSerializationException;

	@Path("/")
	@POST
	@Operation(summary = "Ajouter variation produit", 
		description = "Opération de création d'une nouvelle variation du produit .", 
		operationId = "createVariation")
	Response createVariation(@Context UriInfo uriInfo, @PathParam(value = "productId") Long productId,
			ProductVariationData variation);

	@PUT
	@Path("/{variationId}")
	@Operation(summary = "Modification variation produit", 
		description = "Opération de modification d'une variation existante du produit .", 
		operationId = "updateVariation")
	Response updateVariation(@PathParam(value = "productId") Long productId,
			@PathParam(value = "variationId") Long variationId, ProductVariationData variation);



	@DELETE
	@Path("/{variationId}")
	@Operation(summary = "Supprimer variation produit", description = "Opération de suppression de la variation du produit .", operationId = "deleteVariations")
	Response deleteVariations(@PathParam(value = "productId") Long productId, @PathParam(value = "variationId") Long id);

}
