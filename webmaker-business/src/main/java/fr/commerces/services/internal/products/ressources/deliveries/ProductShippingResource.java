package fr.commerces.services.internal.products.ressources.deliveries;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import fr.commerces.services._transverse.GenericResource;
import fr.webmaker.common.Identifier;
import fr.webmaker.common.response.CollectionResponse;
import fr.webmaker.product.data.ProductShippingData;

@RequestScoped
public class ProductShippingResource extends GenericResource<CollectionResponse<ProductShippingData, Identifier<Long>>>
		implements ProductShippingApi {

	@Inject
	ProductShippingManager manager;

	@Override
	public Response getProductShipping(final Long productId, final INCLUDE include) {
		final ProductShippingData data = manager.findShippingByProductId(productId);
		// TODO prendre en compte dans une prochaine version INCLUDE
		if (Objects.equals(include, INCLUDE.TRANSPORTERS) && !isNotEmpty(data.getTransporters())) {

		}

		return buildResponse(data, productId);
	}
	
	@Override
	public Response patchProductShipping(final Long productId, final ProductShippingData data) {
		manager.updateShipping(productId, data);
		return Response.noContent().build();
	}

}