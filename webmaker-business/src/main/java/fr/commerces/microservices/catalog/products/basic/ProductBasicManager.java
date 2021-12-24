package fr.commerces.microservices.catalog.products.basic;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.logged.ManagerInterceptor;
import fr.commerces.microservices.catalog.products.entity.Product;

@ManagerInterceptor
@ApplicationScoped
public class ProductBasicManager {

	@Inject
	ProductBasicMapper mapper;

	/**
	 * Otenir un produit (données de base) dans une langue
	 * 
	 * @param productId    identifiant
	 * @return
	 * @throws NotFoundException Produit non trouvé
	 */
	public final ProductBasicData findProductBasicByProductAndLang(@NotNull final Long productId) throws NotFoundException {
		return Product.<Product>findByIdOptional(productId)
				.map(mapper::toData)
				.orElseThrow(NotFoundException::new);
	}

	/* ################ Opérations de mise à jour ################ */

	/**
	 * Opération de mise à jour de base d'un produit traduit dans une langue
	 * 
	 * @param language  langue
	 * @param productId identifiant technique du produit
	 * @param data      données produit
	 * @throws NotFoundException produit introuvable
	 */
	@Transactional
	public final void updateProductBasic(@NotNull final LanguageCode language, @NotNull final Long productId,
			@NotNull @Valid final ProductBasicData data) throws NotFoundException {
		/*
		 * MAJ PRODUIT
		 */
		Product.<Product>findByIdOptional(productId).map(pojo -> mapper.toEntity(data, pojo))
				.orElseThrow(NotFoundException::new);
	}

}