package fr.commerces.services.products.entity;

import java.util.Optional;

import javax.persistence.Cacheable;
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
@Cacheable(false)
@Table(name = "PRODUCT_LANG")
public class ProductLang extends PanacheEntityBase {

	@EmbeddedId
	public ProductLangPK identity;

	@MapsId("idProduct")
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Product.class)
	@JoinColumn(name = "PRODUCT_ID")
	public Product product;

	/*
	 * ###################################################################
	 * Produit dans une langue
	 * ###################################################################
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
	 * Description courte
	 */
	@Column(name = "DESCRIPTION_SHORT", length = 256, nullable = false)
	public String summary;

	/*
	 * ###################################################################
	 * REFERENCEMENT
	 * ###################################################################
	 */

	/**
	 * 0 of 70 characters used (recommended)
	 */
	@Column(name = "META_TITLE", length = 128)
	public String metaTitle;


	/**
	 * 0 of 160 characters used (recommended).
	 */
	@Column(name = "META_DESCRIPTION", length = 256)
	public String metaDescription;

	/**
	 * Récriture de url
	 */
	@Column(name = "FRIENDLY_URL", length = 128)
	public String friendlyURL;

	/* ################################ METHODES PanacheQuery ################################ */

	/**
	 * Recherche de produits dans une langue
	 * 
	 * @param codeLang code langue
	 * @return
	 */
	public static PanacheQuery<ProductLang> findByLanguageCode(final LanguageCode languageCode) {
		return find("identity.language", languageCode);
	}
	
	/**
	 * Recherche un produit dans une langue
	 * @param idProduct identifiant du produit
	 * @param languageCode code langue
	 * @return
	 */
	public static Optional<ProductLang> findByIdProductAndLanguageCode(final Long idProduct, final LanguageCode languageCode) {
		return Optional.ofNullable(find("identity.idProduct = ?1 and identity.language =?2", idProduct, languageCode)
				.firstResult());
	}

	/* ################################ METHODES Transient ################################ */

	@Transient
	public Long getId() {
		if (identity == null) {
			return null;
		} else {
			return identity.getIdProduct();
		}
	}
	
	@Transient
	public void setLanguage(LanguageCode language) {
		if (identity == null) {
			identity = new ProductLangPK(null, language);
		} else {
			identity.setLanguage(language);
		}
	}

}
