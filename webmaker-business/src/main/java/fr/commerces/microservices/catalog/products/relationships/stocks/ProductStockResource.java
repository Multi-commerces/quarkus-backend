package fr.commerces.microservices.catalog.products.relationships.stocks;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasminb.jsonapi.ResourceConverter;

import fr.commerces.commons.resources.JsonApiResource;
import fr.webmaker.data.product.ProductStockData;

@RequestScoped
public class ProductStockResource extends JsonApiResource implements ProductStockApi {

	@Inject
	ProductStockManager manager;

	@Inject
	ObjectMapper objectMapper;

	@PostConstruct
	public void postConstruct() {
		converter = new ResourceConverter(objectMapper, ProductStockData.class);
	}

	@Override
	public Response getProductStock(Long productId) {
		return writeJsonApiResponse(manager.findStockByProductId(productId));
	}

	@Override
	public Response patchProductStock(Long productId, ProductStockData data) {
		return writeJsonApiResponse(manager.updateStock(productId, data));
	}

}