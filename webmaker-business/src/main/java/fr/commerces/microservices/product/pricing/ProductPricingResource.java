package fr.commerces.microservices.product.pricing;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import fr.commerces.commons.resources.GenericResource;
import fr.commerces.microservices.product.ProductManager;
import fr.webmaker.common.Identifier;
import fr.webmaker.common.response.CollectionResponse;
import fr.webmaker.product.data.ProductStockData;

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