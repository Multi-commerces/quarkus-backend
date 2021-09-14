package fr.commerces.commons.abstracts;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbtractQuarkusApiTest {
	
	private final Map<String, Object> pathParams = new HashMap<String, Object>();
	private final Map<String, Object> queryPrams = new HashMap<String, Object>();
	
//	protected abstract String getBasePath();

	@BeforeEach
	public void beforeEach() {
		log.info("############################## BeforeEach ##############################");

	}

	@AfterEach
	public void afterEach() {
		log.info("############################## AfterEach ##############################");
		pathParams.clear();
		queryPrams.clear();
	}
	
	protected void putPathParam(String key, Object value) {
		pathParams.put(key, value);
	}

	protected void putQueryParam(String key, Object value) {
		queryPrams.put(key, value);
	}
	
	protected final ValidatableResponse testEndpoint_StatusCode404() {
		return testEndpoint_StatusCode404("");
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

	protected final ValidatableResponse testEndpoint(final int expectedStatusCode) {
		return testEndpoint("/", expectedStatusCode);
	}
	
	protected final ValidatableResponse testEndpoint(final String endpointPath, final int expectedStatusCode) {
		if(endpointPath == null)
		{
			return given().when().get("/", pathParams)    
	          .then()
	             .statusCode(expectedStatusCode);
		}
		
		final StringBuilder fullPath = new StringBuilder(endpointPath);

		if (!queryPrams.isEmpty()) {
			fullPath.append("?");
			final String params = queryPrams.entrySet().stream()
					.map(entry -> String.join("=", entry.getKey(), String.valueOf(entry.getValue())))
					.collect(Collectors.joining("&")).toString();
			
			fullPath.append(params);
			log.info("Path (end-point) : " + fullPath.toString());
		}

		return given().when().get(fullPath.toString(), pathParams).then()
				.statusCode(expectedStatusCode);
	}

}
