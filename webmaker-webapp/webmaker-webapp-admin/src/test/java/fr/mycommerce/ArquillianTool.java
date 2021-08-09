package fr.mycommerce;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

/**
 * Classe utilitaire pour les tests d'intégration arquillian.
 * https://openliberty.io/guides/arquillian-managed.html#getting-started
 * 
 * @author Julien ILARI 2020-09
 *
 */
public class ArquillianTool {

	/**
	 * Nom de archive web.
	 */
	private final static String WARNAME = System.getProperty("arquillian.war.name");

	/**
	 * Création de base de archive web pour le deploiement pour les tests
	 * d'intégration.
	 * 
	 * @return
	 */
	public static WebArchive createDeployment() {
		// Import Maven runtime dependencies
		File[] files = Maven.resolver().loadPomFromFile("pom.xml").importCompileAndRuntimeDependencies().resolve()
				.withTransitivity().asFile();

		WebArchive war = ShrinkWrap.create(WebArchive.class, WARNAME).addAsLibraries(files)
				.addPackages(true, "fr.webmaker")
				.addPackages(true, "fr.mycommerce")
				.addAsWebInfResource("faces-config.xml").addAsWebInfResource("web.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		// Show the deploy structure
		System.out.println(war.toString(true));

		return war;
	}

}
