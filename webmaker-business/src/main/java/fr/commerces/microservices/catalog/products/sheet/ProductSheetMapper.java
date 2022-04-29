package fr.commerces.microservices.catalog.products.sheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.github.jasminb.jsonapi.Link;
import com.github.jasminb.jsonapi.Links;

import fr.commerces.commons.mapper.DefaultMappingConfig;
import fr.commerces.microservices.catalog.products.entity.Product;
import fr.commerces.microservices.catalog.products.entity.ProductLang;
import fr.webmaker.data.DualData;
import fr.webmaker.data.product.ProductSheetData;

@ApplicationScoped
@Mapper(config = DefaultMappingConfig.class)
public abstract class ProductSheetMapper {

	List<ProductSheetData> wrap(final List<ProductLang> source) {
		return source.stream().map(this::wrap)
				// Ajout du lien vers la gestion de la ressource basique produit
				.map(data -> {
					String href = String.join("/", "products", data.getId());

					@SuppressWarnings("serial")
					Map<String, Link> linkMap = new HashMap<>() {
						{
							put("product", new Link(href));
						}
					};

					data.setLinks(new Links(linkMap));

					return data;
				}).collect(Collectors.toList());
	}

	@Named(value = "wrapProduct")
	abstract ProductSheetData wrap(Product source);

	@Mapping(target = "languageCode", source = "identity.language")
	@Mapping(target = ".", source = "product", qualifiedByName = "wrapProduct")
	abstract ProductSheetData wrap(ProductLang source);

	@Mapping(target = "id", ignore = true)
	abstract Product unwrap(ProductSheetData data, @MappingTarget Product entity);
	abstract ProductLang unwrap(ProductSheetData data, @MappingTarget ProductLang entity);

	@Mapping(target = "dataA", source = ".")
	@Mapping(target = "dataB", source = ".")
	abstract DualData<Product, ProductLang> unwrap(ProductSheetData data);

}