package fr.mycommerce.service.product;

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

import fr.webmaker.common.response.CollectionResponse;
import fr.webmaker.product.ProductID;
import fr.webmaker.product.data.ProductData;
import fr.webmaker.product.response.ProductDataResponse;

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
	ProductDataResponse get(
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