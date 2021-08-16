package fr.commerces.microservices.catalog.products.entity;

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

import fr.commerces.microservices.catalog.images.ShopImage;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;


@Entity @Getter @Setter
@Cacheable(false)
@Table(name = "PRODUCT_IMAGE", uniqueConstraints = { 
		@UniqueConstraint(columnNames = { "PRODUCT_IMAGE_ID" }),
		@UniqueConstraint(columnNames = { "IMAGE_ID", "PRODUCT_ID" })
})
public class ProductImage extends PanacheEntityBase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PRODUCT_IMAGE_ID")
	public Long id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = ShopImage.class)
	@JoinColumn(name = "IMAGE_ID", nullable = false)
	public ShopImage image;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Product.class)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	public Product product;
	
	@Column(name = "POSITION", nullable = false, columnDefinition = "integer default 0")
	public Integer position;

}
