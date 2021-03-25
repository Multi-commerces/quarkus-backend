package fr.commerces.services._transverse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * L’attribut error n’est pas forcement redondant avec le statut HTTP :
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
public class ErrorData {

	private String error;

	@JsonProperty("error_message")
	private String message;

	@JsonProperty("error_code")
	private String code;

	@JsonProperty("error_code_link")
	private String moreInfo;

}
