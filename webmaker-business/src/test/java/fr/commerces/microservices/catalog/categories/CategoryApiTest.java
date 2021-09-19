package fr.commerces.microservices.catalog.categories;

import static fr.commerces.commons.utilities.UtilityTest.LANG_CODE_BIDON;
import static fr.commerces.commons.utilities.UtilityTest.LANG_CODE_FR;
import static fr.commerces.commons.utilities.UtilityTest.LANG_CODE_NOTUSE;

import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.models.PathItem.HttpMethod;
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
	public void testGetCategoryByIdEndpoint_LANG_CODE_BIDON() {
		putPathParam("languageCode", LANG_CODE_BIDON);
		testEndpoint(HttpMethod.GET, Status.BAD_REQUEST);
	}
	
	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetCategoryByIdEndpoint_LANG_CODE_NOTUSE() {
		putPathParam("languageCode", LANG_CODE_NOTUSE);
		testEndpoint(HttpMethod.GET, Status.OK);
	}

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetCategoryByIdEndpoint_OK() {
		putPathParam("languageCode", LANG_CODE_FR);
		testEndpoint(HttpMethod.GET, Status.OK);
	}

}