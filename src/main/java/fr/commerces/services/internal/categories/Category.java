package fr.commerces.services.internal.categories;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import fr.commerces.services.internal.products.entity.ProductCategory;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Cacheable(false)
@Entity
@Getter
@Setter
@Table(name = "CATEGORY", uniqueConstraints = { @UniqueConstraint(columnNames = { "category_id" }) })
public class Category extends PanacheEntityBase {

	@Id
	@Column(name = "category_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	public List<ProductCategory> products = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "category_parent_id", nullable = true)
	public Category parentCategory;

	/**
	 * Liste des sous-catégories FetchType.LAZY
	 */
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "parentCategory", fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy("position")
	public Set<Category> childrenCategory = new HashSet<>();

	@NotNull
	public String description;

	@NotNull
	public String designation;

	public int position;

	/**
	 * catégorie activé/désactivé
	 */
	public boolean Displayed;

	/**
	 * Image de la catégorie (version non retourchée)
	 */
	public byte[] bigPicture;

	/**
	 * Image de la catégorie (version réduite)
	 */
	public byte[] smallPicture;


	@Temporal(TemporalType.TIMESTAMP)
	public Date created;

	@Temporal(TemporalType.TIMESTAMP)
	public Date updated;

}
