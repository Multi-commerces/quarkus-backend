package fr.mycommerce.service.product;

import javax.enterprise.context.Dependent;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Produces("application/vnd.api+json")
@Consumes("application/vnd.api+json")
@Dependent
@Path("/products")
@RegisterRestClient(configKey = "mycommerce-api")
public interface ProductRestClient  {

	@GET
	@Path("/") 
	byte[] getProducts(@QueryParam(value = "page")  Integer page,
			@QueryParam(value = "size") Integer size);
	
	@Path("/{productId}")
	@GET
	byte[] get(
			@PathParam("languageCode") String languageCode,
			@PathParam("productId") Long id);

	/* ############################################################################################################# */

	@POST
	@Path("/") 
	Response create(byte[] data);

	/* ############################################################################################################# */

	@PUT
	@Path("/{productId}")
	void update(@PathParam(value = "productId") String productId, 
			byte[] data);

	@DELETE
	@Path("/{productId}")
	void delete(@PathParam(value = "productId") String productId);

}