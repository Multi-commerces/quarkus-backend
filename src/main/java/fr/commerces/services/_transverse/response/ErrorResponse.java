package fr.commerces.services._transverse.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.commerces.services._transverse.data.LinkData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <h1>Informations sur une erreur survenue lors de l'appel api</h1>
 * <p>L’attribut error n’est pas forcement redondant avec le statut HTTP</p>
 * <h3>exemple:</h3>
 * <ul>
 * <li>400 & error_code=invalid_product</li>
 * <li>400 & error_code=invalid_cartegory</li>
 * </ul>
 * 
 * @author Julien ILARI
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

	private String error;

	@JsonProperty("error_message")
	private String message;

	@JsonProperty("error_code")
	private String code;

	@JsonProperty("error_code_link")
	private String moreInfo;

	/**
	 * Le consommateur de l’API connaît la raison de l’erreur, et peut la corriger
	 * facilement en suivant les directives données dans les liens hypermédias.
	 */
	@JsonProperty("_links")
	protected List<LinkData> links = new ArrayList<>();

}
