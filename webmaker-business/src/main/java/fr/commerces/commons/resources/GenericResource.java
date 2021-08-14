package fr.commerces.commons.resources;

import java.io.Serializable;
import java.net.URI;
import java.util.Locale;
import java.util.function.Function;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import fr.commerces.services.authentifications.provider.AuthenticationContextProvider;
import fr.webmaker.common.Identifier;
import fr.webmaker.common.response.CollectionResponse;
import fr.webmaker.common.response.SingleResponse;
import lombok.Getter;


public abstract class GenericResource<R extends CollectionResponse<?, ?>> {

	@Inject
	AuthenticationContextProvider authentication;
	
	@Context 
	HttpHeaders headers;

	@Context @Getter
	UriInfo uriInfo;

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
		return Response.ok(new SingleResponse<>(new Identifier<Long>(id), data)).build();

	}
	
	/**
	 * Construction de la réponse
	 * @param data
	 * @param identifier
	 * @return
	 */
	protected Response buildResponse(final Serializable data, final Identifier<?> identifier)
	{
		return Response.ok(new SingleResponse<>(identifier, data)).build();
	}

	

}
