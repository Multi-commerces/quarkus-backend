package fr.commerces.microservices.authentification.data;

import javax.validation.constraints.NotNull;


public class ProjectUserData {

	@NotNull
	private Long id;

	@NotNull
	private Boolean manager;

	@NotNull
	private String name;

	@NotNull
	private Boolean publicAccess;

	public ProjectUserData(@NotNull Long id, @NotNull Boolean manager, @NotNull String name,
			@NotNull Boolean publicAccess) {
		this.id = id;
		this.manager = manager;
		this.name = name;
		this.publicAccess = publicAccess;
	}

	public Long getId() {
		return id;
	}

	public Boolean isManager() {
		return manager;
	}

	public String getName() {
		return name;
	}

	public Boolean isPublicAccess() {
		return publicAccess;
	}
}