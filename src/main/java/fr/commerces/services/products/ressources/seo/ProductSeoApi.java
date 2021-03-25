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

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import fr.commerces.services.products.data.ProductData;

@Path("/products/{productId}/seos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Resource SEO", description = "Resource de gestion SEO des produits")
public interface ProductSeoApi {

	@GET
	@Path("/{productId}")
	@Operation(operationId = "getProductSeoById", summary = "Recherche SEO (Search Engine Optimization)", description = "Retourne les informations du produit.")
	@Tag(ref = "Resource Produit SEO")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = "SEO du Produit trouvé"),
			@APIResponse(responseCode = "404", description = "Aucun SEO trouvé pour le produit fourni") })
	ProductData getProductSeoById(@PathParam("productId") Long id);

	@GET
	@Path("/{productId}")
	@Operation(operationId = "getProductSEOs", summary = "Recherche les SEO (toutes les langues) d'un produit.", description = "Retourne les informations du SEO produit.")
	@Tag(ref = "Resource Produit SEO")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = "Produits trouvés"),
			@APIResponse(responseCode = "404", description = "Aucun porduit trouvé avec les critères de sélection fournis") })
	List<ProductData> getProductSEOs(@PathParam("productId") Long id);

	@POST
	@Operation(summary = "Création produit", description = "Demande la création d'un nouveau SEO pour le produit.", operationId = "createProductSeo")
	@Tag(ref = "Resource Produit SEO")
	@APIResponses(value = { @APIResponse(responseCode = "200", description = "Produit créé") })
	Response createProductSeo(@Valid ProductData data, @Context UriInfo uriInfo);

	@PUT
	@Path("/{productId}/seo/")
	@Operation(summary = "Modification SEO produit", description = "Demande la modification SEO d'un produit.", operationId = "updateProductSeo")
	@Tag(ref = "Resource Produit SEO")
	Response updateProductSeo(@PathParam(value = "productId") Long productId, @PathParam(value = "productSeoId") Long productSeoId,  ProductData variation);

}
