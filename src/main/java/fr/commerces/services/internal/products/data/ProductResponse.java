package fr.commerces.services.internal.products.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.commerces.services._transverse.response.CollectionResponse;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductResponse extends CollectionResponse<ProductData, Long> {
	
	@JsonProperty("lang")
	protected  String lang;


	

}
