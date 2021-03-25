package fr.commerces;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
@OpenAPIDefinition(info = @Info(title = "Multi-commerces API", description = "Cette API permet les opérations CRUD et l'interaction avec multi-commerces", version = "1.0", contact = @Contact(name = "multi-commerces GitHub", url = "https://github.com/lunatech-labs/lunatech-timekeeper"), license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html")), servers = {
		@Server(url = "http://localhost:8081", description = "DEV Server") }, security = {
				@SecurityRequirement(name = "dev_multicommercesOAuth2", scopes = { "profile" }) })
// Quarkus Issue pending with redirect and OAuth2 here https://github.com/quarkusio/quarkus/issues/4766
// To fix this issue I extracted the swagger oauth2-redirect.html file and saved it in the timekeeper folder
@SecurityScheme(securitySchemeName = "dev_multicommercesOAuth2", type = SecuritySchemeType.OAUTH2, description = "authentication for OAuth2 access", flows = @OAuthFlows(implicit = @OAuthFlow(authorizationUrl = "http://localhost:8080/auth/realms/multi-commerces/protocol/openid-connect/auth")))
public class ApplicationRest extends Application {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationRest.class);
	
	void onStart(@Observes @Priority(value = 1) StartupEvent ev) {
		logger.info("Serveur START [OK] =====================================================");
		logger.info("http://localhost:8081/q/openapi/ui/");
	}

}
