package fr.commerces.microservices.catalog.products.stocks;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import fr.commerces.commons.resources.GenericResource;
import fr.webmaker.commons.identifier.Identifier;
import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.microservices.catalog.products.data.ProductStockData;


@RequestScoped
public class ProductStockResource extends GenericResource<CollectionResponse<ProductStockData, Identifier<Long>>> implements ProductStockApi {

	@Inject
	ProductStockManager manager;
	 	
	public static final String resourceName = "stocks";

	@Override
	public Response getProductStock(Long productId) {
		final ProductStockData data = manager.findStockByProductId(productId);
		return buildResponse(data, productId);
	}

	@Override
	public Response patchProductStock(Long productId, ProductStockData data) {
		manager.updateStock(productId, data);
		return Response.noContent().build();
	} 

}