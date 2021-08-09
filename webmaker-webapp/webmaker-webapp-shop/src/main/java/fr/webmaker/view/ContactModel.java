package fr.webmaker.view;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Dependent
@Data
public class ContactModel implements Serializable
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Nom
	 */
	@NotNull
	private String name;
	
	/**
	 * Numéro de téléphone
	 */
	@NotNull
	private String phone;
	
	/**
	 * Adresse mail
	 */
	@NotNull
	private String email;
	
	/**
	 * Message (format html)
	 */
	@NotNull
	private String message;

	
}
