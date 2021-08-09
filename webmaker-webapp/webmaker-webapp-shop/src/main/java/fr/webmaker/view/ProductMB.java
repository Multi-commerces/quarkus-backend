package fr.webmaker.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.cdi.ViewScoped;

import fr.webmaker.common.response.CollectionResponse;
import fr.webmaker.product.ProductID;
import fr.webmaker.product.data.ProductData;
import fr.webmaker.resource.ProductRestClient;
import lombok.Getter;

@ViewScoped
@Named
public class ProductMB implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@RestClient
	ProductRestClient service;

	@Getter
	private Map<Long, ProductData> items = new HashMap<Long, ProductData>();

	@PostConstruct
	public void postConstruct() {
		CollectionResponse<ProductData, ProductID> values = service.getProducts("fr", 1, 10);
		
		values.get_embedded().forEach(o -> {
			items.put(o.getIdentifier().getId(), o.getData());
		});

		for (Entry<Long, ProductData> entry : items.entrySet()) {
			ProductData productData = entry.getValue();
			
			int leftLimit = 97; // letter 'a'
		    int rightLimit = 122; // letter 'z'
		    int targetStringLength = 10;
		    Random random = new Random();

		    String generatedString = random.ints(leftLimit, rightLimit + 1)
		      .limit(targetStringLength)
		      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		      .toString();
		    
//		    productData.setSummary(generatedString);
		}


	}

}
