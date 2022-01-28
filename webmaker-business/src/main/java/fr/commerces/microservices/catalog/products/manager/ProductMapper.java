package fr.commerces.microservices.catalog.products.manager;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import fr.commerces.commons.resources.DefaultMappingConfig;
import fr.commerces.microservices.catalog.products.entity.Product;
import fr.webmaker.data.BaseResource;
import fr.webmaker.data.product.ProductCompositeData;
import fr.webmaker.data.product.ProductData;

/**
 * Mapper Product
 * 
 * https://stackabuse.com/guide-to-mapstruct-in-java-advanced-mapping-library/
 * 
 * @author Julien ILARI
 *
 */
@ApplicationScoped
@Mapper(uses = ProductFactory.class, config = DefaultMappingConfig.class)
public abstract class ProductMapper {

	@BeanMapping(resultType = ProductCompositeData.class)
	@Mapping(target = "imageCover", ignore = true)
	@Mapping(target = "variations", ignore = true)
	@Mapping(target = "categories", ignore = true)
	@Mapping(target = "productLangs", ignore = true)
	abstract ProductCompositeData wrapComposite(Product source);

	@BeanMapping(resultType = ProductData.class)
	abstract BaseResource wrap(Product source);

	/**
	 * PATCH | POST (Opération de création ou mise à jour partielle)
	 */
	@Mapping(target = "id", ignore = true)
	abstract Product unwrap(ProductData data, @Context Long id);

	/**
	 * UPDATE (Opération de mise à jour)
	 * 
	 * @param data
	 * @param entity @MappingTarget
	 * @return
	 */
	@Mapping(target = "id", ignore = true)
	abstract Product unwrap(ProductData data, @MappingTarget Product entity);
	
	Product unwrap(ProductData data)
	{
		return unwrap(data, (Long) null);
	}

}