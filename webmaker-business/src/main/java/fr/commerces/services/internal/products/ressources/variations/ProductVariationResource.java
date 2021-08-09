package fr.commerces.services.internal.products.ressources.variations;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import fr.commerces.services._transverse.GenericResource;
import fr.webmaker.common.response.CollectionResponse;
import fr.webmaker.product.ProductVariationID;
import fr.webmaker.product.data.ProductVariationData;
import fr.webmaker.product.response.ProductVariationDataResponse;

public class ProductVariationResource extends GenericResource<CollectionResponse<ProductVariationData, ProductVariationID>>
		implements ProductVariationApi {

	@Inject
	ProductVariationManager manager;

	@Override
	public List<ProductVariationDataResponse> getVariations(Long productId) {
		final Map<Long, ProductVariationData> productById = manager.list(productId);

		return productById.entrySet().stream()
			.map(entry -> new ProductVariationDataResponse(entry.getKey(), productId, entry.getValue()))
			.collect(Collectors.toList());
	}

//	@RolesAllowed({ "gestionnaire" })
//	@Override
//	public Response deleteVariations(Long productId, List<Long> ids) {
//		manager.delete(productId, ids);
//		return Response.ok().build();
//	}

//	@RolesAllowed({ "gestionnaire" })
	@Override
	public Response createVariation(UriInfo uriInfo, Long productId, ProductVariationData variation) {
		final long variationId = manager.create(productId, variation);
		final URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(variationId)).build();

		return Response.created(uri).build();
	}

//	@RolesAllowed({ "gestionnaire" })
	@Override
	public Response updateVariation(Long productId, Long variationId, ProductVariationData variation) {
		return manager.update(productId, variationId, variation).map(o -> Response.noContent().build())
				.orElseThrow(NotFoundException::new);
	}
}
