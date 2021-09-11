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

package fr.commerces.microservices.authentification.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

import java.util.ArrayList;

public enum Profile {
    SUPER_ADMIN("super_admin"),
    DEVELOPPEUR("developpeur_web"),
    ADMIN("admin"),
    USER("user"),
    GESTIONNAIRE("gestionnaire"),
    RESP_SAV("responsable_service_client"),
    RESP_CRM("responsable_CRM"),
    RESP_ACHAT("responsable_achat"),
    REST_LOGISTIQUE("responsable_logistique"),
    REST_PRODUCT("responsable_produit");

    String tokenValue;

    Profile(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public static Optional<Profile> getByName(String value) {
        return stream(Profile.values())
                .filter(profile -> value.equals(profile.tokenValue))
                .findFirst();
    }

    @Converter
    public static class ListConverter implements AttributeConverter<List<Profile>, String> {

        @Override
        public String convertToDatabaseColumn(List<Profile> list) {
        	if(list == null)
        	{
        		return null;
        	}
        	
            return list.stream().map(Enum::name).collect(Collectors.joining(","));
        }

        @Override
        public List<Profile> convertToEntityAttribute(String joined) {
        	if(joined == null)
        	{
        		return new ArrayList<Profile>();
        	}
        	
            return stream(joined.split(",")).map(Profile::valueOf).collect(Collectors.toList());
        }
    }
}
