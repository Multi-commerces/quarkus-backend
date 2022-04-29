package fr.webmaker.restfull.model;

import java.util.ArrayList;
import java.util.List;

import fr.webmaker.exception.mapper.Result;
import fr.webmaker.restfull.hateos.LinkData;
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
	
	private Result validationError;

	private String error_message;

	private String error_code;

	private String error_code_link;
	
	

	public ErrorResponse(String error, String error_message, String error_code, String error_code_link,
			List<LinkData> _links) {
		super();
		this.error = error;
		this.error_message = error_message;
		this.error_code = error_code;
		this.error_code_link = error_code_link;
		this._links = _links;
	}



	/**
	 * Le consommateur de l’API connaît la raison de l’erreur, et peut la corriger
	 * facilement en suivant les directives données dans les liens hypermédias.
	 */
	protected List<LinkData> _links = new ArrayList<>();

}
