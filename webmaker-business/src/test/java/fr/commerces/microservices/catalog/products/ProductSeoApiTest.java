package fr.commerces.microservices.catalog.products;

import static fr.commerces.commons.utilities.UtilityTest.LANG_CODE_FR;
import static fr.commerces.commons.utilities.UtilityTest.PRODUCT_ID_10000001;
import static fr.commerces.commons.utilities.UtilityTest.PRODUCT_ID_BIDON;

import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.models.PathItem.HttpMethod;
import org.junit.jupiter.api.Test;

import fr.commerces.commons.abstracts.AbtractQuarkusApiTest;
import fr.commerces.microservices.catalog.products.openapi.ProductSeoApi;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

@TestHTTPEndpoint(ProductSeoApi.class)
@QuarkusTest
public class ProductSeoApiTest extends AbtractQuarkusApiTest {

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductByIdEndpoint_StatusCode404() {
		putPathParam("languageCode", LANG_CODE_FR);
		putPathParam("productId", PRODUCT_ID_BIDON);
		testEndpoint(HttpMethod.GET, "/languages/{languageCode}", Status.NOT_FOUND);
	}

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductByIdEndpoint_OK() {
		putPathParam("languageCode", LANG_CODE_FR);
		putPathParam("productId", PRODUCT_ID_10000001);
		testEndpoint(HttpMethod.GET, "/languages/{languageCode}", Status.OK);
	}

}