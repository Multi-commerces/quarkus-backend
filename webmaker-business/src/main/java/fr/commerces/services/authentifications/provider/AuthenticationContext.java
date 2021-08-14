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

import javax.validation.constraints.NotNull;

import fr.commerces.microservices.product.entity.Product;
import lombok.Getter;

@Getter
public final class AuthenticationContext {

	@NotNull
    private final String codeCLient;
	
    @NotNull
    private final Long userId;
   
    public AuthenticationContext(@NotNull String codeCLient, @NotNull Long userId) {
    	this.codeCLient = codeCLient;
        this.userId = userId;
    }

    @SuppressWarnings("unused")
	private Boolean isSuperAdmin() {
        return true;
    }

    @SuppressWarnings("unused")
    private Boolean isAdmin() {
        return true;
    }

    
    public String getCodeCLient() {
		return codeCLient;
	}

	public Long getUserId() {
        return userId;
    }

    @SuppressWarnings("unused")
    private Boolean canAccess() {
        return true;
    }
    
    protected final boolean canAccess(@NotNull Product productEntry) {
        return true;
    }
    
}
