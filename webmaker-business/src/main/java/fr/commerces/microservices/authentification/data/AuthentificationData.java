package fr.commerces.microservices.authentification.data;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import fr.commerces.microservices.authentification.entity.Profile;
import fr.commerces.microservices.authentification.entity.User;
import lombok.Data;

@Data
public final class AuthentificationData {

    @NotBlank
    private final String firstName;

    @NotBlank
    private final String lastName;
    
    @NotBlank
    private final String local;

    @NotBlank
    @Email
    private final String email;

    @NotNull
    private final String picture;

    @NotEmpty
    private final List<Profile> profiles;

    public AuthentificationData(
            @NotBlank String firstName,
            @NotBlank String lastName,
            @NotBlank @Email String email,
            @NotNull String picture,
            @NotEmpty List<Profile> profiles,
            @NotNull String locale
    ) {
        this.local = locale;
		this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.picture = picture;
        this.profiles = profiles;
    }

  	/*
	 * Panache optimizes update process, but to avoid unnecessary logging, use this
	 */
	public boolean isEquals(@NotNull User user) {
        return !(Objects.equals(user.firstName, getFirstName())
                && Objects.equals(user.lastName, getLastName())
                && Objects.equals(user.email, getEmail())
                && Objects.equals(user.local, getLocal())
                && Objects.equals(user.picture, getPicture())
                && user.profiles.size() == getProfiles().size()
                && user.profiles.containsAll(getProfiles())
        );
    }

	
}
