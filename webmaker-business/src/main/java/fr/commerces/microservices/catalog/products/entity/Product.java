package fr.commerces.microservices.catalog.products.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import fr.commerces.microservices.catalog.images.entity.ShopImage;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "PRODUCT", uniqueConstraints = { @UniqueConstraint(columnNames = { "product_id" }) })
public class Product extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	public Long id;

	@Column(name = "reference")
	public String reference;

	@Column(name = "code_Client")
	public String codeClient;

	@Column(name = "code_Barre")
	public String codeBarre;

	@Column(name = "price_HT")
	public Double priceHT;

//	@Column(name = "tax_Rule")
//	public Double taxRule;

	@Column(name = "quantity")
	public Long quantity;

	@Column(name = "package_Width")
	public Integer packageWidth;

	@Column(name = "package_Height")
	public Integer packageHeight;

	@Column(name = "package_Depth")
	public Integer packageDepth;

	@Column(name = "package_Weight")
	public Integer packageWeight;
	
	@Column(name = "delivery_Time_QuantityOK")
	private Integer deliveryTimeQuantityOK;
	
	@Column(name = "delivery_Time_QuantityNOK")
	private Integer deliveryTimeQuantityNOK;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<ProductCategory> categories = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, targetEntity = ProductLang.class, mappedBy = "product", cascade = {
			CascadeType.REMOVE }, orphanRemoval = true)
	private List<ProductLang> productLang = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, targetEntity = ProductVariation.class, mappedBy = "product", cascade = {
			CascadeType.REMOVE }, orphanRemoval = true)
	private List<ProductVariation> variations = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, targetEntity = ProductImage.class, mappedBy = "product", cascade = {
			CascadeType.REMOVE, CascadeType.MERGE }, orphanRemoval = true)
	private List<ProductImage> images = new ArrayList<>();
	
	@OneToOne
    @JoinColumn(name="IMAGE_ID")
    private ShopImage coverImage ; 
	
	
}
