package fr.commerces.commons.hypermedia;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.models.PathItem.HttpMethod;
import org.jboss.logging.MDC;
import org.jboss.resteasy.resteasy_jaxrs.i18n.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.commerces.commons.resources.GenericResource;
import fr.webmaker.commons.data.LinkData;
import fr.webmaker.commons.data.RelationData;
import fr.webmaker.commons.data.SingleCompositeData;
import fr.webmaker.commons.response.CollectionResponse;

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

	@AroundInvoke
	public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
		final Object target = invocationContext.getTarget();
		MDC.put("method", invocationContext.getMethod().getName());
		MDC.put("class", invocationContext.getMethod().getDeclaringClass().getSimpleName());
		
		final Object object = invocationContext.proceed();
		
		/*
		 *  TARGET doit être du type GenericResource
		 */
		if (!(target instanceof GenericResource)) {
			return object;
		}

		final GenericResource resource = (GenericResource) target;
		final UriInfo uriInfo = resource.getUriInfo();
		
		/*
		 * [@HypermediaApi] l'annotation doit-être présente pour activer le mode HypermediaApi
		 */
		final HypermediaApi hypermedia = resource.getClass().getAnnotation(HypermediaApi.class);
		if(hypermedia == null)
		{
			return object;
		}
		
		if (object instanceof CollectionResponse) {
			/* Response */
			return addLinkInResponse((CollectionResponse<?, ?>) object, invocationContext, uriInfo, hypermedia);
		} else if (object instanceof Collection) {
			final Collection<?> collections = (List<?>) object;
			/* Responses */
			if (collections.stream().allMatch(CollectionResponse.class::isInstance)) {
				collections.stream().forEach(o -> {
					/* Response */
					final CollectionResponse<?, ?> response = (CollectionResponse<?, ?>) o;
					addLinkInResponse(response, invocationContext, uriInfo, hypermedia);
				});
			}
		}
		
		return object;
	}

	static CollectionResponse<?, ?> addLinkInResponse(final CollectionResponse<?, ?> collectionResponse,
			final InvocationContext invocationContext, final UriInfo uriInfo, final HypermediaApi hypermedia) {
		Objects.requireNonNull(collectionResponse);
		Objects.requireNonNull(invocationContext);
		Objects.requireNonNull(uriInfo);
		Objects.requireNonNull(hypermedia);
		
		/*
		 * Construction LINKS
		 */		
		for (SingleCompositeData<?, ?> response : collectionResponse.getCollection()) {
			for (HypermediaLink link : hypermedia.links()) {
				
				// LINK (resource, httpMethod, method)
				final Class<?> resource = link.resource();
				final HttpMethod httpMethod = link.httpMethod();
				final String method = link.methode();
				
				/*
				 * requireNonNull
				 */
				Objects.requireNonNull(resource);
				Objects.requireNonNull(httpMethod);
				Objects.requireNonNull(method);
				
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
				String rel = link.rel();
				String summary = "";
				String desc = "";
				if (theMethod != null) {
					var operation = theMethod.getAnnotation(Operation.class);
					summary = operation.summary();
					desc = operation.description();
				}

				// PARAMS QUERY
				final List<Object> params = new ArrayList<Object>();
				params.add(response.getIdentifier().getId());//
				Arrays.asList(invocationContext.getParameters()).forEach(param -> {
					params.add(param);
				});

				// URI RESOURCE
				var uriBuilder = uriInfo.getBaseUriBuilder().path(resource);
				if(theMethod != null) uriBuilder.path(theMethod);
				
				// URI (TITLE, TYPE, REL)
				final URI uri = uriBuilder.build(params.toArray());

				
				final LinkData linkData = new LinkData(rel, uri.getPath());
				linkData.setSummary(summary);
				linkData.setMethod(httpMethod.name());
				response.getLinks().add(linkData);
				
//				new RelationData(uri.getPath(), null)
				response.getRelationships().put(rel, new RelationData(uri.getPath(), null));
				
				logger.info("####################### HypermediaLink ###########################");
				logger.info("url = {}", uri);
				logger.info("httpMethod = {}", httpMethod);
				logger.info("method = {}", method);
				logger.info("rel = {}", rel);
				logger.info("summary = {}", summary);
				logger.info("desc = {}", desc);
				logger.info("linkData = {}", linkData);	
			}
		}
		
		return collectionResponse;
	}

	

}