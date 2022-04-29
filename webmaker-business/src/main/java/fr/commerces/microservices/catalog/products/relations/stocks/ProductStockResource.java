package fr.commerces.microservices.catalog.products.relations.stocks;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.commerces.commons.resources.JsonApiResource;
import fr.webmaker.data.product.ProductStockData;

@RequestScoped
public class ProductStockResource extends JsonApiResource<ProductStockData> implements ProductStockApi {

	@Inject
	ProductStockManager manager;

	@Inject
	ObjectMapper objectMapper;
	
	public ProductStockResource() {
		super(ProductStockData.class);
	}

	@Override
	public Response getProductStock(Long productId) {
		return writeResponse(manager.findStockByProductId(productId));
	}

	@Override
	public Response patchProductStock(Long productId, byte[] data) {
		return writeResponse(manager.updateStock(productId, readData(data)));
	}

}