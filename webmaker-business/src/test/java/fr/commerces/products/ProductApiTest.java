package fr.commerces.products;

import static fr.commerces.commons.utilities.UtilityTest.LANG_CODE_FR;
import static fr.commerces.commons.utilities.UtilityTest.PATH_PRODUCT;
import static fr.commerces.commons.utilities.UtilityTest.PRODUCT_ID_10000001;
import static fr.commerces.commons.utilities.UtilityTest.PRODUCT_ID_BIDON;

import org.junit.jupiter.api.Test;

import fr.commerces.commons.abstracts.AbtractQuarkusApiTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

//@TestTransaction
@QuarkusTest
public class ProductApiTest extends AbtractQuarkusApiTest {

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductByIdEndpoint_StatusCode404() {
		putPathParam("languageCode", LANG_CODE_FR);
		putPathParam("productId", PRODUCT_ID_BIDON);

		testEndpoint_StatusCode404("/{productId}");
	}

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductByIdEndpoint_OK() {
		putPathParam("languageCode", LANG_CODE_FR);
		putPathParam("productId", PRODUCT_ID_10000001);

		testEndpoint_OK("/{productId}");
	}

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductsEndpoint_QueryParamEmpty_OK() {
		putPathParam("languageCode", LANG_CODE_FR);

		testEndpoint_OK("/");
	}
	
	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductsEndpoint_OK() {
		putPathParam("languageCode", LANG_CODE_FR);

		putQueryParam("page", 1);
		putQueryParam("size", 1);

		testEndpoint_OK("/");
	}

	@Override
	protected String getBasePath() {
		return PATH_PRODUCT;
	}

}