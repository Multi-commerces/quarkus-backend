package fr.commerces.microservices.catalog.products.pricing;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import fr.commerces.commons.resources.GenericResource;
import fr.commerces.microservices.catalog.products.lang.ProductLangManager;
import fr.commerces.microservices.catalog.products.shipping.ProductPricingApi;
import fr.commerces.microservices.catalog.products.stocks.ProductStockData;

@RequestScoped
public class ProductPricingResource extends GenericResource	implements ProductPricingApi {

	@Inject
	ProductLangManager manager;

	@Override
	public Response getProductPricing(Long productId) {
		// TODO : Méthode non implémentée
		final ProductStockData data = null;
//		return buildResponse(data, productId);
		return null;
	}

	@Override
	public Response patchProductPricing(Long productId, ProductStockData data) {
		// TODO : Méthode non implémentée
		return Response.noContent().build();
	}

}