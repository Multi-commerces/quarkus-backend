package fr.webmaker.common.response;

import java.util.ArrayList;
import java.util.List;

import fr.webmaker.common.LinkData;
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

	private String error_message;

	private String error_code;

	private String error_code_link;

	/**
	 * Le consommateur de l’API connaît la raison de l’erreur, et peut la corriger
	 * facilement en suivant les directives données dans les liens hypermédias.
	 */
	protected List<LinkData> _links = new ArrayList<>();

}
