package fr.commerces.microservices.product.entity;

import java.time.LocalDateTime;

import javax.enterprise.context.RequestScoped;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@RequestScoped
public class CategoryListener {

	@PrePersist
	protected void onCreate(Category entity) {
		entity.updated = entity.created = LocalDateTime.now();
	}
	
	@PostPersist
	protected void postPersist(Category entity) {
		entity.updated = entity.created = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate(Category entity) {
		entity.updated = LocalDateTime.now();
	}
}