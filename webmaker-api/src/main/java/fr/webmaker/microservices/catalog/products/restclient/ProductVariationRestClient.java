package fr.webmaker.microservices.catalog.products.restclient;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.products.data.ProductVariationData;
import fr.webmaker.microservices.catalog.products.id.ProductVariationID;

@Dependent
@Path("/products/{productId}/variations")
@RegisterRestClient(configKey = "mycommerce-api")
@RegisterClientHeaders
public interface ProductVariationRestClient  {

	@GET
	@Path("/") 
	List<SingleResponse<ProductVariationData, ProductVariationID>> getVariations(
			@PathParam("productId") Long productId);
	
	@Path("/{variationId}")
	@GET
	ProductVariationData get(
			@PathParam("productId") Long productId,
			@PathParam("variationId") Long variationId);

	@POST
	@Path("/") 
	void create(
			@PathParam("productId") Long productId,
			ProductVariationData data);
	
	@PUT
	@Path("/") 
	void update(
			@PathParam("productId") Long productId,
			@PathParam("variationId") Long variationId,
			ProductVariationData data);


	@DELETE
	@Path("/{variationId}")
	void delete(@PathParam("productId") Long productId,
			@PathParam("variationId") Long variationId);

}