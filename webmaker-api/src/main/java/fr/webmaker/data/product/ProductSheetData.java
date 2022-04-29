package fr.webmaker.data.product;

import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.annotations.Type;
import com.neovisionaries.i18n.LanguageCode;

import fr.webmaker.data.BaseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Représentation d'une fiche produit (CompositeData)
 * 
 * @author Julien ILARI
 *
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Type(value = "productSheet", path = "/translated-product-sheets")
public class ProductSheetData extends BaseResource {
	
	/**
	 * Required. The two-letter ISO 639-1 language code for the item.
	 */
	// jax-rs
	@DefaultValue("fr")
	// swagger
	@Schema(defaultValue = "fr", description = "Langue de tranduction du produit", example = "fr", implementation = LanguageCode.class)
	// validation
	@NotNull
	private LanguageCode languageCode = LanguageCode.fr;

	@Schema(description = "Référence unique du produit", example = "REF10000001", implementation = String.class)
	@JsonProperty(value = "reference")
	@NotNull
	private String reference;
	
	@Schema(description = "Nom du produit", example = "t-shirt blanc, taille M", implementation = String.class)
	@JsonProperty(value = "name")
	@NotNull
	private String name;
	
	@Schema(description = "Description courte", example = "(description courte) t-shirt blanc, taille M", implementation = String.class)
	@JsonProperty(value = "summary")
	@NotNull
	private String summary;
	
	@Schema(description = "Description détaillée", example = "(description détaillé) t-shirt blanc, taille M", implementation = String.class)
	@JsonProperty(value = "description")
	@NotNull
	private String description;
	
	
	@Schema(description = "Quantité disponible", example = "32", implementation = Long.class)
	@JsonProperty(value = "quantity")
	@NotNull
	private Long quantity;

	@Schema(description = "Prix unitaire (Hors Taxe)", example = "13", implementation = Double.class)
	@JsonProperty(value = "priceHT")
	@NotNull
	private Double priceHT;
	
	@DefaultValue("EUR")
	@Schema(defaultValue = "EUR", description = "Device ", example = "EUR", implementation = String.class)
	@JsonProperty(value = "priceTo")
	@NotNull
	private String priceTo = "EUR";

	@Schema(description = "Taxe appliquée", example = "5.5", implementation = Double.class)
	@JsonProperty(value = "taxeRule")
	@NotNull
	private Double taxeRule;

}