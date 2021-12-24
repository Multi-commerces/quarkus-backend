package fr.commerces.microservices.catalog.products;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;

import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;
import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.exceptions.crud.NotFoundException;
import fr.commerces.commons.resources.GenericResource;
import fr.commerces.microservices.catalog.categories.CategoryManager;
import fr.commerces.microservices.catalog.categories.data.CategoryData;
import fr.commerces.microservices.catalog.categories.data.CategoryRelationData;
import fr.commerces.microservices.catalog.categories.data.CategoryLangData;
import fr.commerces.microservices.catalog.products.lang.ProductLangData;
import fr.commerces.microservices.catalog.products.shipping.ProductShippingData;
import fr.webmaker.commons.request.PageRequest;


public class ProductResource extends GenericResource implements ProductResourceApi {

	@Inject
	ProductManager manager;
	
	@Inject
	CategoryManager categoryManager;
	
	public ProductResource() {
		converter = new ResourceConverter(objectMapper, 
				CategoryRelationData.class, ProductDepth1Data.class,
				ProductShippingData.class,
				ProductDepth1Data.class, CategoryLangData.class, 
				CategoryData.class, ProductData.class, ProductLangData.class);
	}
	
	/**
	 * GET ALL
	 * @throws DocumentSerializationException 
	 */
	@Override
	public byte[] getProducts(final String languageCode, final PageRequest page) throws DocumentSerializationException {
		final List<ProductDepth1Data> data = manager.findAll();

		return converter.writeDocumentCollection(
				new JSONAPIDocument<List<ProductDepth1Data>>(data));
	}
	
	/**
	 * GET BY ID
	 * @throws DocumentSerializationException 
	 */
	@Override
	public byte[] getProductById(final LanguageCode languageCode, final Long idProduct) throws DocumentSerializationException {
		final ProductDepth1Data data = manager.findById(idProduct);
		
		byte[] values = converter.writeDocument(
				new JSONAPIDocument<ProductDepth1Data>(
						data));
		
		return values;
	}

	/**
	 * CREATE BY LANG
	 */
	@Override
	public Response createProductLang(final String languageCode, final ProductLangData data) {
		Long newId = 12L;//manager.createProduct(data);

		final URI uri = uriInfo.getAbsolutePathBuilder().path(newId.toString()).build();

		
		return Response.created(uri).language(Locale.FRENCH).build();
	}

	/**
	 * DELETE BY ID & LANG
	 */
	@Override
	public Response deleteProductLang(final String languageCode, final Long productId) {
		
		return null;
	}

	
	
	@Override
	public byte[] getCategories(Long productId) throws DocumentSerializationException {
		final Map<Long, List<CategoryRelationData>> categoriesByProductId = categoryManager
				.findCategoriesByProductIds(Arrays.asList(productId));
		if(CollectionUtils.isEmpty(categoriesByProductId.get(productId))) {
			throw new NotFoundException(productId);
			
		}
		
//		converter.disableSerializationOption(SerializationFeature.INCLUDE_RELATIONSHIP_ATTRIBUTES);
		
		return converter.writeDocumentCollection(
				new JSONAPIDocument<List<CategoryRelationData>>(
						categoriesByProductId.get(productId), null, null, objectMapper));
	}

}