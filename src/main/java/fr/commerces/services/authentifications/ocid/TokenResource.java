package fr.commerces.services.authentifications.ocid;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.oidc.IdToken;
import io.quarkus.oidc.RefreshToken;

@Path("/tokens")
public class TokenResource {

	/**
	 * Point d'injection pour le jeton d'identification émis par le fournisseur
	 * OpenID Connect
	 */
	@Inject
	@IdToken
	JsonWebToken idToken;

	/**
	 * Point d'injection pour le jeton d'accès émis par le fournisseur OpenID
	 * Connect
	 */
	@Inject
	JsonWebToken accessToken;

	/**
	 * Point d'injection pour le jeton d'actualisation émis par le fournisseur
	 * OpenID Connect
	 */
	@Inject
	RefreshToken refreshToken;

	/**
	 * Renvoie les jetons disponibles pour l'application.
	 * <p>
	 * Ce point de terminaison existe uniquement à des fins de démonstration !!!
	 * </p>
	 *
	 * @return nos tokens disponibles pour l'application
	 */
	@GET
	public String getTokens() {
		StringBuilder response = new StringBuilder().append("<html>").append("<body>").append("<ul>");

		// User name
		final Object userName = this.idToken.getClaim("preferred_username");
		if (userName != null) {
			response.append("<li>username: ").append(userName.toString()).append("</li>");
		}
		// scope
		final Object scopes = this.accessToken.getClaim("scope");
		if (scopes != null) {
			response.append("<li>scopes: ").append(scopes.toString()).append("</li>");
		}

		response.append("<li>refresh_token: ").append(refreshToken.getToken() != null).append("</li>");

		return response.append("</ul>").append("</body>").append("</html>").toString();
	}
}