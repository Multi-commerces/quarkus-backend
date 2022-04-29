package fr.webmaker.data.product;

import java.time.LocalDateTime;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.jasminb.jsonapi.annotations.Relationship;
import com.github.jasminb.jsonapi.annotations.Type;
import com.neovisionaries.i18n.LanguageCode;

import fr.webmaker.data.BaseResource;
import fr.webmaker.restfull.databind.LocalDateTimeDeserializer;
import fr.webmaker.restfull.databind.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Représentation d'un produit (traduit dans une langue)
 * 
 * @author Julien ILARI
 *
 */
@Type(value="productLang", path = "/products/{id}")
@Data
@EqualsAndHashCode(callSuper = true)
public final class ProductLangCompositeData extends BaseResource {

	/**
	 * Required. The two-letter ISO 639-1 language code for the item.
	 */
	private LanguageCode languageCode = LanguageCode.fr;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	@Schema(name = "created", description = "Date de création de la cétégorie")
	private LocalDateTime created;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "GMT")
	@Schema(description = "Date de mise à jour de la catégorie")
	private LocalDateTime updated;
	
	
	@Relationship(relatedPath = "../../", value = "product", serialise  = false)
	private ProductData product;
	
	
	@JsonProperty("translation")
	private ProductLangData lang;
	
//	@Relationship(relatedPath = "/seo", path = "/relationships/seo", value = "seo")
	private ProductSeoData seo;
	

	

}