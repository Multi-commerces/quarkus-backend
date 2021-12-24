package fr.commerces.microservices.catalog.categories.data;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.jasminb.jsonapi.annotations.Type;

import fr.commerces.microservices.catalog.categories.CategoryResource;
import fr.webmaker.commons.data.BaseResource;
import fr.webmaker.commons.mapper.LocalDateTimeDeserializer;
import fr.webmaker.commons.mapper.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Type(value = "category", path = CategoryResource.PATH + "/{id}")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CategoryData extends BaseResource {

	public static final String ATT_POSITION = "position";

	@Null
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
	private LocalDateTime created;

	@Null
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
	private LocalDateTime updated;

	@Transient
	public String getCreatedDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return created.format(formatter);
	}

	/**
	 * Position (utilisé lors de l'affichage dans un tableau)
	 */
	private Integer position;

	/**
	 * Condition pour que la catégorie apparaisse dans le menu de la boutique
	 */
	@JsonProperty("displayed")
	private Boolean displayed;

	/**
	 * URL : Image principale de la catégorie
	 */
	@JsonProperty("imageUrl")
	@ToString.Exclude
	private String imageUrl;

	/**
	 * URL : Une petite image pour la page de la catégorie parent.
	 */
	@JsonProperty("thumbnailUrl")
	@ToString.Exclude
	private String thumbnailUrl;

	/**
	 * URL : Une vignette de la catégorie pour le menu sous la forme d'une petite
	 * image représentant la catégorie.
	 */
	@JsonProperty("menuThumbnailUrl")
	@ToString.Exclude
	private String menuThumbnailUrl;

}
