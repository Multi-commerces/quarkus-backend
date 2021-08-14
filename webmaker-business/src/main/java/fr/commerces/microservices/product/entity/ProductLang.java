package fr.commerces.microservices.product.entity;

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

import com.neovisionaries.i18n.LanguageCode;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * Representation du produit, traduit dans une langue définit par le codeLangue
 * 
 * @author Julien ILARI
 *
 */
@Entity
@Getter
@Setter
//@Cacheable(false)
@Table(name = "PRODUCT_LANG")
public class ProductLang extends PanacheEntityBase {

	@EmbeddedId
	public ProductLangPK identity;

	@MapsId("idProduct")
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Product.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "PRODUCT_ID")
	public Product product;

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

	/**
	 * Récapitulatif (résumé)
	 */
	@Column(name = "DESCRIPTION_SHORT", length = 256, nullable = false)
	public String summary;

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
	 * Recherche de produits dans une langue
	 * 
	 * @param codeLang code langue
	 * @return
	 */
	public static PanacheQuery<ProductLang> findByLanguageCode(final LanguageCode languageCode) {
		return find("identity.language = ?1", languageCode);
	}

	/**
	 * Recherche un produit dans une langue
	 * 
	 * @param idProduct    identifiant du produit
	 * @param languageCode code langue
	 * @return
	 */
	public static Optional<ProductLang> findByIdProductAndLanguageCode(final Long idProduct,
			final LanguageCode languageCode) {
		return findByIdOptional(new ProductLangPK(idProduct, languageCode));
	}

	/**
	 * Supression d'un produit dans une langue
	 * 
	 * @param idProduct    idProduct de l'entity à supprimer.
	 * @param languageCode languageCode de l'entity à supprimer.
	 * @return false si l'entité n'a pas été supprimée (introuvable).
	 */
	public static boolean deleteByProductLangPK(final Long idProduct, final LanguageCode languageCode) {
		return deleteById(new ProductLangPK(idProduct, languageCode));
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
			return identity.getLanguage();
		} else {
			return null;
		}
	}

	@Transient
	public Long getId() {
		if (identity == null) {
			return null;
		} else {
			return identity.getIdProduct();
		}
	}

	@Transient
	public void setProductLangPK(final LanguageCode language) {
		if (identity == null) {
			identity = new ProductLangPK(null, language);
		} else {
			identity.setLanguage(language);
		}
	}

}