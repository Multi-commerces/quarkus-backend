package fr.mycommerce.service.product;

import javax.enterprise.context.Dependent;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Produces("application/vnd.api+json")
@Consumes("application/vnd.api+json")
@Dependent
@Path("/product-tranlated-sheets")
@RegisterRestClient(configKey = "mycommerce-api")
public interface ProductSheetRestClient  {

	@GET
	@Path("/") 
	byte[] search();	
	
	@GET
	@Path("/{productId}") 
	byte[] searchById(@PathParam("productId") String id);	

	@POST
	@Path("/") 
	Response create(byte[] data);
	
	@PATCH
	@Path("/{productId}") 
	Response update(@PathParam("productId") String id, byte[] data);



}