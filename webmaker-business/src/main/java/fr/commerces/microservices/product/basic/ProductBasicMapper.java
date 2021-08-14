package fr.commerces.microservices.product.basic;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import fr.commerces.commons.mapper.QuarkusMappingConfig;
import fr.commerces.microservices.product.entity.Product;
import fr.commerces.microservices.product.entity.ProductLang;
import fr.webmaker.product.data.ProductBasicData;

@ApplicationScoped
@Mapper(config = QuarkusMappingConfig.class)
public abstract class ProductBasicMapper {

	/**
	 * Mapper dans le cadre d'une opération de lecture
	 * 
	 * @param entity
	 * @return
	 */
	@Mapping(target = ".", source = "product")
	public abstract ProductBasicData toData(ProductLang entity);

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
	public abstract Product toProduct(ProductBasicData data, @MappingTarget Product entity);

	/**
	 * ProductLang
	 * <p>
	 * Mapping des données de data vers entity (ProductLang)
	 * </p>
	 * 
	 * @param data
	 * @param entity
	 * @return
	 */
	public abstract ProductLang toProductLang(ProductBasicData data, @MappingTarget ProductLang entity);

}