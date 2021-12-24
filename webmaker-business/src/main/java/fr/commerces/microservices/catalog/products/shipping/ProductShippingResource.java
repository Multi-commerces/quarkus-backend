package fr.commerces.microservices.catalog.products.shipping;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import fr.commerces.commons.resources.GenericResource;

@RequestScoped
public class ProductShippingResource extends GenericResource implements ProductShippingApi {

	@Inject
	ProductShippingManager manager;

	@Override
	public Response getProductShipping(final Long productId, final INCLUDE include) {
		final ProductShippingData data = manager.findShippingByProductId(productId);
		// TODO prendre en compte dans une prochaine version INCLUDE
		if (Objects.equals(include, INCLUDE.TRANSPORTERS) && !isNotEmpty(data.getTransporters())) {

		}
		// buildResponse(data, productId);
		return null;
	}
	
	@Override
	public Response patchProductShipping(final Long productId, final ProductShippingData data) {
		manager.updateShipping(productId, data);
		return Response.noContent().build();
	}

}