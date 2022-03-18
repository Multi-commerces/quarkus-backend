package fr.commerces.commons.resources;

public final class ConstApi {
	
	public static final String MEDIA_JSON_API = "application/vnd.api+json";

	public static final String CODE200GET = "[OK] - Opération de recherche effectuée avec succès";
	public static final String CODE404GET = "[KO] - Ressource introuvable";
	
	public static final String CODE201POST = "[OK] - Opération de création effectuée avec succès";
	
	public static final String CODE20XPATCH = "[OK] - Opération de modification partielle effectuée avec succès";
	
	public static final String CODE201PUT = "[OK] - Opération de mise à jour effectuée avec succès";
	public static final String CODE204PUT = "[OK] - Opération de mise à jour effectuée avec succès";

	public static final String CODE204DELETE = "[OK] - Opération de suppression effectuée avec succès";
	public static final String CODE404DELETE = "[NOK] - Opération de suppression impossible car l'occurence à supprimer est introuvable.";


	public static final String ERROR_MSG_0001 = "Tentative de remplacement complet d'une relation à plusieurs non authorisé";
	
}
