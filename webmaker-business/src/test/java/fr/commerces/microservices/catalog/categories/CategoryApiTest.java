package fr.commerces.microservices.catalog.categories;

import static fr.commerces.commons.utilities.UtilityTest.LANG_CODE_FR;
import static fr.commerces.commons.utilities.UtilityTest.LANG_CODE_BIDON;
import static fr.commerces.commons.utilities.UtilityTest.PRODUCT_ID_10000001;

import org.junit.jupiter.api.Test;

import fr.commerces.commons.abstracts.AbtractQuarkusApiTest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

@TestHTTPEndpoint(CategoryResource.class)
@QuarkusTest
public class CategoryApiTest extends AbtractQuarkusApiTest {

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetCategoryByIdEndpoint_StatusCode404() {
		putPathParam("languageCode", LANG_CODE_BIDON);
		putQueryParam("includeSubCategories", true);

		testEndpoint_StatusCode404();
	}

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetCategoryByIdEndpoint_OK() {
		putPathParam("languageCode", LANG_CODE_FR);
		putPathParam("productId", PRODUCT_ID_10000001);

		testEndpoint_OK();
	}

}