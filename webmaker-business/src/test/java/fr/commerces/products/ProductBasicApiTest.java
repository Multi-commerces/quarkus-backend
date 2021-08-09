package fr.commerces.products;

import static fr.commerces.common.UtilityTest.LANG_FR;
import static fr.commerces.common.UtilityTest.PATH_PRODUCT_BASIC;
import static fr.commerces.common.UtilityTest.PRODUCT_10000001;
import static fr.commerces.common.UtilityTest.PRODUCT_BIDON;

import org.junit.jupiter.api.Test;

import fr.commerces.common.AbtractQuarkusApiTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

@QuarkusTest
public class ProductBasicApiTest extends AbtractQuarkusApiTest {

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductByIdEndpoint_StatusCode404() {
		putPathParam("languageCode", LANG_FR);
		putPathParam("productId", PRODUCT_BIDON);

		testEndpoint_StatusCode404();
	}

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductByIdEndpoint_OK() {
		putPathParam("languageCode", LANG_FR);
		putPathParam("productId", PRODUCT_10000001);

		testEndpoint_OK();
	}

	@Override
	protected String getBasePath() {
		return PATH_PRODUCT_BASIC;
	}

}