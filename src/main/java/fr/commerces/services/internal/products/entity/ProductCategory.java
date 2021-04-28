package fr.commerces.services.internal.products.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import fr.commerces.services.internal.categories.Category;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;



@Entity @Getter @Setter
@Table(name = "PRODUCT_CATEGORY")
public class ProductCategory extends PanacheEntityBase  {

	@EmbeddedId
	private ProductCategoryPK id;

	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne
	@MapsId("categoryId")
	@JoinColumn(name = "category_id")
	private Category category;


}
