package fr.commerces.common;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import io.restassured.response.ValidatableResponse;

public abstract class AbtractQuarkusApiTest {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AbtractQuarkusApiTest.class);
	
	private static final Map<String, Object> pathParams = new HashMap<String, Object>();
	private static final Map<String, Object> queryParams = new HashMap<String, Object>();
	
	protected abstract String getBasePath();

	@BeforeEach
	public void beforeEach() {
		log.info("############################## BeforeEach ##############################");

	}

	@AfterEach
	public void afterEach() {
		log.info("############################## AfterEach ##############################");
		pathParams.clear();
		queryParams.clear();
	}
	
	protected static void putPathParam(String key, Object value) {
		pathParams.put(key, value);
	}

	protected static void putQueryParam(String key, Object value) {
		queryParams.put(key, value);
	}
	
	protected final ValidatableResponse testEndpoint_StatusCode404() {
		return testEndpoint_StatusCode404("/");
	}

	protected final ValidatableResponse testEndpoint_StatusCode404(final String endpointPath) {
		return testEndpoint(endpointPath, 404);
	}
	
	protected final ValidatableResponse testEndpoint_OK() {
		return testEndpoint_OK("/");
	}

	protected final ValidatableResponse testEndpoint_OK(final String endpointPath) {
		return testEndpoint(endpointPath, 200);
	}

	protected final ValidatableResponse testEndpoint(final String endpointPath, final int expectedStatusCode) {
		final StringBuilder fullPath = new StringBuilder(getBasePath()).append(endpointPath);

		if (!queryParams.isEmpty()) {
			fullPath.append("?");
			queryParams.entrySet().forEach(entry -> {
				fullPath.append("&");
				fullPath.append(String.valueOf(entry.getKey()));
				fullPath.append("=");
				fullPath.append(String.valueOf(entry.getValue()));
			});
		}

		return given().when().get(fullPath.toString().replace("?&", "?"), pathParams).then()
				.statusCode(expectedStatusCode);
	}

}
