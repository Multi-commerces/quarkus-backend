package fr.commerces.services.products.ressources.seo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.UriBuilder;

import fr.commerces.services._transverse.GenericResource;
import fr.commerces.services.authentifications.provider.AuthenticationContextProvider;
import fr.commerces.services.products.ressources.products.ProductService;
import lombok.Getter;

@Path("products/{productId}/seo")
@RequestScoped
public class ProductSeoResource extends GenericResource {
	
	@Getter
	final static UriBuilder uriBase = UriBuilder.fromResource(ProductSeoResource.class);
	
	public static final String resourceName = "seo"; 

	@Inject
	ProductService manager;

	@Inject
	AuthenticationContextProvider authentication;
	 	
	

}