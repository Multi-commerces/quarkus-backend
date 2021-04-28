package fr.commerces.services.internal.products.mapper;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ObjectFactory;

import fr.commerces.services.internal.products.data.ProductVariationData;
import fr.commerces.services.internal.products.entity.Product;
import fr.commerces.services.internal.products.entity.ProductVariation;

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

	/**
	 * bind pour l'opérations de lecture
	 * 
	 * @param entity
	 * @return
	 */
	public abstract ProductVariationData bind(ProductVariation entity);

	/**
	 * Pour l'opération de mise à jour
	 * 
	 * @param data
	 * @param entity
	 */
	@Mapping(target = "id", ignore = true)
	@InheritConfiguration
	public abstract ProductVariation dataIntoEntity(ProductVariationData data, @MappingTarget ProductVariation entity);

	@ObjectFactory
	public ProductVariation resolve(ProductVariationData data) {
		if (data == null || data.getId() == null) {
			return new ProductVariation();
		}

		return Product.<ProductVariation>findByIdOptional(data.getId()).orElse(new ProductVariation());
	}

}