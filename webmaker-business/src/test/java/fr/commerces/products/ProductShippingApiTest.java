package fr.commerces.products;

import static fr.commerces.commons.utilities.UtilityTest.PATH_PRODUCT_SHIPPING;
import static fr.commerces.commons.utilities.UtilityTest.PRODUCT_ID_10000001;
import static fr.commerces.commons.utilities.UtilityTest.PRODUCT_ID_BIDON;

import org.junit.jupiter.api.Test;

import fr.commerces.commons.abstracts.AbtractQuarkusApiTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

@QuarkusTest
public class ProductShippingApiTest extends AbtractQuarkusApiTest {

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductByIdEndpoint_StatusCode404() {
		putPathParam("productId", PRODUCT_ID_BIDON);

		testEndpoint_StatusCode404();
	}

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductByIdEndpoint_OK() {
		putPathParam("productId", PRODUCT_ID_10000001);

		testEndpoint_OK();
	}

	@Override
	protected String getBasePath() {
		return PATH_PRODUCT_SHIPPING;
	}

}