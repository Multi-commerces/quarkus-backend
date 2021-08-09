package fr.commerces.services.authentifications.provider;

import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.security.credential.CertificateCredential;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;

/**
 * <h1>Personnalisation de l'identité de sécurité</h1>
 * <p>
 * === Si plus d'une coutume SecurityIdentityAugmentorest enregistrée, elles
 * seront considérées comme des candidats égaux et invoquées dans un ordre
 * aléatoire. Vous pouvez appliquer la commande en implémentant une
 * SecurityIdentityAugmentor#priorityméthode par défaut . Les augmentateurs avec
 * des priorités plus élevées seront appelés en premier. ===
 * </p>
 * <p>
 * Authentification TLS mutuelle
 * https://quarkus.io/guides/security-built-in-authentication#mutual-tls
 * </p>
 * 
 * @see <a href=
 *      'https://quarkus.io/guides/security-customization#security-identity-customization'>documentation</a>
 * @see <a href='https://quarkus.io/guides/security'>Documentation "securité"</a>
 * @author Julien ILARI
 *
 */
@ApplicationScoped
public class RolesAugmentor implements SecurityIdentityAugmentor {

	@Override
	public Uni<SecurityIdentity> augment(SecurityIdentity identity, AuthenticationRequestContext context) {
		return Uni.createFrom().item(build(identity));
	}

	private Supplier<SecurityIdentity> build(SecurityIdentity identity) {
		// create a new builder and copy principal, attributes, credentials and roles
		// from the original identity
		QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder(identity);

		CertificateCredential certificate = identity.getCredential(CertificateCredential.class);
		if (certificate != null) {
			builder.addRoles(extractRoles(certificate.getCertificate()));
		}
		return builder::build;
	}

	private Set<String> extractRoles(X509Certificate certificate) {
		String name = certificate.getSubjectX500Principal().getName();

		switch (name) {
		case "CN=client":
			return Collections.singleton("user");
		case "CN=guest-client":
			return Collections.singleton("guest");
		default:
			return Collections.emptySet();
		}
	}
}