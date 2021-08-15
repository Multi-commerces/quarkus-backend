package fr.commerces.microservices.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.neovisionaries.i18n.LanguageCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class CategoryLangPK implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public CategoryLangPK() {
		super();
	}

	public CategoryLangPK(final Long categoryId, final LanguageCode languageCode) {
		super();
		this.categoryId = categoryId;
		this.language = languageCode;
	}

	@Column(name = "CATEGORY_ID", length = 10, updatable = false)
	private Long categoryId;

	@Enumerated(EnumType.STRING)
	@Column(name = "CODE_LANG", length = 10, updatable = true)
	private LanguageCode language;

}
