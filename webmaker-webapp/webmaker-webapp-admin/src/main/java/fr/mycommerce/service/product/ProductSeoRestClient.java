package fr.mycommerce.service.product;

import javax.enterprise.context.Dependent;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Dependent
@Produces("application/vnd.api+json")
@Consumes("application/vnd.api+json")
@Path("/products/{productId}/seo")
@RegisterRestClient(configKey = "mycommerce-api")
public interface ProductSeoRestClient {

	@GET
	@Path("/languages/{languageCode}")
	byte[] get(@PathParam("languageCode") String languageCode,
			@PathParam("productId") Long id);

	@PATCH
	@Path("/languages/{languageCode}")
	Response patch(@PathParam("languageCode") String languageCode, @PathParam("productId") Long productId,
			byte[] data);

}