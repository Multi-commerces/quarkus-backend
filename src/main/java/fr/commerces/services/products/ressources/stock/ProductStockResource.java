package fr.commerces.services.products.ressources.stock;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

import fr.commerces.services._transverse.GenericResource;
import fr.commerces.services._transverse.GenericResponse;
import fr.commerces.services.products.data.ProductStockData;
import fr.commerces.services.products.manager.ProductManager;

@Path("products/{productId}/stocks")
@RequestScoped
public class ProductStockResource extends GenericResource<GenericResponse<ProductStockData, Long>> {

	@Inject
	ProductManager manager;
	 	
	public static final String resourceName = "stocks"; 

}