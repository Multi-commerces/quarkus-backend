package fr.commerces.services.internal.products.ressources.variations;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import fr.commerces.services._transverse.GenericResource;
import fr.commerces.services._transverse.response.CollectionResponse;
import fr.commerces.services.internal.products.data.ProductVariationData;
import fr.commerces.services.internal.products.manager.ProductVariationManager;

public class ProductVariationResource extends GenericResource<CollectionResponse<ProductVariationData,Long>> implements ProductVariationApi {

	@Inject
	ProductVariationManager manager;

	@Override
	public List<ProductVariationData> getVariations(Long productId) {
		return manager.list(productId);
	}

	@RolesAllowed({ "gestionnaire" })
	@Override
	public Response deleteVariations(Long productId, List<Long> ids) {
		manager.delete(productId, ids);
		return Response.ok().build();
	}

	@RolesAllowed({ "gestionnaire" })
	@Override
	public Response createVariation(UriInfo uriInfo, Long productId, ProductVariationData variation) {
		final long variationId = manager.create(productId, variation);
		final URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(variationId)).build();

		return Response.created(uri).build();
	}

	@RolesAllowed({ "gestionnaire" })
	@Override
	public Response updateVariation(Long productId, Long variationId, ProductVariationData variation) {
		return manager.update(productId, variationId, variation).map(o -> Response.noContent().build())
				.orElseThrow(NotFoundException::new);
	}
}
