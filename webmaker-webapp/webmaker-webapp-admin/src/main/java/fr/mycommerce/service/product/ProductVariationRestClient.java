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

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Dependent
@Produces("application/vnd.api+json")
@Consumes("application/vnd.api+json")
@Path("/products/{productId}/variations")
@RegisterRestClient(configKey = "mycommerce-api")
public interface ProductVariationRestClient  {

	@GET
	@Path("/") 
	byte[] getVariations(
			@PathParam("productId") Long productId);
	
	@Path("/{variationId}")
	@GET
	byte[] get(
			@PathParam("productId") Long productId,
			@PathParam("variationId") Long variationId);

	@POST
	@Path("/") 
	void create(
			@PathParam("productId") Long productId,
			byte[] data);
	
	@PUT
	@Path("/") 
	void update(
			@PathParam("productId") Long productId,
			@PathParam("variationId") Long variationId,
			byte[] data);


	@DELETE
	@Path("/{variationId}")
	void delete(@PathParam("productId") Long productId,
			@PathParam("variationId") Long variationId);

}