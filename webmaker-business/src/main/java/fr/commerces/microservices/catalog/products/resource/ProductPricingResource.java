package fr.commerces.microservices.catalog.products.resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import fr.commerces.commons.resources.GenericResource;
import fr.commerces.microservices.catalog.products.manager.ProductManager;
import fr.commerces.microservices.catalog.products.openapi.ProductPricingApi;
import fr.webmaker.commons.identifier.Identifier;
import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.microservices.catalog.products.data.ProductStockData;

@RequestScoped
public class ProductPricingResource extends GenericResource<CollectionResponse<ProductStockData, Identifier<Long>>>
		implements ProductPricingApi {

	@Inject
	ProductManager manager;

	@Override
	public Response getProductPricing(Long productId) {
		// TODO : Méthode non implémentée
		final ProductStockData data = null;
		return buildResponse(data, productId);
	}

	@Override
	public Response patchProductPricing(Long productId, ProductStockData data) {
		// TODO : Méthode non implémentée
		return Response.noContent().build();
	}

}