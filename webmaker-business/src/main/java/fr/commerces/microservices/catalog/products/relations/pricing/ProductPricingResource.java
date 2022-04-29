package fr.commerces.microservices.catalog.products.relations.pricing;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.products.entity.Product;
import fr.commerces.microservices.catalog.products.self.ProductManager;
import fr.commerces.microservices.catalog.products.self.model.ProductInclude;
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
		return writeResponse(manager.findById(productId, List.of(ProductInclude.PRICING)).getPricing());
	}

	@Transactional
	@Override
	public Response patchProductPricing(Long productId, byte[] data) {
		var value = Product.<Product>findByIdOptional(productId)
			.map(pojo -> mapper.unwrap(readData(data), productId))
			.map(mapper::wrap)
			.orElseThrow(NotFoundException::new);
		
		
		return writeResponse(value);
	}

}