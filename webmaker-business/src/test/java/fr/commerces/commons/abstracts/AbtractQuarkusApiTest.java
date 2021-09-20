package fr.commerces.commons.abstracts;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.models.PathItem.HttpMethod;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbtractQuarkusApiTest {
	
	private final Map<String, Object> pathParams = new HashMap<String, Object>();
	private final Map<String, Object> queryPrams = new HashMap<String, Object>();
	protected Object body = null;

	@BeforeEach
	public void beforeEach() {
		log.info("############################## BeforeEach ##############################");

	}

	@AfterEach
	public void afterEach() {
		log.info("############################## AfterEach ##############################");
		pathParams.clear();
		queryPrams.clear();
		body = null;
	}
	
	protected void putPathParam(String key, Object value) {
		pathParams.put(key, value);
	}

	protected void putQueryParam(String key, Object value) {
		queryPrams.put(key, value);
	}

	protected final ValidatableResponse testEndpoint_OK(final String endpointPath) {
		return testEndpoint(HttpMethod.GET, endpointPath, Status.OK);
	}

	protected final ValidatableResponse testEndpoint(final HttpMethod httpMethod, final Status status) {
		return testEndpoint(httpMethod, "/", status);
	}
	
	protected final ValidatableResponse testEndpoint(final HttpMethod httpMethod, final String endpointPath, final Status status) {
		String path = endpointPath == null ? "/" : endpointPath;
		
		final RequestSpecification with = RestAssured.with()
				.queryParams(queryPrams)
				.pathParams(pathParams);
		
		final RequestSpecification given = given(body == null ? with: with.body(body))
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
					.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);

		final RequestSpecification when = given.when();
		final Response response;
		switch (httpMethod) {
		case GET:
			response = when.get(path);
			break;
		case POST:
			response = when.post(path);
			break;
		case PUT:
			response = when.put(path);
			break;
		case PATCH:
			response = when.patch(path);
			break;
		default:
			return null;
		}
		
		return response.then().statusCode(status.getStatusCode());
	}

}
