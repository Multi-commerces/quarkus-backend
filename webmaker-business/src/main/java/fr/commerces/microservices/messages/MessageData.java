package fr.commerces.microservices.messages;

import java.io.Serializable;

import lombok.Data;

@Data
public class MessageData implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Nom de expéditeur
	 */
	public String Name;

	/**
	 * Adresse mail de l'expéditeur
	 */
	public String email;

	/**
	 * Message
	 */
	public String message;

}
