package fr.commerces.services.products.ressources.variations;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import fr.commerces.services.products.data.ProductVariationData;

@ApplicationScoped
public class ProductVariationManager {

	public List<ProductVariationData> list(Long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long create(Long productId, ProductVariationData variation) {
		// TODO Auto-generated method stub
		return 0L;
	}
	
	public Optional<Long> update(Long productId, Long variationId, ProductVariationData variation) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void delete(Long productId, List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

	

	

}