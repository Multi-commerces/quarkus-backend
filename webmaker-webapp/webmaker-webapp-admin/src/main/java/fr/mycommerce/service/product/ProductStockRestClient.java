package fr.mycommerce.service.product;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Dependent
@Path("products/{productId}/stocks")
@RegisterRestClient(configKey = "mycommerce-api")
public interface ProductStockRestClient {

	@GET
	@Path("/")
	byte[] get(@PathParam("productId") Long id);

	@PATCH
	@Path("/")
	void patch(@PathParam("productId") Long productId, byte[] data);

}