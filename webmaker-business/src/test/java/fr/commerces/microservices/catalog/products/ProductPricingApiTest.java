package fr.commerces.microservices.catalog.products;

import static fr.commerces.commons.utilities.UtilityTest.PRODUCT_ID_10000001;
import static fr.commerces.commons.utilities.UtilityTest.PRODUCT_ID_BIDON;

import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.models.PathItem.HttpMethod;
import org.junit.jupiter.api.Test;

import fr.commerces.commons.abstracts.AbtractQuarkusApiTest;
import fr.commerces.microservices.catalog.products.relationships.shipping.ProductPricingApi;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.response.ValidatableResponse;

@TestHTTPEndpoint(ProductPricingApi.class)
@QuarkusTest
public class ProductPricingApiTest extends AbtractQuarkusApiTest {

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductByIdEndpoint_PRODUCT_ID_BIDON() {
		putPathParam("productId", PRODUCT_ID_BIDON);
		ValidatableResponse response = testEndpoint(HttpMethod.GET, Status.OK);
		
	}

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductByIdEndpoint_OK() {
		putPathParam("productId", PRODUCT_ID_10000001);
		testEndpoint(HttpMethod.GET, Status.OK);
	}

}