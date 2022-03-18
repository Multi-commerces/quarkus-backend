package fr.mycommerce.service.product;

import javax.enterprise.context.Dependent;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Dependent
@Produces("application/vnd.api+json")
@Consumes("application/vnd.api+json")
@Path("products/{productId}/pricings")
@RegisterRestClient(configKey = "mycommerce-api")
public interface ProductPricingRestClient {

	@GET
	@Path("/")
	byte[] get(@PathParam("productId") Long id);

	@PATCH
	@Path("/")
	void patch(@PathParam("productId") Long productId, byte[] data);

}