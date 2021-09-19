package fr.webmaker.microservices.catalog.products.restclient;

import javax.enterprise.context.Dependent;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.microservices.catalog.products.data.ProductData;
import fr.webmaker.microservices.catalog.products.id.ProductID;
import fr.webmaker.microservices.catalog.products.response.ProductResponse;

@Dependent
@Path("/languages/{languageCode}/products")
@RegisterRestClient(configKey = "mycommerce-api")
@RegisterClientHeaders
public interface ProductRestClient  {

	@GET
	@Path("/") 
	CollectionResponse<ProductData, ProductID> getProducts(
			@PathParam("languageCode") String languageCode,
			@QueryParam(value = "page")  Integer page,
			@QueryParam(value = "size") Integer size);
	
	@Path("/{productId}")
	@GET
	ProductResponse get(
			@PathParam("languageCode") String languageCode,
			@PathParam("productId") Long id);

	/* ############################################################################################################# */

	@POST
	@Path("/") 
	Response create(
			@PathParam("languageCode")  String languageCode,
			 ProductData data);

	/* ############################################################################################################# */

	@PUT
	@Path("/{productId}")
	void update(
			@PathParam("languageCode") String languageCode,
			@PathParam(value = "productId") Long productId, 
			ProductData data);

	@DELETE
	@Path("/{productId}")
	void delete(
			@PathParam("languageCode") String languageCode,
			@PathParam(value = "productId") Long productId);

}