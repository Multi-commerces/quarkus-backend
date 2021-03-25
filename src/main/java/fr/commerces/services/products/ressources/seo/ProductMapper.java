package fr.commerces.services.products.ressources.seo;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import fr.commerces.services._transverse.GenericResponse;
import fr.commerces.services.products.data.ProductSeoData;
import fr.commerces.services.products.entity.ProductLang;

@ApplicationScoped
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ProductMapper {

	public abstract ProductLang toEntity(ProductSeoData data);

	public abstract ProductSeoData toData(ProductLang entity);

	public abstract GenericResponse<ProductSeoData, Long> toResponse(ProductLang entity);

	@InheritConfiguration
	public abstract ProductLang dataIntoEntity(ProductSeoData data, @MappingTarget ProductLang entity);

}