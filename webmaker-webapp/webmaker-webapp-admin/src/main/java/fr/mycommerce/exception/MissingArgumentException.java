package fr.mycommerce.exception;

public class MissingArgumentException extends IllegalArgumentException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 582076541838399222L;
	/**
	 * clé i18n pour traduction et présentation d'un message fonctionnel à
	 * l'utilisateur.
	 */
	private String i18n;

	/**
	 * Nom de argument en erreur
	 */
	private String argument;

	public MissingArgumentException(final String argument) {
		super("argument manquant");
		this.i18n = "error.arg.empty." + argument;

	}

	public String getI18n() {
		return i18n;
	}

	public String getArgument() {
		return argument;
	}

}