package fr.commerces.microservices.catalog.products.deliveries;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import fr.commerces.commons.logged.ManagerInterceptor;
import fr.commerces.microservices.catalog.products.entity.Product;
import fr.webmaker.microservices.catalog.products.data.ProductShippingData;

@ManagerInterceptor
@ApplicationScoped
//@AlternativePriority(Interceptor.Priority.APPLICATION)
public class ProductShippingManager {

	@Inject
	ProductShippingMapper mapper;

	/* ################ Opérations de lecture ################ */

	/**
	 * Obtenir les informations d'expédition du produit
	 * 
	 * @param productId
	 * @return
	 */
	public final ProductShippingData findShippingByProductId(@NotNull final Long productId) {
		final Product product = Product.<Product>findByIdOptional(productId).orElseThrow(NotFoundException::new);
		return mapper.toData(product);
	}

	/* ################ Opérations de mise à jour ################ */

	/**
	 * Opération de mise à jour des informations d'expédition du produit
	 * 
	 * @param productId
	 * @param data
	 */
	@Transactional
	public final void updateShipping(@NotNull final Long productId, @NotNull final ProductShippingData data)
			throws NotFoundException {
		Product.<Product>findByIdOptional(productId).map(pojo -> mapper.toEntity(data, pojo))
				.orElseThrow(NotFoundException::new);
	}



}