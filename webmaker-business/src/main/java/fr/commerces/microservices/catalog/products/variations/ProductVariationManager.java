package fr.commerces.microservices.catalog.products.variations;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import fr.commerces.microservices.catalog.products.entity.Product;
import fr.commerces.microservices.catalog.products.entity.ProductVariation;

@ApplicationScoped
public class ProductVariationManager {
	
	@Inject
	private ProductVariationMapper mapper;

	public Map<Long, ProductVariationData> list(@NotNull final Long productId) {
		try (final Stream<ProductVariation> streamEntity = ProductVariation.findByProductId(productId).stream()) {
			var map = streamEntity.collect(Collectors.toMap(ProductVariation::getId, Function.identity()));
			return mapper.toMap(map);
		}
	}

	@Transactional
	public Long create(@NotNull final Long productId, @NotNull final ProductVariationData productVariation) {
		final Product product = Product.<Product>findByIdOptional(productId).orElseThrow(NotFoundException::new);
		
		final ProductVariation variation = mapper.unbind(productVariation);
		variation.setProduct(product);
		
		// Enregistrement
		ProductVariation.persist(variation);
		
		return variation.getId();
	}
	
	@Transactional
	public Optional<Long> update(Long productId, Long variationId, ProductVariationData variation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public void delete(Long productId, List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

	

	

}