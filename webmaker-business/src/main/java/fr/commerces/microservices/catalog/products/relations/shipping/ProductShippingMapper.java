package fr.commerces.microservices.catalog.products.relations.shipping;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import fr.commerces.commons.mapper.DefaultMappingConfig;
import fr.commerces.microservices.catalog.products.entity.Product;
import fr.webmaker.data.product.ProductShippingData;

@ApplicationScoped
@Mapper(config = DefaultMappingConfig.class)
public abstract class ProductShippingMapper {


	/**
	 * Produit Shipping (lecture)
	 * 
	 * @param entity
	 * @return
	 */
	public abstract ProductShippingData toData(Product entity);


	/**
	 * Produit Shipping (mise à jour)
	 * 
	 * @param data
	 * @param entity
	 * @return
	 */
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "productLangs", ignore = true)
	@Mapping(target = "variations", ignore = true)
	@Mapping(target = "images", ignore = true)
	public abstract Product toEntity(ProductShippingData data, @MappingTarget Product entity);
	




}