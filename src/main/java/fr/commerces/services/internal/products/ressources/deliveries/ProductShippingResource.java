package fr.commerces.services.internal.products.ressources.deliveries;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import fr.commerces.services._transverse.GenericResource;
import fr.commerces.services._transverse.response.CollectionResponse;
import fr.commerces.services._transverse.response.SingleResponse;
import fr.commerces.services.internal.products.data.ProductShippingData;
import fr.commerces.services.internal.products.manager.ProductManager;

@RequestScoped
public class ProductShippingResource extends GenericResource<CollectionResponse<ProductShippingData, Long>>
		implements ProductShippingApi {

	@Inject
	ProductManager manager;

	@Override
	public Response getProductShipping(final Long productId, final INCLUDE include) {
		final ProductShippingData data = manager.findShippingByProductId(productId);
		// TODO prendre en compte dans une prochaine version INCLUDE
		if (Objects.equals(include, INCLUDE.TRANSPORTERS) && !isNotEmpty(data.getTransporters())) {

		}

		// RESPONSE
		final SingleResponse<ProductShippingData, Long> response = new SingleResponse<ProductShippingData, Long>();
		response.setData(data);
		response.setId(productId);

		return Response.ok(response).build();
	}

	@Override
	public Response postProductShipping(final Long productId, ProductShippingData data) {
		manager.updateShipping(productId, data);
		return Response.noContent().build();
	}

}