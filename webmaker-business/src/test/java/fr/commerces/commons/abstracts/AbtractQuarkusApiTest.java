package fr.commerces.commons.abstracts;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbtractQuarkusApiTest {
	
	private final Map<String, Object> pathParams = new HashMap<String, Object>();
	private final Map<String, Object> queryParams = new HashMap<String, Object>();
	
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
	
	protected void putPathParam(String key, Object value) {
		pathParams.put(key, value);
	}

	protected void putQueryParam(String key, Object value) {
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
