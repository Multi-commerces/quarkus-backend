package fr.mycommerce.service.product;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.products.data.ProductBasicData;
import fr.webmaker.microservices.catalog.products.id.ProductID;

@Dependent
@Path("/products")
@RegisterRestClient(configKey = "mycommerce-api")
@RegisterClientHeaders
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