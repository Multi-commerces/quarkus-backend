package fr.commerces.services.internal.products.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
//@Cacheable(false)
@Table(name = "PRODUCT_VARIATION", uniqueConstraints = { @UniqueConstraint(columnNames = { "ID_PRODUCT_VARIATION" }) })
public class ProductVariation extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PRODUCT_VARIATION")
	public Long id;

	@Column(name = "REFERENCE_SHOP")
	public String referenceShop;

	@Column(name = "REFERENCE_PROVIDER")
	public String referenceProvider;

	@Column(name = "NAME")
	public String name;

	@Column(name = "PICTURE")
	public byte[] picture;

	@Column(name = "PRICE_IMPACT")
	public Double priceImpact;

	@Column(name = "QUANTITY")
	public Long quantity;

	@Column(name = "IS_DEFAULT_VARIATION")
	public boolean defaultVariation;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Product.class)
	@JoinColumn(name = "ID_PRODUCT")
	public Product product;

}
