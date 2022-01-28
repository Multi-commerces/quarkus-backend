package fr.commerces.microservices.catalog.products.entity;

import java.util.Collection;
import java.util.stream.Stream;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import fr.commerces.microservices.catalog.categories.entity.Category;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "PRODUCT_CATEGORY")
public class ProductCategory extends PanacheEntityBase {

	@EmbeddedId
	private ProductCategoryPK identity;

	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne
	@MapsId("categoryId")
	@JoinColumn(name = "category_id")
	private Category category;

	public ProductCategory() {
		super();
	}

	public ProductCategory(Product product, Category category) {
		super();
		this.product = product;
		this.category = category;
		this.identity = new ProductCategoryPK(product.getId(), category.getId());
	}

	/*
	 * ###################################################################
	 * ###################### METHODES PanacheQuery ######################
	 * ###################################################################
	 */

	/**
	 * Recherche les catégories des produits
	 * 
	 * @param productId identifiant du produit
	 * @return
	 */
	public static PanacheQuery<ProductCategory> findByProductIds(final Collection<Long> productIds) {
		return find("SELECT pc FROM ProductCategory pc " 
						+ "INNER JOIN FETCH pc.category c "
						+ "INNER JOIN FETCH pc.product p " 
						+ "WHERE p.id in (:productIds)",
				Parameters.with("productIds", productIds));
	}
	
	public static Stream<ProductCategory> findByIds(final Collection<Long> productIds, final Collection<Long> categoryIds) {
		return find("SELECT pc FROM ProductCategory pc "
						+ "INNER JOIN FETCH pc.category c "
						+ "INNER JOIN FETCH pc.product p " 
						+ "WHERE p.id in (?1) and c.id in (?2)", productIds, categoryIds)
				.stream();
	}
	
	public static long delete(final Collection<Long> productIds, final Collection<Long> categoryIds) {
		return delete("ProductCategory pc WHERE pc.identity.productId in (?1) and pc.identity.categoryId in (?2)", productIds, categoryIds);
	}

}
