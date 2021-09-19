package fr.webmaker.microservices.catalog.products.restclient;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import fr.webmaker.commons.identifier.Identifier;
import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.products.data.ProductStockData;

@Dependent
@Path("products/{productId}/stocks")
@RegisterRestClient(configKey = "mycommerce-api")
@RegisterClientHeaders
public interface ProductStockRestClient {

	@GET
	@Path("/")
	SingleResponse<ProductStockData, Identifier<Long>> get(@PathParam("productId") Long id);

	@PATCH
	@Path("/")
	void patch(@PathParam("productId") Long productId, ProductStockData data);

}