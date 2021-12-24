package fr.commerces.microservices.catalog.products.variations;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.SerializationFeature;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;

import fr.commerces.commons.resources.GenericResource;

public class ProductVariationResource extends GenericResource implements ProductVariationApi {

	@Inject
	ProductVariationManager manager;

	@Inject
	ObjectMapper objectMapper;

	private ResourceConverter converter;

	@PostConstruct
	public void postConstruct() {
		converter = new ResourceConverter(objectMapper, ProductVariationData.class);
		converter.enableSerializationOption(SerializationFeature.INCLUDE_RELATIONSHIP_ATTRIBUTES);
		converter.enableSerializationOption(SerializationFeature.INCLUDE_LINKS);
		converter.enableSerializationOption(SerializationFeature.INCLUDE_META);
	}

	@Override
	public byte[] getVariations(final Long productId) throws DocumentSerializationException {
		final Map<Long, ProductVariationData> data = manager.list(productId);

		return converter.writeDocumentCollection(
				new JSONAPIDocument<Collection<ProductVariationData>>(data.values()));
	}

	@Override
	public Response deleteVariations(Long productId, List<Long> ids) {
		manager.delete(productId, ids);
		return Response.ok().build();
	}

	@Override
	public Response createVariation(UriInfo uriInfo, Long productId, ProductVariationData variation) {
		final long variationId = manager.create(productId, variation);
		final URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(variationId)).build();

		return Response.created(uri).build();
	}

	@Override
	public Response updateVariation(Long productId, Long variationId, ProductVariationData variation) {
		return manager.update(productId, variationId, variation).map(o -> Response.noContent().build())
				.orElseThrow(NotFoundException::new);
	}
}
