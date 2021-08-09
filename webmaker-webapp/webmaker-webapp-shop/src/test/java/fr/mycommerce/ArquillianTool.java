package fr.mycommerce;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * Classe utilitaire pour les tests d'intégration arquillian.
 * @author Julien ILARI 2020-09
 *
 */
public class ArquillianTool
{
	/**
	 * Nom de archive web.
	 */
	private final static String WARNAME = "arquillian-managed.war";

	/**
	 * Création de base de archive web pour le deploiement pour les tests d'intégration.
	 * @return
	 */
	public static WebArchive createDeployment()
	{
		return ShrinkWrap
			.create(WebArchive.class, WARNAME)
			.addAsManifestResource("beans.xml", "beans.xml");
	}

}
