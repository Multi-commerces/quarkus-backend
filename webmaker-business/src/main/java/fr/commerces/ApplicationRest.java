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
import org.eclipse.microprofile.openapi.annotations.security.OAuthScope;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.StartupEvent;


@ApplicationScoped
@OpenAPIDefinition(
	info = @Info(title = "Multi-commerces API", 
			termsOfService = "https://github.com/lunatech-labs/lunatech-timekeeper",
			description = "Cette API permet d'interagire avec votre boutique en ligne", 
			version = "1.0.0", 
			contact = @Contact(name = "web maker GitHub", 
				url = "https://github.com/lunatech-labs/lunatech-timekeeper",
				email = "julien.ilari@.com"), 
			license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html")		
	), 
	servers = { 
			@Server(url = "http://localhost:8081", description = "DEV Server"), 
			@Server(url = "https://api.web-maker.fr", description = "PROD Server") 
	}, 
	security = { 
			@SecurityRequirement(name = "dev_OAuth2", scopes = { "profile" }),
			@SecurityRequirement(name = "prod_OAuth2", scopes = { "profile" })
	}
)

// https://github.com/quarkusio/quarkus/issues/4766
@SecurityScheme(
	securitySchemeName = "dev_OAuth2", 
	type = SecuritySchemeType.OAUTH2, 
	description = "authentication pour un access OAuth2", 
	flows = @OAuthFlows(
			implicit = @OAuthFlow(scopes = { @OAuthScope },
			authorizationUrl = "http://localhost:8080/auth/realms/webmaker/protocol/openid-connect/auth")
	)
)
@SecurityScheme(
    securitySchemeName = "prod_OAuth2",
    type = SecuritySchemeType.OAUTH2,
    description = "authentication pour un access OAuth2",
    flows = @OAuthFlows(
            implicit = @OAuthFlow(scopes = { @OAuthScope },
            	authorizationUrl = "http://auth.web-maker.fr/auth/realms/webmaker/protocol/openid-connect/auth")
    )
)
public class ApplicationRest extends Application {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationRest.class);
	
	void onStart(@Observes @Priority(value = 1) StartupEvent ev) {
		logger.info("Serveur START [OK] =====================================================");
		logger.info("http://localhost:8081/swagger");
	}

}
