package fr.commerces.microservices.catalog.products.relationships.pricing;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.products.manager.ProductManager;
import fr.commerces.microservices.catalog.products.model.ProductRelation;
import fr.webmaker.data.product.ProductPricingData;

public class ProductPricingResource extends JsonApiResource<ProductPricingData>	implements ProductPricingApi {

	@Inject
	ProductManager manager;
	
	@Inject
	ProductPricingMapper mapper;

	public ProductPricingResource() {
		super(ProductPricingData.class);
	}

	@Override
	public Response getProductPricing(Long productId) {
		return writeJsonApiResponse(manager.findById(productId, List.of(ProductRelation.PRICING)).getPricing());
	}

	@Transactional
	@Override
	public Response patchProductPricing(Long productId, byte[] data) {
		mapper.unwrap(readData(data), productId);
		return Response.noContent().build();
	}

}