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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import fr.commerces.microservices.catalog.images.entities.ShopImage;
import fr.webmaker.exception.crud.NotFoundUpdateException;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Entity
@NamedQueries({
    @NamedQuery(name = "Product.findByRef", query = "from Product where reference = ?1")
})
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

	@Column(name = "taxe_Rule")
	public Double taxeRule;

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

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductCategory> categories = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, targetEntity = ProductLang.class, mappedBy = "product", cascade = {
			CascadeType.REMOVE, CascadeType.PERSIST }, orphanRemoval = true)
	private List<ProductLang> productLangs = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, targetEntity = ProductVariation.class, mappedBy = "product", cascade = {
			CascadeType.REMOVE }, orphanRemoval = true)
	private List<ProductVariation> variations = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, targetEntity = ProductImage.class, mappedBy = "product", cascade = {
			CascadeType.REMOVE, CascadeType.MERGE }, orphanRemoval = true)
	private List<ProductImage> images = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="IMAGE_ID")
    private ShopImage coverImage ;

	public static void main(String[] args) {
		
	} 
	
	public static Product findByIdOrElseThrow(final Long productId) {
		return Product.<Product>findByIdOptional(productId)
				.orElseThrow(() -> new NotFoundUpdateException(productId));
	}
	
	public static Product findByRef(final String reference) {
		return find("#Product.findByRef", reference).firstResult();
	}
	
	
}
