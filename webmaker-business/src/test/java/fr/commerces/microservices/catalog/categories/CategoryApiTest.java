package fr.commerces.microservices.catalog.categories;

import static fr.commerces.commons.utilities.UtilityTest.CATEGORY_ID_20000001;
import static fr.commerces.commons.utilities.UtilityTest.LANG_CODE_BIDON;
import static fr.commerces.commons.utilities.UtilityTest.LANG_CODE_FR;
import static fr.commerces.commons.utilities.UtilityTest.LANG_CODE_NOTUSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.models.PathItem.HttpMethod;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import fr.commerces.commons.abstracts.AbtractQuarkusApiTest;
import fr.webmaker.microservices.catalog.categories.data.CategoryLangData;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import io.restassured.response.ValidatableResponse;

@TestHTTPEndpoint(CategoryResource.class)
@QuarkusTest
public class CategoryApiTest extends AbtractQuarkusApiTest {
	
	@InjectMock 
    CategoryManager manager;
	
//	@InjectSpy
//	CategoryManager spyManager;

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetCategoryByIdEndpoint_LANG_CODE_BIDON() {
		putPathParam("languageCode", LANG_CODE_BIDON);
		testEndpoint(HttpMethod.GET, Status.OK);
		
//		verify(spyManager, times(1)).findCategoryHierarchy(isNull());
	}
	
	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetCategoryByIdEndpoint_LANG_CODE_NOTUSE() {
		putPathParam("languageCode", LANG_CODE_NOTUSE);
		testEndpoint(HttpMethod.GET, Status.OK);
		
//		verify(spyManager, times(1)).findCategoryHierarchy(any());
	}

	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testGetCategoryByIdEndpoint_OK() {
		putPathParam("languageCode", LANG_CODE_FR);
		testEndpoint(HttpMethod.GET, Status.OK);
		
//		verify(spyManager, times(1)).findCategoryHierarchy(any());
	}
	
	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testPostCategoryByIdEndpoint_OK() {
		putPathParam("languageCode", LANG_CODE_FR);
		
		body = new CategoryLangData();
		CategoryLangData data = (CategoryLangData) body;
		data.setDescription("descrption");
		data.setDisplayed(true);
		data.setPosition(1);
		data.setName("nom");
		
		when(manager.createCategory(isNull(), any())).thenReturn(1L);
		ValidatableResponse response = testEndpoint(HttpMethod.POST, Status.CREATED);
		response.header("location", Matchers.contains("/languages/fr/categories/1"));
	}
	
	@Test
	@TestSecurity(authorizationEnabled = false)
	public void testPutCategoryByIdEndpoint_NoContent() {
		// Preparation --------------------------------------
		body = new CategoryLangData();
		CategoryLangData data = (CategoryLangData) body;
		data.setDescription("descrption");
		data.setDisplayed(true);
		data.setPosition(1);
		data.setName("nom");
		
		// Test EndPoint --------------------------------------
		putPathParam("languageCode", LANG_CODE_FR);
		putPathParam("id", CATEGORY_ID_20000001);
		
		doNothing().when(manager).update(anyLong(), any(), any());
		testEndpoint(HttpMethod.PUT, "/{id}", Status.NO_CONTENT);
	}

}