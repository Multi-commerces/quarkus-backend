package fr.commerces.services.products.ressources.pricing;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

import fr.commerces.services._transverse.GenericResource;
import fr.commerces.services.products.ressources.products.ProductService;

@Path("products/{productId}/pricings")
@RequestScoped
public class ProductPricingResource extends GenericResource {

	@Inject
	ProductService manager;
	 	
	public static final String resourceName = "pricings"; 

}