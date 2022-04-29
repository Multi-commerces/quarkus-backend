package fr.webmaker.data.category;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.data.BaseResource;
import fr.webmaker.restfull.databind.LocalDateTimeDeserializer;
import fr.webmaker.restfull.databind.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// JSON:API
@Type(value = CategoryData.TYPE, path = "/categories/{id}")
// Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CategoryData extends BaseResource {
	public static final String TYPE = "category";
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@JsonProperty(access = Access.READ_ONLY)
//	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'", timezone = "GMT")
	@Schema(name = "created", description = "Date de création de la cétégorie")
	private LocalDateTime created;

	@JsonProperty(access = Access.READ_ONLY)
//	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'", timezone = "GMT")
	@Schema(description = "Date de mise à jour de la catégorie")
	private LocalDateTime updated;

	@Schema(description = "Position (utilisé lors de l'affichage dans un tableau)", example = "1")
	@JsonProperty(value = "position", defaultValue = "1", required = true)
	private Integer position;
	
	@Schema(description = "Condition pour que la catégorie apparaisse dans le menu de la boutique", example = "true")
	@JsonProperty(value = "displayed", defaultValue = "true", required = true)
	private Boolean displayed;

	@Schema(description = "URL : Image principale de la catégorie")
	@JsonProperty(value= "imageUrl", defaultValue = "http://placehold.it/240x240&text=Image 240x240")
	private String imageUrl = "https://via.placeholder.com/240x240&text=Image 240x240";


	@Schema(description = "URL : Une petite image pour la page de la catégorie parent.")	
	@JsonProperty(value= "thumbnailUrl", defaultValue = "http://placehold.it/80x80&text=Image 80x80")
	private String thumbnailUrl = "https://via.placeholder.com/80x80&text=Image 80x80";

	@Schema(description = "URL : Une vignette de la catégorie pour le menu sous la forme d'une petite image représentant la catégorie.")	
	@JsonProperty(value= "menuThumbnailUrl", defaultValue = "http://placehold.it/40x40&text=Image 40x40")
	private String menuThumbnailUrl = "https://via.placeholder.com/40x40&text=Image 40x40";

	public CategoryData(String id) {
		super(id);
	}
	
	@Transient
	public String getCreatedDate() {
		return created.format(formatter);
	}

}
