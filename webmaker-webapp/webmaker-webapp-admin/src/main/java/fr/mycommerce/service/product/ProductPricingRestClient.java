package fr.mycommerce.service.product;

import javax.enterprise.context.Dependent;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import fr.webmaker.common.Identifier;
import fr.webmaker.common.response.SingleResponse;
import fr.webmaker.product.data.ProductPricingData;

@Dependent
@Path("products/{productId}/pricings")
@RegisterRestClient(configKey = "mycommerce-api")
public interface ProductPricingRestClient {

	@GET
	@Path("/")
	SingleResponse<ProductPricingData,  Identifier<Long>> get(@PathParam("productId") Long id);

	@PATCH
	@Path("/")
	void patch(@PathParam("productId") Long productId, ProductPricingData data);

}