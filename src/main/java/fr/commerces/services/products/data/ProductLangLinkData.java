package fr.commerces.services.products.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.commerces.services._transverse.LinkData;
import lombok.Data;

@Data
public class ProductLangLinkData {

	private String codeLang;
	
	@JsonProperty("links")
	private List<LinkData> lang = new ArrayList<>();
	


}
