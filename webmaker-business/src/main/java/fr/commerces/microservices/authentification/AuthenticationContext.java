package fr.commerces.microservices.authentification;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import fr.commerces.microservices.authentification.entity.Profile;
import lombok.Data;

@Data
public final class AuthenticationContext {

	private final long userId;

	private final long custumerId;

	@NotEmpty
	private final List<Profile> profiles;
	
	public AuthenticationContext()
	{
		this.custumerId = 0;
		this.userId = 0;
		this.profiles = new ArrayList<>();
	}

	AuthenticationContext(@NotNull final Long userId, @NotNull final Long custumerId,
			@NotEmpty final List<Profile> profiles) {
		this.userId = userId;
		this.custumerId = custumerId;
		this.profiles = new ArrayList<>(profiles);
	}

	public Boolean isSuperAdmin() {
		return profiles.contains(Profile.SUPER_ADMIN);
	}

	public Boolean isAdmin() {
		return profiles.contains(Profile.ADMIN);
	}

	public Boolean canAccess() {
		if (isAdmin() || isSuperAdmin()) {
			return true;
		}

		return true;
	}

}
