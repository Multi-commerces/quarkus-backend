package fr.commerces.services.internal.products.data;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.commerces.services._transverse.data.LinkData;
import lombok.Data;

@Data
public class ProductImageData {

	private String name;
	
	private String title;
	
	public Boolean cover;
	
	@JsonProperty("_picture")
	private LinkData picture;
	
	@JsonIgnore
	public static String randomName() {
		return UUID.randomUUID().toString();
	}



	public ProductImageData() {
		super();
	}

}
