package fr.commerces.microservices.catalog.products.relations.stocks;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import fr.commerces.microservices.catalog.products.entity.Product;
import fr.webmaker.annotation.ManagerInterceptor;
import fr.webmaker.data.product.ProductStockData;

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
	 * @return 
	 */
	@Transactional
	public final ProductStockData updateStock(@NotNull final Long productId, @NotNull final ProductStockData data)
			throws NotFoundException {
		
		return Product.<Product>findByIdOptional(productId)
				.map(pojo -> mapper.toEntity(data, pojo))
				.map(mapper::toData)
				.orElseThrow(NotFoundException::new);
		
		
	}

}