package fr.webmaker.commons.data;

import lombok.Data;

@Data
public class ImageData {

	/**
	 * Description courte pour accompagner l'image
	 */
	private String caption;

	/**
	 * Url de l'image
	 */
	private String url;

	public ImageData() {
		super();
	}

}
