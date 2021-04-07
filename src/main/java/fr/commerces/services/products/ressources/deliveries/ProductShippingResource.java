package fr.commerces.services.products.ressources.deliveries;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import fr.commerces.services._transverse.GenericResponse;
import fr.commerces.services.products.data.ProductShippingData;
import fr.commerces.services.products.manager.ProductManager;


@RequestScoped
public class ProductShippingResource implements ProductShippingApi {
	
	@Inject
	ProductManager manager;

	@Override
	public GenericResponse<ProductShippingData, Long> getProductShipping(Long productId, INCLUDE include) {
		// TODO prendre en compte dans une prochaine version INCLUDE
		return manager.findProductShippingById(productId);
	}

	@Override
	public Response postProductShipping(Long productId, ProductShippingData data) {
		manager.updateProductShipping(productId, data);
		return Response.noContent().build();
	}

}