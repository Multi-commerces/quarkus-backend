package fr.commerces.microservices.catalog.products.entity;

import java.util.Collection;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import fr.commerces.microservices.catalog.categories.Category;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import lombok.Getter;
import lombok.Setter;



@Entity @Getter @Setter
@Table(name = "PRODUCT_CATEGORY")
public class ProductCategory extends PanacheEntityBase  {

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
		return find("product.id in (:productIds)", Parameters.with("productIds", productIds));
	}
}
