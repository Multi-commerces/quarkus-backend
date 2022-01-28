package fr.commerces.microservices.catalog.products.relationships.variations;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import fr.commerces.microservices.catalog.products.entity.ProductVariation;
import fr.webmaker.data.product.ProductVariationData;

@ApplicationScoped
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ProductVariationMapper {

	/**
	 * unbind pour l'opération de création
	 * 
	 * @param data
	 * @return
	 */
	@Mapping(target = "id", ignore = true)
	public abstract ProductVariation unbind(ProductVariationData data);
	public abstract Map<Long, ProductVariationData> toMap(Map<Long, ProductVariation> entity);

	/**
	 * bind pour l'opérations de lecture
	 * 
	 * @param entity
	 * @return
	 */
	public abstract ProductVariationData bind(ProductVariation entity);
	public abstract List<ProductVariationData> bind(List<ProductVariation> entities);

	/**
	 * Pour l'opération de mise à jour
	 * @ObjectFactory pour créer un resolve
	 * @param data
	 * @param entity
	 */
	@Mapping(target = "id", ignore = true)
	@InheritConfiguration
	public abstract ProductVariation dataIntoEntity(ProductVariationData data, @MappingTarget ProductVariation entity);

}