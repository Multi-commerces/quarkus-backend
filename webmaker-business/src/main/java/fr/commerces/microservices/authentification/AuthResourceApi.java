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

package fr.commerces.microservices.authentification;

import java.io.IOException;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.keycloak.representations.idm.authorization.Permission;

import fr.commerces.microservices.authentification.data.AuthentificationData;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Ressource Authentification", description = "Ressource de gestion des produits")
public interface AuthResourceApi {

	
	@GET
	@Path("git")
	@PermitAll
	@Operation(operationId = "authentification", summary = "Authentification avec email mot de passe")
	String authentification(@QueryParam(value = "email") String email, @QueryParam(value = "password") String password);

	@GET
	@Path("roles-allowed-admin")
	@PermitAll
	AuthentificationData authentification(@Context SecurityContext ctx);

	@GET
	@Path("authorizationAccessToken")
	@PermitAll
	@Operation(operationId = "authorizationAccessToken", summary = "Authentification avec accessToken")
	String authorizationAccessToken(@QueryParam(value = "accessToken") String accessToken);

	
	@GET
	@Path("accessToken")
	@PermitAll
	@Operation(operationId = "accessToken", summary = "Création d'un accessToken")
	String accessToken();

	@GET
	@Path("createResourcesAndScopes")
	@PermitAll
	@APIResponses(value = { 
			@APIResponse(responseCode = "200", description = "[OK]"),
			@APIResponse(responseCode = "409", description = "[NOK] - Ressource déjà existante") 
	})
	void createResourcesAndScopes() throws IOException;

	@GET
	@Path("permissions")
	@Authenticated
	@Operation(operationId = "permissions", summary = "Liste de authorisations")
	Uni<List<Permission>> getPermission();

	

	

}
