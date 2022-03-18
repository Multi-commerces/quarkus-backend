package fr.mycommerce.service.product;

import javax.enterprise.context.Dependent;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Dependent
@Produces("application/vnd.api+json")
@Consumes("application/vnd.api+json")
@Path("/products/{productId}/languages")
@RegisterRestClient(configKey = "mycommerce-api")
public interface ProductBasicRestClient {

	@GET
	@Path("/{languageCode}")
	byte[] get(@PathParam("languageCode") String languageCode,
			@PathParam("productId") Long id);

	@PUT
	@Path("/{languageCode}")
	void patch(@PathParam("languageCode") String languageCode, @PathParam("productId") Long productId,
			byte[] data);

	@POST
	@Path("/{languageCode}")
	void create(@PathParam("productId") Long productId, @PathParam("languageCode") String languageCode, byte[] data);

}