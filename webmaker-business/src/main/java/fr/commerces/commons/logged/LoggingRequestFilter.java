package fr.commerces.commons.logged;

import java.util.Date;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import io.vertx.core.http.HttpServerRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class LoggingRequestFilter implements ContainerRequestFilter {

	@Context
	UriInfo info;

	@Context
	HttpServerRequest request;

	@Override
	public void filter(ContainerRequestContext requestContext) {
		log.debug("------------------------------------------------------------------------------------------");
		log.debug("Request {} {}, path {}, content-type {}", requestContext.getMethod(),
				requestContext.getUriInfo().getBaseUri(), info.getPath(),
				requestContext.getHeaderString(HttpHeaders.CONTENT_TYPE));

		debugUriInfo(requestContext.getUriInfo());
		log.debug("------------------------------------------------------------------------------------------");
	}

	/**
	 * Logger debug UriInfo (logger.isDebugEnabled())
	 * 
	 * @param uriInfo
	 */
	static void debugUriInfo(UriInfo uriInfo) {
		if (!log.isDebugEnabled())
			return;

		log.debug("-- UriInfo --" + (new Date()).toInstant());
		if (uriInfo == null) {
			log.debug("UriInfo: " + uriInfo);
			return;
		}

		log.debug("absolutePath: {}", uriInfo.getAbsolutePath());
		log.debug("baseUri: {}", uriInfo.getBaseUri());
		log.debug("matchedResource: {}", uriInfo.getMatchedResources());
		log.debug("matchedUri: {}", uriInfo.getMatchedURIs());
		log.debug("path: {}", uriInfo.getPath());
		log.debug("pathParameters: {}", uriInfo.getPathParameters());
		log.debug("pathSegments: {}", uriInfo.getPathSegments());
		log.debug("queryParameters: {}", uriInfo.getQueryParameters());
		log.debug("requestUri: {}", uriInfo.getRequestUri());
	}
}