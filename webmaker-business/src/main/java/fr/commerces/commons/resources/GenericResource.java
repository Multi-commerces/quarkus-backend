package fr.commerces.commons.resources;

import java.io.Serializable;
import java.net.URI;
import java.util.Locale;
import java.util.function.Function;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasminb.jsonapi.ResourceConverter;

import fr.commerces.microservices.authentification.AuthenticationContextProvider;
import fr.webmaker.commons.data.SingleCompositeData;
import fr.webmaker.commons.identifier.Identifier;
import lombok.Getter;

@Produces("application/vnd.api+json")
@Consumes("application/vnd.api+json")
public abstract class GenericResource {

	@Inject
	protected AuthenticationContextProvider authentication;
	
	@Context 
	protected HttpHeaders headers;

	@Context @Getter
	protected UriInfo uriInfo;
	
	@Inject
	protected ObjectMapper objectMapper;
	
	protected ResourceConverter converter;
	
	public GenericResource()
	{			
		
	}

	protected final Function<Long, Response> buildResponse = id -> {
		if (id == null) {
			throw new IllegalArgumentException("arg.id.null");
		}
		
		final URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build();
		return Response.created(uri).language(Locale.FRENCH).build();
	};
	
	/**
	 * Construction de la réponse
	 * @param data
	 * @param identifier
	 * @return
	 */
	protected Response buildResponse(final Serializable data, final Long id)
	{
		return Response.ok(new SingleCompositeData<>(new Identifier<Long>(id), data)).build();

	}
	
	/**
	 * Construction de la réponse
	 * @param data
	 * @param identifier
	 * @return
	 */
	protected Response buildResponse(final Serializable data, final Identifier<?> identifier)
	{
		return Response.ok(new SingleCompositeData<>(identifier, data)).build();
	}

	

}
