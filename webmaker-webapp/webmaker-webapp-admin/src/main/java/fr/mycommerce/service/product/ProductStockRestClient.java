package fr.mycommerce.service.product;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import fr.webmaker.common.Identifier;
import fr.webmaker.common.response.SingleResponse;
import fr.webmaker.product.data.ProductStockData;

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