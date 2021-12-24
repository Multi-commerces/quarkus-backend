package fr.commerces.microservices.catalog.products.stocks;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.SerializationFeature;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;

import fr.commerces.commons.resources.GenericResource;

@RequestScoped
public class ProductStockResource extends GenericResource implements ProductStockApi {

	@Inject
	ProductStockManager manager;

	public static final String resourceName = "stocks";

	@Inject
	ObjectMapper objectMapper;

	private ResourceConverter converter;

	@PostConstruct
	public void postConstruct() {
		converter = new ResourceConverter(objectMapper, ProductStockData.class);
		converter.enableSerializationOption(SerializationFeature.INCLUDE_RELATIONSHIP_ATTRIBUTES);
		converter.enableSerializationOption(SerializationFeature.INCLUDE_LINKS);
		converter.enableSerializationOption(SerializationFeature.INCLUDE_META);
	}

	@Override
	public byte[] getProductStock(Long productId) throws DocumentSerializationException {
		final ProductStockData data = manager.findStockByProductId(productId);

		return converter.writeDocument(new JSONAPIDocument<ProductStockData>(data));
	}

	@Override
	public Response patchProductStock(Long productId, ProductStockData data) {
		manager.updateStock(productId, data);
		return Response.noContent().build();
	}

}