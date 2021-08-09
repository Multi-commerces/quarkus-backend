package fr.commerces.services.external.laposte;

import java.io.Serializable;

import lombok.Data;

@Data
public class AddressResponse implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String code;
	private String adresse;

}
