package fr.commerces.microservices.catalog.images.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import fr.commerces.microservices.catalog.images.enums.ShopImageDeviceType;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



/**
 * Configuration des diverses tailles d'images.
 * @author Julien ILARI
 *
 */
@Entity
@Table(name = "CONFIG_IMAGE", 
	uniqueConstraints = { 
			@UniqueConstraint(columnNames = { "CONFIG_IMAGE_ID" }) 
	})
@Getter
@Setter
@ToString
public class ShopImageDimentionConfig extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CONFIG_IMAGE_ID")
	public Long id;

	/**
	 * Nom de la configuration
	 */
	@Column(name = "NAME", nullable = false, length = 64, updatable = false)
	public String name;
	
	
	/**
	 * DEFAULT, MOBILE, TABLET ?
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "DEVICE_TYPE", nullable = false)
	public ShopImageDeviceType deviceType;
	
	/**
	 * Produits
	 */
	public boolean produtcs;
	
	/**
	 * Catégorie
	 */
	public boolean categories;

	/**
	 * Marques
	 */
	public boolean brands;

	/**
	 * Fournisseurs
	 */
	public boolean suppliers;

	/**
	 * Magasins
	 */
	public boolean stores;
	
	/**
	 * Largeur de l'image
	 */
	@Column(name = "WIDTH", nullable = false)
	public Integer width;
	
	/**
	 * Hauteur de l'image
	 */
	@Column(name = "HEIGHT", nullable = false)
	public Integer height;

	

}
