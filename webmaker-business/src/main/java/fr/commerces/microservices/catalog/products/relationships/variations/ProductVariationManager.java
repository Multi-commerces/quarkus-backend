package fr.commerces.microservices.catalog.products.relationships.variations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import fr.commerces.microservices.catalog.products.entity.Product;
import fr.commerces.microservices.catalog.products.entity.ProductVariation;
import fr.webmaker.data.product.ProductVariationData;

@ApplicationScoped
public class ProductVariationManager {
	
	@Inject
	private ProductVariationMapper mapper;

	public List<ProductVariationData> list(@NotNull final Long productId) {
		var product = Product.<Product>findByIdOptional(productId).orElseThrow(NotFoundException::new);
		
		return product.getVariations().stream()
				.map(mapper::bind)
				.collect(Collectors.toList());	
	}
	
	public Map<Long, List<ProductVariationData>> findByProductIds(@NotNull final Collection<Long> productIds) {
		try (final Stream<ProductVariation> streamEntity = ProductVariation.findByProductIds(productIds).stream()) {
			return streamEntity
					.collect(Collectors.groupingBy(o -> o.getProduct().getId(),
							Collectors.mapping(mapper::bind, Collectors.toList())));
		}
	}

	@Transactional
	public Long create(@NotNull final Long productId, @NotNull final ProductVariationData productVariation) {
		var product = Product.<Product>findByIdOptional(productId).orElseThrow(NotFoundException::new);
		
		var variation = mapper.unbind(productVariation);
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