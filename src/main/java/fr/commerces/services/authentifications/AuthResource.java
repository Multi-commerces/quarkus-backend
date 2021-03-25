package fr.commerces.services.authentifications;

import javax.enterprise.context.RequestScoped;

/**
 * https://quarkus.io/guides/security-jwt
 * @author Julien ILARI
 *
 */
@RequestScoped
public class AuthResource implements AuthResourceApi {

	@Override
	public String authentification(String email, String password) {
		// TODO Auto-generated method stub
		return "testfdsdjgdsjg";
	}

	
	
	

	

}