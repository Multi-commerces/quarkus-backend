package fr.commerces.products.services;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.microservices.catalog.products.manager.ProductLangManager;
import fr.webmaker.microservices.catalog.products.data.ProductLangData;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ProductServiceTest {

	@Inject
	ProductLangManager manager;

	@Test
	public void testFindAllByLanguageCode() {
		int size = 2;

		final Map<Long, ProductLangData> values = manager.findAllByLanguageCode(LanguageCode.fr, Optional.of(1),
				Optional.of(size));

		assertThat("Le nombre d'item, doit correspondre au param.'size' fourni", values.size(), is(size));
	}

	@Test
	public void testFindAllByLanguageCode_OptionalEmpty() {
		final Map<Long, ProductLangData> values = manager.findAllByLanguageCode(LanguageCode.fr, Optional.empty(),
				Optional.empty());

		assertThat(values.size(), is(3));
	}

	@Test
	public void testFindAllByLanguageCode_OptionalNull() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			manager.findAllByLanguageCode(LanguageCode.fr, null, null);
		});
	}

	@Test
	public void testFindAllByLanguageCode_LangNull() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			manager.findAllByLanguageCode(null, Optional.empty(), Optional.empty());
		});
	}

	@Test
	public void testFindAllByLanguageCode_PageNull() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			manager.findAllByLanguageCode(LanguageCode.fr, null, Optional.empty());
		});
	}

	@Test
	public void testFindAllByLanguageCode_PageMinError() {
		// Doit être supérieure ou égal à 1
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			manager.findAllByLanguageCode(LanguageCode.fr, Optional.of(0), Optional.empty());
		});
	}

	@Test
	public void testFindAllByLanguageCode_SizeNull() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			manager.findAllByLanguageCode(LanguageCode.fr, Optional.empty(), null);
		});
	}

	@Test
	public void testFindAllByLanguageCode_SizeMinError() {
		// Doit être supérieure ou égal à 1
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			manager.findAllByLanguageCode(LanguageCode.fr, Optional.empty(), Optional.of(0));
		});
	}

}