package fr.commerces.services.internal.products.ressources.stock;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

import fr.commerces.services._transverse.GenericResource;
import fr.commerces.services._transverse.response.CollectionResponse;
import fr.commerces.services.internal.products.data.ProductStockData;
import fr.commerces.services.internal.products.manager.ProductManager;

@Path("products/{productId}/stocks")
@RequestScoped
public class ProductStockResource extends GenericResource<CollectionResponse<ProductStockData, Long>> {

	@Inject
	ProductManager manager;
	 	
	public static final String resourceName = "stocks"; 

}