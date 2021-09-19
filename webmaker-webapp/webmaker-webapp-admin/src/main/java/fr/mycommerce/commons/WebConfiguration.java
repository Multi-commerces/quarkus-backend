package fr.mycommerce.commons;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

@ApplicationScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class WebConfiguration {

	public class AppProduces {

		@Named("version")
		@Produces
		public String version() {
			return "1.0.0";
		}

	}
}