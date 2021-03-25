/*
 * Copyright 2020 Lunatech S.A.S
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.commerces.services.authentifications.provider;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import fr.commerces.services.authentifications.models.Profile;
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.security.ForbiddenException;
import io.quarkus.security.UnauthorizedException;
import io.quarkus.security.identity.SecurityIdentity;

@ApplicationScoped
public class AuthenticationContextProvider {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationContextProvider.class);

	/**
	 * <h1>Obtenir le sujet</h1>
	 * obtenir le sujet en injectant simplement un SecurityIdentity
	 */
	@Inject
	SecurityIdentity identity;

	public AuthenticationContext context() {
		logger.info("[AuthenticationContext] =======================================================");
		if (identity.getPrincipal() instanceof OidcJwtCallerPrincipal) {
			final var authenticationRequest = getAuthenticationRequest((OidcJwtCallerPrincipal) identity.getPrincipal());
			try {
				final var authenticationContext = new AuthenticationContext("", 0L);
				// TODO : RAF Authentifier utilisateur

				MDC.put("authenticationContext.userId", authenticationContext.getUserId().toString());
				logger.info("Authentification success {}", authenticationRequest);
				
				return authenticationContext;
			} catch (Exception e) {
				logger.error("Authentification failed");
				throw new ForbiddenException(e.getMessage(), e);
			}

		} else {
			if(logger.isDebugEnabled())
			{
				logger.warn("Authentification failed");
				return null;
			}
			else
			{
				return null;
				//throw new UnauthorizedException();
			}
			
		}
	}

	private AuthenticationData getAuthenticationRequest(OidcJwtCallerPrincipal jwtCallerPrincipal) {
		final var jwtClaims = jwtCallerPrincipal.getClaims();
		final String email = jwtClaims.getClaimValueAsString("email");
		final String firstName = jwtClaims.getClaimValueAsString("given_name");
		final String lastName = jwtClaims.getClaimValueAsString("family_name");
		final String organization = jwtClaims.getClaimValueAsString("organization");
		final String picture = jwtClaims.getClaimValueAsString("picture");
		final List<Profile> profiles = getProfiles(jwtCallerPrincipal, jwtClaims);

		return new AuthenticationData(firstName, lastName, email, picture, profiles, organization);
	}

	private List<Profile> getProfiles(OidcJwtCallerPrincipal jwtCallerPrincipal, JwtClaims jwtClaims) {
		JsonObject realmAccess;
		logger.debug("jwtClaims {}", jwtClaims.toString());
		
		try {
			realmAccess = jwtClaims.getClaimValue("realm_access", JsonObject.class);
		} catch (MalformedClaimException e) {
			throw new UnauthorizedException();
		}

		final List<Profile> profiles = Optional.ofNullable(realmAccess)
				.map(jsonObject -> jsonObject.getJsonArray("roles").stream().map(Object::toString)
						.map(s -> s.replace("\"", "")).map(Profile::getByName).filter(Optional::isPresent)
						.map(Optional::get).collect(Collectors.toList()))
				.orElseGet(Collections::emptyList);

		if (profiles.isEmpty()) {
			throw new UnauthorizedException();
		}
		return profiles;
	}

}
