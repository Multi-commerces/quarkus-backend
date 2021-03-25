package fr.commerces.services.products.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSeoData implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 0 of 70 characters used (recommended)
	 */
	private String metatitle;

	/**
	 * 0 of 160 characters used (recommended)
	 */
	private String metaDescription;

	private String friendlyURL;

}
