package fr.commerces.web.shop;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.services.internal.products.data.ProductData;
import fr.commerces.services.internal.products.manager.ProductManager;
import lombok.Getter;

@RequestScoped
@Named
public class ProductMB {

	@Inject
	ProductManager manager;

	@Getter
	Collection<ProductData> items;

	@PostConstruct
	public void postConstruct() {
		items = manager.findAllByLanguageCode(LanguageCode.fr, Optional.of(1), Optional.of(10)).values();
		int i = 1;

		for (ProductData productData : items) {
			
			int leftLimit = 97; // letter 'a'
		    int rightLimit = 122; // letter 'z'
		    int targetStringLength = 10;
		    Random random = new Random();

		    String generatedString = random.ints(leftLimit, rightLimit + 1)
		      .limit(targetStringLength)
		      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		      .toString();
		    
		    productData.setSummary(generatedString);
		    
			productData.getImageCover().setHref("images/" + String.valueOf(i++) + ".jpg");
			
			
		}


	}

}
