package fr.commerces.microservices.catalog.products.relationships.variations;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.SerializationFeature;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;

import fr.commerces.commons.resources.JsonApiResource;
import fr.webmaker.data.product.ProductVariationData;

public class ProductVariationResource extends JsonApiResource<ProductVariationData> implements ProductVariationApi {

	@Inject
	ProductVariationManager manager;

	@Inject
	ObjectMapper objectMapper;

	private ResourceConverter converter;
	
	public ProductVariationResource() {
		super(ProductVariationData.class);
	}

	@PostConstruct
	public void postConstruct() {
		converter = new ResourceConverter(objectMapper, ProductVariationData.class);
		converter.enableSerializationOption(SerializationFeature.INCLUDE_RELATIONSHIP_ATTRIBUTES);
		converter.enableSerializationOption(SerializationFeature.INCLUDE_LINKS);
		converter.enableSerializationOption(SerializationFeature.INCLUDE_META);
	}

	@Override
	public Response getVariations(final Long productId) throws DocumentSerializationException {
		var data = manager.list(productId);

		return writeResponse(data, Collections.emptyList());
	}

	@Override
	public Response deleteVariations(Long productId, Long variationId) {
		manager.delete(productId, List.of(variationId));
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
