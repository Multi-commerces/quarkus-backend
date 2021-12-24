package fr.commerces.microservices.catalog.categories.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import fr.commerces.microservices.catalog.products.entity.ProductCategory;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Cacheable(true)
@Getter @Setter 
@ToString(onlyExplicitlyIncluded = true) 
@EqualsAndHashCode(callSuper=false,onlyExplicitlyIncluded = true)
@Table(name = "CATEGORY", uniqueConstraints = { @UniqueConstraint(columnNames = { "category_id" }) })
public class Category extends PanacheEntityBase {

	@Id
	@Column(name = "category_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Include 
	@ToString.Include
	public Long id;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	public List<ProductCategory> productCategories = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "category_parent_id", nullable = true)
	public Category parentCategory;

	/**
	 * Liste des sous-catégories FetchType.LAZY
	 */
	@Fetch(FetchMode.JOIN)
	@OneToMany(cascade = {
			CascadeType.ALL }, mappedBy = "parentCategory", fetch = FetchType.LAZY, orphanRemoval = false)
	@OrderBy("position")
	public Set<Category> childrenCategory = new HashSet<>();

	@Column(nullable = false)
	@ToString.Include
	public int position;

	/**
	 * catégorie activé/désactivé
	 */
	@ToString.Include
	public boolean displayed;

	/**
	 * Image de la catégorie (version non retourchée)
	 */
	public byte[] bigPicture;

	/**
	 * Image de la catégorie (version réduite)
	 */
	public byte[] smallPicture;

	@Column(columnDefinition = "timestamp default current_timestamp", nullable = false, updatable = false, insertable = true)
	@CreationTimestamp
	public LocalDateTime created;

	@Column(columnDefinition = "timestamp default current_timestamp", nullable = false, updatable = true, insertable = true)
	@UpdateTimestamp
	public LocalDateTime updated;
	
	@Fetch(FetchMode.JOIN)
	@OneToMany(fetch = FetchType.LAZY, targetEntity = CategoryLang.class, mappedBy = "category", cascade = {
			CascadeType.REMOVE }, orphanRemoval = true)
	private List<CategoryLang> categoryLangs = new ArrayList<>();

	/**
	 * Recherche de produits dans une langue
	 * 
	 * @param codeLang code langue
	 * @return
	 */
	public static PanacheQuery<Category> findCategoryHierarchy() {
		return Category.<Category>find("parentCategory is null");
	}
}
