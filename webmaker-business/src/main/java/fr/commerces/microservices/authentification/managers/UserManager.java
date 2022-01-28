package fr.commerces.microservices.authentification.managers;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.commerces.microservices.authentification.AuthenticationContext;
import fr.commerces.microservices.authentification.data.AuthentificationData;
import fr.commerces.microservices.authentification.data.UserData;
import fr.commerces.microservices.authentification.entity.User;
import fr.commerces.microservices.authentification.mapper.UserMapper;
import fr.webmaker.annotation.ManagerInterceptor;

@ManagerInterceptor
@ApplicationScoped
public class UserManager {
	private static Logger logger = LoggerFactory.getLogger(UserManager.class);

	@Inject
	UserMapper mapper;

    public Optional<UserData> findResponseById(Long id, AuthenticationContext ctx) {
        return findById(id, ctx).map(mapper::toData);
    }

    public List<UserData> listAllResponses(AuthenticationContext ctx) {
        return streamAll(ctx, mapper::toData, Collectors.toList());
    }

	@Transactional
	public AuthenticationContext authenticate(AuthentificationData request) {
		final User authenticatedUser = findByEmail(request.getEmail()).orElseGet(() -> {
			logger.debug("Create a new user for email={} with {}", request.getEmail(), request);
			final User user = mapper.toUser(request);
			User.persist(user);
			return user;
		});

		return mapper.toAuthContext(authenticatedUser);
	}

    public Long countAll() {
        return User.count();
    }

    Optional<User> findById(Long id, AuthenticationContext ctx) {
        return User.<User>findByIdOptional(id);
    }

    private Optional<User> findByEmail(String email) {
        return User.<User>find("email", email)
                .firstResultOptional();
    }

    <R extends Collection<UserData>> R streamAll(
            AuthenticationContext ctx,
            Function<User, UserData> bind,
            Collector<UserData, ?, R> collector
    ) {
        try (final Stream<User> users = User.streamAll()) {
            return users
                    .map(bind)
                    .collect(collector);
        }
    }
}
