package fr.commerces.services.internal.products.ressources.stocks;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import fr.commerces.services._transverse.GenericResource;
import fr.webmaker.common.Identifier;
import fr.webmaker.common.response.CollectionResponse;
import fr.webmaker.product.data.ProductStockData;


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