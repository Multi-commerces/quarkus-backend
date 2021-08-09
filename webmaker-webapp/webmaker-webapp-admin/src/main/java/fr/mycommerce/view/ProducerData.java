package fr.mycommerce.view;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 * Classe de production des données de référence
 * @author Julien ILARI
 *
 */
@ApplicationScoped
public class ProducerData {

	public class CatalogueProduces {
		
		@Named("version")
		@Produces
		public String version()
		{
			return "1.0.0";
		}
		
	}



}