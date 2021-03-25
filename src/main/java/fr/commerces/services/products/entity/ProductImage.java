package fr.commerces.services.products.entity;

import javax.persistence.Cacheable;
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
@Cacheable(false)
@Table(name = "PRODUCT_IMAGE", uniqueConstraints = { @UniqueConstraint(columnNames = { "PRODUCT_IMAGE_ID" }) })
public class ProductImage extends PanacheEntityBase {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PRODUCT_IMAGE_ID")
	public Long id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Product.class)
	@JoinColumn(name = "product_id")
	public Product product;

	@Column(name = "TITLE")
	public String title;

	@Column(name = "PICTURE")
	public byte[] picture;

	@Column(name = "COVER")
	public Boolean cover;

}
