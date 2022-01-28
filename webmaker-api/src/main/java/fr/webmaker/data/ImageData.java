package fr.webmaker.data;

import com.github.jasminb.jsonapi.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Type(value = "image")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImageData extends BaseResource {

	/**
	 * Description courte pour accompagner l'image
	 */
	private String caption = "thumbnail";

	/**
	 * Url de l'image
	 */
	private String href = "http://placehold.it/160x160";

}
