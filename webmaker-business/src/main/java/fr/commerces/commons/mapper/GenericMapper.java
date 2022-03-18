package fr.commerces.commons.mapper;

import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ValidationException;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.openapitools.jackson.nullable.JsonNullable;

import fr.webmaker.data.BaseResource;
import fr.webmaker.exception.crud.NotFoundUpdateException;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * Mapper générique (prise en charge de JsonNullable) 
 * @author Julien ILARI
 *
 */
@Mapper(componentModel = "cdi", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class GenericMapper {

	@Inject
	private EntityManager manager;
	
	@ObjectFactory
	public <T extends PanacheEntityBase> T factory(BaseResource data, @TargetType Class<T> entityClass, @Context Object entityId) {
		
			if (entityId == null) {
				// Mode création
				try {
					return entityClass.getConstructor().newInstance();
				} catch (Exception e) {
					throw new ValidationException("Mapping impossible pour " + entityClass.getName());
				}
			}
			
			// Mode mise à jour
			return Optional.ofNullable(manager.find(entityClass, entityId))
					.orElseThrow(() -> new NotFoundUpdateException(entityId));
		
	}

	public <T> T unwrap(JsonNullable<T> nullable) {
		return nullable.orElse(null);
	}

	public <T> JsonNullable<T> wrap(T entity) {
		return JsonNullable.of(entity);
	}

	/**
	 * UPDATE (Opération de mise à jour)
	 */
	@BeforeMapping
	public <T> void beforeMapping(JsonNullable<T> nullable, @MappingTarget T target) {
		// ignore
	}

}