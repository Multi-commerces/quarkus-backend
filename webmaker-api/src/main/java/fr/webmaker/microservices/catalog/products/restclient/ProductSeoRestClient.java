package fr.webmaker.microservices.catalog.products.restclient;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import fr.webmaker.commons.identifier.Identifier;
import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.products.data.ProductSeoData;

@Dependent
@Path("/products/{productId}/seo")
@RegisterRestClient(configKey = "mycommerce-api")
public interface ProductSeoRestClient {

	@GET
	@Path("/languages/{languageCode}")
	SingleResponse<ProductSeoData, Identifier<Long>> get(@PathParam("languageCode") String languageCode,
			@PathParam("productId") Long id);

	@PATCH
	@Path("/languages/{languageCode}")
	void patch(@PathParam("languageCode") String languageCode, @PathParam("productId") Long productId,
			ProductSeoData data);

}