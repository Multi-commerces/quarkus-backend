package fr.commerces.microservices.catalog.products.seo;

import static fr.commerces.commons.utilities.UtilityTest.LANG_CODE_FR;
import static fr.commerces.commons.utilities.UtilityTest.PRODUCT_ID_10000001;
import static fr.commerces.commons.utilities.UtilityTest.PRODUCT_ID_BIDON;

import org.junit.jupiter.api.Test;

import fr.commerces.commons.abstracts.AbtractQuarkusApiTest;
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

		testEndpoint_StatusCode404("/languages/{languageCode}");
	}

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductByIdEndpoint_OK() {
		putPathParam("languageCode", LANG_CODE_FR);
		putPathParam("productId", PRODUCT_ID_10000001);

		testEndpoint_OK("/languages/{languageCode}");
	}

}