package fr.commerces.services.products;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ProductResourceTest {
	
	@Test
	public void testProductEndpoint() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("languageCode", "fr");
		params.put("productId", 1L);
		when().get("/products/{productId}/languages/{languageCode}", params)
			.then().statusCode(404);
	}
	
	@Test
	public void testProductsEndpoint() {
		given()
			.when().get("/products?languageCode=fr&page=1&size=20")
			.then().statusCode(200);
	}
	

}