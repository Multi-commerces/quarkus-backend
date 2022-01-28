package fr.commerces.microservices.catalog.products.relationships.pricing;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.products.relationships.lang.ProductLangManager;
import fr.commerces.microservices.catalog.products.relationships.shipping.ProductPricingApi;
import fr.webmaker.data.product.ProductPricingData;
import fr.webmaker.data.product.ProductStockData;

@RequestScoped
public class ProductPricingResource extends JsonApiResource<ProductPricingData>	implements ProductPricingApi {

	@Inject
	ProductLangManager manager;

	public ProductPricingResource() {
		super(ProductPricingData.class);
	}

	@Override
	public Response getProductPricing(Long productId) {
//		final ProductStockData data = null;
//		return buildResponse(data, productId);
		return null;
	}

	@Override
	public Response patchProductPricing(Long productId, ProductStockData data) {
		// TODO : Méthode non implémentée
		return Response.noContent().build();
	}

}