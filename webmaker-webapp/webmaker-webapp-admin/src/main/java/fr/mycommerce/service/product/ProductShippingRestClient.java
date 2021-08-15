package fr.mycommerce.service.product;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import fr.webmaker.commons.identifier.Identifier;
import fr.webmaker.commons.response.SingleResponse;
import fr.webmaker.microservices.catalog.products.data.ProductShippingData;

@Dependent
@Path("products/{productId}/shipping")
@RegisterRestClient(configKey = "mycommerce-api")
@RegisterClientHeaders
public interface ProductShippingRestClient {

	@GET
	@Path("/")
	SingleResponse<ProductShippingData, Identifier<Long>> get(@PathParam("productId") Long id);

	@PATCH
	@Path("/")
	void patch(@PathParam("productId") Long productId, ProductShippingData data);

}