package fr.commerces.services.products.ressources.products;

import java.util.Collection;
import java.util.Optional;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.services._transverse.GenericResponse;
import fr.commerces.services.products.data.ProductData;

public interface ProductService {

	Collection<GenericResponse<ProductData, Long>> list(LanguageCode language, Optional<Integer> page, Optional<Integer> size);

	GenericResponse<ProductData, Long> findById(Long id);

	void update(Long id, ProductData data);

	Long create(LanguageCode language, ProductData data);
	
	

}