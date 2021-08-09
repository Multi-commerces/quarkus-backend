package fr.mycommerce.service.product;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import fr.webmaker.common.response.SingleResponse;
import fr.webmaker.product.ProductID;
import fr.webmaker.product.data.ProductBasicData;

@Dependent
@Path("/products")
@RegisterRestClient(configKey = "mycommerce-api")
public interface ProductBasicRestClient {

	@GET
	@Path("/{productId}/languages/{languageCode}/basic")
	SingleResponse<ProductBasicData, ProductID> get(@PathParam("languageCode") String languageCode,
			@PathParam("productId") Long id);

	@PATCH
	@Path("/{productId}/languages/{languageCode}/basic")
	void patch(@PathParam("languageCode") String languageCode, @PathParam("productId") Long productId,
			ProductBasicData data);

	@POST
	@Path("/languages/{languageCode}")
	void create(@PathParam("languageCode") String languageCode, ProductBasicData data);

}