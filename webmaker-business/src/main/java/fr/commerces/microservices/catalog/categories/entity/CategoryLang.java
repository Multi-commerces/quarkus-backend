package fr.commerces.microservices.catalog.categories.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import com.neovisionaries.i18n.LanguageCode;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * Representation d'une catégorie, traduit dans une langue définit par le codeLangue
 * 
 * @author Julien ILARI
 *
 */
@Entity
@Getter
@Setter
@Table(name = "CATEGORY_LANG")
public class CategoryLang extends PanacheEntityBase {

	@EmbeddedId
	public CategoryLangPK identity;

	@Fetch(FetchMode.JOIN)
	@MapsId("categoryId")
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Category.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "CATEGORY_ID")
	public Category category;
	
	@Column(columnDefinition = "timestamp default current_timestamp", nullable = false, updatable = false, insertable = true)
	@CreationTimestamp
	public LocalDateTime created;

	@Column(columnDefinition = "timestamp default current_timestamp", nullable = false, updatable = true, insertable = true)
	@UpdateTimestamp
	public LocalDateTime updated;

	/*
	 * #######################################################################
	 * ############################ DETAIL PRODUIT ###########################
	 * #######################################################################
	 */

	/**
	 * Nom du produit
	 */
	@Column(name = "NAME", length = 128, nullable = false)
	public String name;

	/**
	 * Description détaillée
	 */
	@Column(name = "DESCRIPTION", length = 9999, nullable = false)
	public String description;

	
	/*
	 * ###################################################################
	 * ########################## REFERENCEMENT ##########################
	 * ###################################################################
	 */

	/**
	 * SEO metaTitle
	 */
	@Column(name = "META_TITLE", length = 256)
	public String metaTitle;

	/**
	 * SEO metaDescription
	 */
	@Column(name = "META_DESCRIPTION", length = 256)
	public String metaDescription;

	/**
	 * URL destinée à améliorer la convivialité et l'accessibilité d'un site Web
	 */
	@Column(name = "FRIENDLY_URL", length = 256)
	public String friendlyURL;

	/*
	 * ###################################################################
	 * ###################### METHODES PanacheQuery ######################
	 * ###################################################################
	 */

	/**
	 * 
	 * @return
	 */
	public static PanacheQuery<CategoryLang> findHierarchy() {
		return CategoryLang.<CategoryLang>find("category.parentCategory is null");
	}
	
	/**
	 * Recherche de produits dans une langue
	 * 
	 * @param codeLang code langue
	 * @return
	 */
	public static PanacheQuery<CategoryLang> findByLanguageCode(final LanguageCode languageCode) {
		return find("identity.languageCode = ?1", languageCode);
	}
	
	public static PanacheQuery<CategoryLang> findByCategoryId(final Long categoryId) {
		return find("identity.categoryId = ?1", categoryId);
	}

	/**
	 * Recherche un produit dans une langue
	 * 
	 * @param categoryId    identifiant du produit
	 * @param languageCode code langue
	 * @return
	 */
	public static Optional<CategoryLang> findByCategoryLangPK(final Long categoryId,
			final LanguageCode languageCode) {
		return findByIdOptional(new CategoryLangPK(categoryId, languageCode));
	}

	/**
	 * Supression d'un produit dans une langue
	 * 
	 * @param categoryId   identifiant de l'entity à supprimer.
	 * @param languageCode languageCode de l'entity à supprimer.
	 * @return false si l'entité n'a pas été supprimée (introuvable).
	 */
	public static boolean deleteByPK(final Long idProduct, final LanguageCode languageCode) {
		return deleteById(new CategoryLangPK(idProduct, languageCode));
	}

	/*
	 * ###################################################################
	 * ####################### METHODES Transient ########################
	 * ###################################################################
	 */
	
	@Transient
	public LanguageCode getLang()
	{
		if (identity != null) {
			return identity.getLanguageCode();
		} else {
			return LanguageCode.undefined;
		}
	}

	@Transient
	public Long getId() {
		if (identity == null) {
			return null;
		} else {
			return identity.getCategoryId();
		}
	}

	@Transient
	public void setCategoryLangPK(final LanguageCode language) {
		if (identity == null) {
			identity = new CategoryLangPK(null, language);
		} else {
			identity.setLanguageCode(language);
		}
	}
	
	@Transient
	public void setParentCategory(final Category parentCategory) {
		category.setParentCategory(parentCategory);
	}
	
	@Transient
	public List<Category> getChildrenCategory()
	{
		return category.getChildrenCategory();
	}

}
