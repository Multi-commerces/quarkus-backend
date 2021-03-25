package fr.commerces.services._transverse;

import java.net.URI;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.eclipse.microprofile.openapi.models.PathItem.HttpMethod;

import fr.commerces.services.authentifications.provider.AuthenticationContextProvider;
import lombok.Getter;

public abstract class GenericResource<R extends GenericResponse<?, ?>> {

	@Inject
	protected AuthenticationContextProvider authentication;

	@Context @Getter
	protected UriInfo uriInfo;
	
	/**
	 * Création Link avec REL => SELF et METHODE => GET
	 */
	protected final BiFunction<ImmutablePair<Class<?>,String>, Long, Link> createLinkSelfGet = (pair, id) -> {
		return Link.fromUriBuilder(uriInfo.getBaseUriBuilder().path(pair.getLeft()))
				.type(HttpMethod.GET.name())
				.rel("self")
				.title(pair.right)
				.build(id);
	};

	protected final Function<Long, Response> buildResponse = id -> {
		if (id == null) {
			throw new IllegalArgumentException("identifiant null");
		}
		
		URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build();
		return Response.created(uri).language(Locale.FRENCH).build();
	};

	

}
