package fr.commerces.web.shop;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class PrefAppMB {

	
	@PostConstruct
	public void postConstruct() {
		// TODO : Implémenter le chargement des préférences de l'utilisateur (si celui-ci est connecté)


	}

}
