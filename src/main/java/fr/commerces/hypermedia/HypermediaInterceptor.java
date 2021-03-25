package fr.commerces.hypermedia;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.commerces.services._transverse.GenericResource;
import fr.commerces.services._transverse.GenericResponse;

/**
 * Logged Interceptor
 * <p>
 * capture des exceptions afin d'enrichir le log pour la résolution d'anomalie.
 * </p>
 * 
 * @author Julien ILARI
 *
 */
@Hypermedia
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class HypermediaInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(HypermediaInterceptor.class);

	@SuppressWarnings("rawtypes")
	@AroundInvoke
	public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
		final Object object = invocationContext.proceed();
		if (invocationContext.getTarget() instanceof GenericResource) {
			

			/* Resource */
			final GenericResource resource = (GenericResource) invocationContext.getTarget();
			final HypermediaSelf hypermedia = invocationContext.getTarget().getClass()
					.getAnnotation(HypermediaSelf.class);
			final UriInfo uriInfo = resource.getUriInfo();
			final UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();

			if (object instanceof GenericResponse) {
				/* Response */
				final GenericResponse response = (GenericResponse) object;
				addLinkInResponse(invocationContext, uriBuilder, response, hypermedia);
			} else if (object instanceof List) {
				final Collection<?> collections = (List) object;
				/* Responses */
				if (collections.stream().anyMatch(GenericResponse.class::isInstance)) {
					collections.parallelStream().forEach(o -> {
						/* Response */
						final GenericResponse response = (GenericResponse) o;
						addLinkInResponse(invocationContext, uriBuilder, response, hypermedia);
					});
				}
			}

		}

		return object;
	}

	static void addLinkInResponse(InvocationContext invocationContext, UriBuilder uriBuilder,
			GenericResponse<?, ?> response, HypermediaSelf hypermedia) {

		/* Construction SELF */
		logger.debug(hypermedia.title());
		final Link self = Link
				.fromUriBuilder(uriBuilder.clone().path(hypermedia.resource()).path(hypermedia.resource(),
						invocationContext.getMethod().getName()))
				.type(hypermedia.httpMethod().name()).rel(hypermedia.rel()).title(hypermedia.title())
				.build(response.getId());

		/* Construction LINKS */
		for (fr.commerces.hypermedia.HypermediaLink link : hypermedia.links()) {
			logger.debug(link.title());
			response.getLinks().add(Link.fromUriBuilder(uriBuilder.clone().path(link.resource()))
					.type(link.httpMethod().name()).rel(link.rel()).title(link.title()).build(response.getId()));
		}

		response.setSelf(self);
	}
	
	static void printUriInfo(UriInfo uriInfo, String msg) {
		System.out.println("---------------");
		System.out.println(msg);
		System.out.println("-- UriInfo --" + (new Date()).toInstant());
		if (uriInfo == null) {
			System.out.println("UriInfo: " + uriInfo);
			return;
		}
		System.out.println("absolutePath: " + uriInfo.getAbsolutePath());
		System.out.println("baseUri: " + uriInfo.getBaseUri());
		System.out.println("matchedResource: " + uriInfo.getMatchedResources());
		System.out.println("matchedUri: " + uriInfo.getMatchedURIs());
		System.out.println("path: " + uriInfo.getPath());
		System.out.println("pathParameters: " + uriInfo.getPathParameters());
		System.out.println("pathSegments: " + uriInfo.getPathSegments());
		System.out.println("queryParameters: " + uriInfo.getQueryParameters());
		System.out.println("requestUri: " + uriInfo.getRequestUri());
		System.out.println("relativize test: " + uriInfo.relativize(URI.create("test")));
		System.out.println("resolve test: " + uriInfo.resolve(URI.create("test")));
	}

}