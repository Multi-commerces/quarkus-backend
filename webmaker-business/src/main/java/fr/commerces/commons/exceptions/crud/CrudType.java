package fr.commerces.commons.exceptions.crud;

enum CrudType {
	READ("lecture", "lire"), UPDATE("mise à jour", "modifier"), CREATE("création", "créer"),
	DELETE("suppression", "supprimer");

	private String summary;

	private String verb;

	private CrudType(String summary, String verb) {
		this.summary = summary;
		this.verb = verb;
	}

	public String getSummary() {
		return this.summary;
	}

	public String getVerb() {
		return verb;
	}

};
