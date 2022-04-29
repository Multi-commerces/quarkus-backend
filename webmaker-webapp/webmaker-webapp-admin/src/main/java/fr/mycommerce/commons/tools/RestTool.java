package fr.mycommerce.commons.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.openapitools.jackson.nullable.JsonNullableModule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.SerializationFeature;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;

import fr.webmaker.data.BaseResource;
import fr.webmaker.data.product.ProductCompositeData;
import fr.webmaker.data.product.ProductData;
import fr.webmaker.data.product.ProductLangCompositeData;
import fr.webmaker.data.product.ProductPricingData;
import fr.webmaker.data.product.ProductSeoData;
import fr.webmaker.data.product.ProductSheetData;
import fr.webmaker.data.product.ProductShippingData;
import fr.webmaker.data.product.ProductStockData;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RestTool {
	
	/**
	 * Pour la lecture et écriture des réponse WEB-API
	 */
	protected ResourceConverter converter;
	
	/**
	 * ObjectMapper utilisé par le converter
	 */
	private ObjectMapper objectMapper;
	
	private Class<?>[] clazz = {ProductSheetData.class, 
			ProductData.class, ProductCompositeData.class,
			ProductLangCompositeData.class, 
			ProductPricingData.class, 
			ProductStockData.class, 
			ProductShippingData.class,
			ProductSeoData.class};
	
	static
	{
		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.registerModule(new JsonNullableModule());
		
		converter = new ResourceConverter(objectMapper, clazz);
	}
	
	/**
	 * Les données au format Json
	 * @return
	 * @throws IOException
	 */
	public <T extends BaseResource> String getJson(T data) throws IOException {
		byte[] flux = RestTool.writeDocument(data);

		return flux != null ? objectMapper.readTree(flux).toPrettyString() : null;
	}
	
	public <T extends BaseResource> byte[] writeDocument(T data) {
		try {
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			
			
			if (data == null) {
				ObjectNode resultNode = objectMapper.createObjectNode();
				resultNode.set("data", null);
				
				byte[] result = null;
				try {
					result = objectMapper.writeValueAsBytes(resultNode);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				return result;
			}
			
			converter.disableSerializationOption(SerializationFeature.INCLUDE_LINKS);
			byte[] flux = converter.writeDocument(new JSONAPIDocument<>(data, objectMapper));
			
			try {
				objectMapper.readTree(flux).toPrettyString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return flux;
		} catch (DocumentSerializationException e) {
			// TODO: handle exception
		}
		return null;
	}

	public <M extends BaseResource> JSONAPIDocument<List<M>> readDocumentCollection(byte[] data, Class<M> clazz) {
		return converter.readDocumentCollection(data, clazz);
	}
	
	public <M extends BaseResource> M readData(byte[] data, Class<M> clazz) {
		return readDocument(data, clazz).get();
	}
	
	public <M extends BaseResource> JSONAPIDocument<M> readDocument(byte[] data, Class<M> clazz) {
		if(data == null)
		{
			return null;
		}
		return converter.readDocument(data, clazz);
	}
	
	public <M extends BaseResource> JSONAPIDocument<M> readDocument(InputStream is, Class<M> clazz) {
		return converter.readDocument(is, clazz);
	}
	
	
}