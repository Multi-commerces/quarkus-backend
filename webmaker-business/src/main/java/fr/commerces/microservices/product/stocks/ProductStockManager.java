package fr.commerces.microservices.product.stocks;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import fr.commerces.commons.logged.ManagerInterceptor;
import fr.commerces.microservices.product.entity.Product;
import fr.webmaker.product.data.ProductStockData;

@ManagerInterceptor
@ApplicationScoped
//@AlternativePriority(Interceptor.Priority.APPLICATION)
public class ProductStockManager {

	@Inject
	ProductStockMapper mapper;

	/* ################ Opérations de lecture ################ */

	/**
	 * Obtenir les informations de stock du produit
	 * 
	 * @param productId
	 * @return
	 */
	public final ProductStockData findStockByProductId(@NotNull final Long productId) {
		return Product.<Product>findByIdOptional(productId).map(mapper::toData).orElseThrow(NotFoundException::new);
	}

	/* ################ Opérations de mise à jour ################ */

	/**
	 * Opération de mise à jour des informations d'expédition du produit
	 * 
	 * @param productId
	 * @param data
	 */
	@Transactional
	public final void updateStock(@NotNull final Long productId, @NotNull final ProductStockData data)
			throws NotFoundException {
		Product.<Product>findByIdOptional(productId).map(pojo -> mapper.toEntity(data, pojo))
				.orElseThrow(NotFoundException::new);
	}

}