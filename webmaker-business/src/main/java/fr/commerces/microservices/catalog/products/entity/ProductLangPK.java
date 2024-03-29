package fr.commerces.microservices.catalog.products.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.neovisionaries.i18n.LanguageCode;

import lombok.Data;

@Data
@Embeddable
public class ProductLangPK implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ProductLangPK() {
		super();
	}

	public ProductLangPK(final Long idProduct, final LanguageCode languageCode) {
		super();
		this.idProduct = idProduct;
		this.language = languageCode;
	}

	@Column(name = "PRODUCT_ID", length = 10, updatable = false)
	Long idProduct;

	@Enumerated(EnumType.STRING)
	@Column(name = "CODE_LANG", length = 10, updatable = true)
	LanguageCode language;

}
