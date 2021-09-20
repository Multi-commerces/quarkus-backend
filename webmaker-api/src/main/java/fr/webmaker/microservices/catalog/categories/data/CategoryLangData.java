package fr.webmaker.microservices.catalog.categories.data;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.webmaker.commons.LocalDateTimeDeserializer;
import fr.webmaker.commons.LocalDateTimeSerializer;
import fr.webmaker.commons.data.SimpleData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryData implements SimpleData {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
	public LocalDateTime created;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
	public LocalDateTime updated;
	

	/**
	 * Nom de la catégorie
	 */
	public String name;

	/**
	 * Description
	 */
	public String description;

	/**
	 * Position (utilisé lors de l'affichage dans un tableau)
	 */
	public int position;

	/**
	 * Condition pour que la catégorie apparaisse dans le menu de la boutique
	 */
	public boolean displayed;

	/**
	 * URL : Image principale de la catégorie
	 */
	@ToString.Exclude
	public String imageUrl;

	/**
	 * URL : Une petite image pour la page de la catégorie parent.
	 */
	@ToString.Exclude
	public String thumbnailUrl;

	/**
	 * URL : Une vignette de la catégorie pour le menu sous la forme d'une petite image
	 * représentant la catégorie.
	 */
	@ToString.Exclude
	public String menuThumbnailUrl;
	
	@Transient
	public String getCreatedDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return created.format(formatter);
	}

}
