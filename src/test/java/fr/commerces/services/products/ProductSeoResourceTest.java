package fr.commerces.services.products;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ProductSeoResourceTest {
	
	@Test
	public void testGetProductSeos() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", 1L);
		
		when().get("/products/{productId}/seo", params)
			.then().statusCode(404);
	}
	
	@Test
	public void testGetProductSeo() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("languageCode", "fr");
		params.put("productId", 1L);
		
		given()
			.when().get("/products/{productId}/seo/languages/{languageCode}", params)
			.then().statusCode(404);
	}
	

}