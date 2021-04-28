package fr.commerces.services._transverse;

import java.net.URI;
import java.util.Locale;
import java.util.function.Function;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import fr.commerces.services._transverse.response.CollectionResponse;
import fr.commerces.services.authentifications.provider.AuthenticationContextProvider;
import lombok.Getter;


public abstract class GenericResource<R extends CollectionResponse<?, ?>> {

	@Inject
	protected AuthenticationContextProvider authentication;
	
	@Context 
	protected HttpHeaders headers;

	@Context @Getter
	protected UriInfo uriInfo;

	protected final Function<Long, Response> buildResponse = id -> {
		if (id == null) {
			throw new IllegalArgumentException("identifiant null");
		}
		
		URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build();
		return Response.created(uri).language(Locale.FRENCH).build();
	};

	

}
