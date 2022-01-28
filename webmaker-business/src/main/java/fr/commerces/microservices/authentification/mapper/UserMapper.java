package fr.commerces.microservices.authentification.mapper;

import javax.enterprise.context.ApplicationScoped;

import org.mapstruct.Mapper;

import fr.commerces.commons.resources.DefaultMappingConfig;
import fr.commerces.microservices.authentification.AuthenticationContext;
import fr.commerces.microservices.authentification.data.AuthentificationData;
import fr.commerces.microservices.authentification.data.UserData;
import fr.commerces.microservices.authentification.entity.User;

@ApplicationScoped
@Mapper(config = DefaultMappingConfig.class)
public abstract class UserMapper {

	public abstract User toEntity(UserData data);

	public abstract UserData toData(User entity);
	
	public abstract User toUser(AuthentificationData data);
	
	
	public abstract AuthenticationContext toAuthContext(User entity);
	

}