package fr.commerces.hypermedia;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.models.PathItem.HttpMethod;
import org.jboss.resteasy.resteasy_jaxrs.i18n.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.commerces.services._transverse.GenericResource;
import fr.commerces.services._transverse.data.LinkData;
import fr.commerces.services._transverse.response.CollectionResponse;
import fr.commerces.services._transverse.response.SingleResponse;

/**
 * API Hypermedia
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
			final HypermediaApi hypermedia = invocationContext.getTarget().getClass()
					.getAnnotation(HypermediaApi.class);

			// UriInfo
			final UriInfo uriInfo = resource.getUriInfo();
			debugUriInfo(uriInfo);

			if (object instanceof CollectionResponse) {
				/* Response */
				final CollectionResponse response = (CollectionResponse) object;
				addLinkInResponse(invocationContext, uriInfo, response, hypermedia);
			} else if (object instanceof List) {
				final Collection<?> collections = (List) object;
				/* Responses */
				if (collections.stream().allMatch(CollectionResponse.class::isInstance)) {
					collections.stream().forEach(o -> {
						/* Response */
						final CollectionResponse response = (CollectionResponse) o;
						addLinkInResponse(invocationContext, uriInfo, response, hypermedia);
					});
				}
			}

		}

		return object;
	}

	static void addLinkInResponse(InvocationContext invocationContext, UriInfo uriInfo,
			CollectionResponse<?, ?> collectionResponse, HypermediaApi hypermedia) {

		/*
		 * Construction LINKS
		 */
		if (collectionResponse == null || collectionResponse.getEmbedded() == null) {
			return;
		}
		for (SingleResponse<?, ?> response : collectionResponse.getEmbedded()) {
			List<LinkData> links = response.getLinks();
			for (fr.commerces.hypermedia.HypermediaLink link : hypermedia.links()) {
				logger.debug(link.toString());

				// LINK
				final Class<?> resource = link.resource();
				final HttpMethod httpMethod = link.httpMethod();
				final String method = link.methode();

				String rel = link.rel();
				String summary = "";
				String desc = "";

				// PARAMS QUERY
				final List<Object> params = new ArrayList<Object>();
				params.add(response.getId());
				Arrays.asList(invocationContext.getParameters()).forEach(param -> {
					params.add(param);
				});

				// URI RESOURCE
				var uriBuilder = uriInfo.getBaseUriBuilder().path(resource);
				if (StringUtils.stripToNull(method) != null) {

					// METHOD
					Method theMethod = null;
					for (Method m : resource.getDeclaredMethods()) {
						if (m.getName().equals(method)) {
							if (theMethod != null && m.isAnnotationPresent(Operation.class)) {
								throw new IllegalArgumentException(Messages.MESSAGES.twoMethodsSameName(method));
							}
							if (m.isAnnotationPresent(Operation.class))
								theMethod = m;
						}
					}

					// REL (LIST, SELF)
					if (theMethod != null) {
						uriBuilder.path(theMethod);
						summary = theMethod.getAnnotation(Operation.class).summary();
						desc = theMethod.getAnnotation(Operation.class).description();
					}
				}

				// URI => TITLE, TYPE, REL
				logger.debug("############" + invocationContext.getContextData().toString());
				URI uri = uriBuilder.build(params.toArray());

				// final var builder =
				// Link.fromUri(uri).rel(rel).title(title).type(httpMethod.name()).build();
				LinkData linkData = new LinkData(rel, uri.getPath());
				linkData.setSummary(summary);
				linkData.setDesc(desc);
				linkData.setMethod(httpMethod);


				links.add(linkData);
			}
		}

	}

	/**
	 * Logger debug UriInfo (logger.isDebugEnabled())
	 * 
	 * @param uriInfo
	 */
	static void debugUriInfo(UriInfo uriInfo) {
		if (!logger.isDebugEnabled())
			return;

		logger.debug("------------------------------------------------------------------------------------------");
		logger.debug("-- UriInfo --" + (new Date()).toInstant());
		if (uriInfo == null) {
			logger.debug("UriInfo: " + uriInfo);
			return;
		}
		logger.debug("absolutePath: {}", uriInfo.getAbsolutePath());
		logger.debug("baseUri: {}", uriInfo.getBaseUri());
		logger.debug("matchedResource: {}", uriInfo.getMatchedResources());
		logger.debug("matchedUri: {}", uriInfo.getMatchedURIs());
		logger.debug("path: {}", uriInfo.getPath());
		logger.debug("pathParameters: {}", uriInfo.getPathParameters());
		logger.debug("pathSegments: {}", uriInfo.getPathSegments());
		logger.debug("queryParameters: {}", uriInfo.getQueryParameters());
		logger.debug("requestUri: {}", uriInfo.getRequestUri());
		logger.debug("------------------------------------------------------------------------------------------");
	}

}