package fr.commerces.microservices.catalog.products;

import static fr.commerces.commons.utilities.UtilityTest.LANG_CODE_FR;
import static fr.commerces.commons.utilities.UtilityTest.PRODUCT_ID_10000001;
import static fr.commerces.commons.utilities.UtilityTest.PRODUCT_ID_BIDON;

import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.models.PathItem.HttpMethod;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import fr.commerces.commons.abstracts.AbtractQuarkusApiTest;
import fr.commerces.microservices.catalog.products.relations.lang.ProductLangResourceApi;
import fr.webmaker.restfull.hateos.PagingData;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;


@TestHTTPEndpoint(ProductLangResourceApi.class)
@QuarkusTest
public class ProductLanguageApiTest extends AbtractQuarkusApiTest {

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductByIdEndpoint_StatusCode404() {
		putPathParam("languageCode", LANG_CODE_FR);
		putPathParam("productId", PRODUCT_ID_BIDON);
		
		testEndpoint(HttpMethod.GET, "/{productId}", Status.NOT_FOUND);
	}

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductByIdEndpoint_OK() {
		putPathParam("languageCode", LANG_CODE_FR);
		putPathParam("productId", PRODUCT_ID_10000001);

		testEndpoint(HttpMethod.GET, "/{productId}", Status.OK);
	}

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductsEndpoint_QueryParamEmpty_OK() {
		putPathParam("languageCode", LANG_CODE_FR);
		
		testEndpoint(HttpMethod.GET, Status.OK)
			.body(String.join(".", "meta", PagingData.JSON_NODE_NUMBER), 
				Matchers.is(1))
			.body(String.join(".", "meta", PagingData.JSON_NODE_TOTAL_PAGES),
				Matchers.is(1))
			.body(String.join(".", "meta", PagingData.JSON_NODE_TOTAL_ELEMENTS),
				Matchers.is(3));
	}
	
	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetProductsEndpoint_OK() {
		putPathParam("languageCode", LANG_CODE_FR);

		putQueryParam("page", 1);
		putQueryParam("size", 1);
		testEndpoint(HttpMethod.GET, Status.OK)
			.assertThat()
				.body("paging.page", Matchers.is(1), 
						"paging.totalPages", Matchers.is(3), 
						"paging.totalElements", Matchers.is(3))
				.body( "collection.size()", Matchers.is(1));
	}

}