package fr.webmaker.exception.crud;

enum CrudExceptionType
{
	READ("lecture", "lire"),
	UPDATE("mise à jour", "modifier"),
	CREATE("création", "créer"),
	DELETE("suppression", "supprimer"),
	;

	private String summary;

	private String verb;

	private CrudExceptionType(String summary, String verb)
	{
		this.summary = summary;
		this.verb = verb;
	}

	public String getSummary()
	{
		return this.summary;
	}

	public String getVerb()
	{
		return verb;
	}

};
