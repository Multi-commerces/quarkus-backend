package fr.commerces.microservices.catalog.products.basic;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import fr.commerces.commons.mapper.DefaultMappingConfig;
import fr.commerces.microservices.catalog.products.entity.Product;

@ApplicationScoped
@Mapper(config = DefaultMappingConfig.class)
public abstract class ProductBasicMapper {

	/**
	 * Mapper dans le cadre d'une opération de lecture
	 * 
	 * @param entity
	 * @return
	 */
	public abstract ProductBasicData toData(Product entity);

	/**
	 * Mapper dans le cadre d'une opération de mise à jour
	 * <p>
	 * Mapping des données de data (ProductBasicData) vers entity (Product)
	 * </p>
	 * 
	 * @param data
	 * @param entity
	 * @return
	 */
	public abstract Product toEntity(ProductBasicData data, @MappingTarget Product entity);


}