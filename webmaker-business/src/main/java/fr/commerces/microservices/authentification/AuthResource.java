package fr.commerces.microservices.authentification;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.representation.TokenIntrospectionResponse;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.keycloak.representations.idm.authorization.AuthorizationResponse;
import org.keycloak.representations.idm.authorization.Permission;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.keycloak.representations.idm.authorization.ScopeRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.commerces.microservices.authentification.data.AuthentificationData;
import io.quarkus.oidc.IdToken;
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.security.identity.SecurityIdentity;

/**
 * https://quarkus.io/guides/security-jwt
 * 
 * @author Julien ILARI
 *
 */
@RequestScoped
public class AuthResource implements AuthResourceApi {
	
	private static Logger logger = LoggerFactory.getLogger(AuthResource.class);
	
	@Inject
	AuthenticationContextProvider authContextProvider;

	/**
	 * Injection point for the ID Token issued by the OpenID Connect Provider
	 */
	@Inject
	@IdToken
	JsonWebToken idToken;

	@Inject
	JsonWebToken jwt;

	/**
	 * CurrentIdentityAssociation.current()
	 */
	@Inject
	SecurityIdentity identity;
	
	@Override
	public String accessToken() {
		logger.debug("Creating Auth0 Api Token");
		AccessTokenResponse accessTokenResponse = AuthzClient.create().obtainAccessToken();
		if (accessTokenResponse != null)
			return accessTokenResponse.getToken();

		return null;
	}
	
	@Override
	public void createResourcesAndScopes() throws IOException {
	    AuthzClient authzClient = AuthzClient.create();
	    Set<ScopeRepresentation> scopes = new HashSet<>();

	    scopes.add(new ScopeRepresentation("read"));
	    scopes.add(new ScopeRepresentation("write"));
	    scopes.add(new ScopeRepresentation("execute"));

	    List<ResourceRepresentation> resources = new ArrayList<>();

	    resources.add(new ResourceRepresentation("Ressource A", scopes));
	    resources.add(new ResourceRepresentation("Ressource B", scopes));
	    resources.add(new ResourceRepresentation("Ressource C", scopes));

	    resources.forEach(resource -> authzClient.protection().resource().create(resource));
	}
	
	@Override
	public String authorizationAccessToken(String accessToken) {
		// create a new instance based on the configuration defined in keycloak.json
		AuthzClient authzClient = AuthzClient.create();
		
		AuthorizationResponse response = authzClient.authorization(accessToken).authorize(
				new AuthorizationRequest());
		String rpt = response.getToken();
		
		LocalDateTime date = LocalDateTime.now().plusSeconds(response.getExpiresIn());
		return "{"
				+ "\"date\" : \"" + date + "\","
				+ "\"ExpiresIn\" : \"" + response.getExpiresIn() + "\","
				+ "\"Bearer\" : \"" + rpt 
				+ "\"}";
	}
	
	@Override
	public String authentification(String email, String password) {
		// create a new instance based on the configuration defined in keycloak.json
		AuthzClient authzClient = AuthzClient.create();

		// create an authorization request
		AuthorizationRequest request = new AuthorizationRequest();
		
		

		// send the entitlement request to the server in order to
		// obtain an RPT with all permissions granted to the user
		AuthorizationResponse response = authzClient.authorization(email, password).authorize(request);
		String rpt = response.getToken();

		System.out.println("You got an RPT: " + rpt);
		
		// introspect the token
		TokenIntrospectionResponse requestingPartyToken = authzClient.protection().introspectRequestingPartyToken(rpt);

		System.out.println("Token status is: " + requestingPartyToken.getActive());
		System.out.println("Permissions granted by the server: ");

		for (Permission granted : requestingPartyToken.getPermissions()) {
		    System.out.println(granted);
		}
		
		return "{"
				+ "\"Context\" : \"" + authContextProvider.context() + "\","
				+ "\"Bearer\" : \"" + rpt + "\"}";
	}
	
	@Override
	public AuthentificationData authentification(SecurityContext ctx) {
		if (ctx.getUserPrincipal() == null) {
			return new AuthentificationData("Anonymous", "ANONYMOUS", "anonymous.anonymous@webmaker.fr", null, null, "fr");
		} 
		
		if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
			throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
		} else {
			final AuthentificationData authData = AuthenticationContextProvider
					.toAuthenticationData((OidcJwtCallerPrincipal) identity.getPrincipal());
			
			logger.info(String.format("%s, isHttps: %s, authScheme: %s, hasJWT: %s", 
					ctx.getUserPrincipal().getName(), 
					ctx.isSecure(),
					ctx.getAuthenticationScheme(), 
					jwt.getClaimNames() != null));
			
			logger.info("Roles : ");
			identity.getRoles().forEach(logger::info);
			
			return authData;
		}
	}

}