package fr.commerces.commons.exceptions.crud;

/**
 * Permet de forger une exception dans le cas d'une demande crud en
 * échec pour cause objet introuvable.
 * 
 * @author julien ILARI
 *
 */
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String identifierDebug;

	public NotFoundException(Object identifier) {
		this(CrudType.READ, identifier);
	}

	public NotFoundException(CrudType crudType, Object identifier) {
		super(String.format("Opération de " + crudType.getSummary() + " impossible car l'occurence à "
				+ crudType.getVerb() + " est introuvable."));

		identifierDebug = String.format("%s",
				identifier != null ? identifier.toString() : "identifier non fourni (null)");
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getIdentifierDebug() {
		return identifierDebug;
	}

}
