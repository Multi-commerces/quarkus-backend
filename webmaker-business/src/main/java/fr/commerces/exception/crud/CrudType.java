package fr.commerces.exception.crud;

enum CrudType {
	READ("lecture", "lire"), UPDATE("mise à jour", "modifier"), CREATE("création", "créer"),
	DELETE("suppression", "supprimer");

	private String summary;

	private String verbe;

	private CrudType(String summary, String verbe) {
		this.summary = summary;
		this.verbe = verbe;
	}

	public String getSummary() {
		return this.summary;
	}

	public String getVerbe() {
		return verbe;
	}

};
