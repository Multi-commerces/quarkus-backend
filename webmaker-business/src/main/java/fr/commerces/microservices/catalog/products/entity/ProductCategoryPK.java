package fr.commerces.microservices.catalog.products.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
@EqualsAndHashCode
public class ProductCategoryPK implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ProductCategoryPK() {
		super();
	}

	@Column(name = "product_id", length = 10, updatable = false)
	private Long productId;

	@Column(name = "category_id", length = 10, updatable = false)
	private Long categoryId;

}
