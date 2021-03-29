package fr.commerces.services.products.manager;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.logged.Logged;
import fr.commerces.services._transverse.GenericResponse;
import fr.commerces.services.products.data.ProductData;
import fr.commerces.services.products.entity.ProductLang;
import fr.commerces.services.products.mapper.ProductMapper;
import io.quarkus.panache.common.Page;

@Logged
@ApplicationScoped

//@AlternativePriority(Interceptor.Priority.APPLICATION)
public class ProductManager {

	//private static final Logger logger = LoggerFactory.getLogger(ProductManager.class);

	private static final int SIZE = 10;

	@Inject
	ProductMapper mapper;
	
	Function<ProductLang, GenericResponse<ProductData, Long>> bind = pojo -> {
		GenericResponse<ProductData, Long> response = mapper.toResponse(pojo);
		return mapper.toResponse(pojo.getProduct(), response);
	};

	public final List<GenericResponse<ProductData, Long>> list(final LanguageCode language,
			final Optional<Integer> page, final Optional<Integer> size) {
		try (final Stream<ProductLang> streamEntity = ProductLang.findByLanguageCode(language)
				.page(Page.of(page.orElse(1) - 1, size.orElse(SIZE))).stream()) {
			return streamEntity.map(bind).collect(Collectors.toList());
		}
	}

	public final GenericResponse<ProductData, Long> findByIdProductAndLanguageCode(final Long productId,
			final LanguageCode language) {
		ProductLang pojo = ProductLang.findByIdProductAndLanguageCode(productId, language).orElseThrow(NotFoundException::new);
		return bind.apply(pojo);

	}

	@Transactional
	public final void update(final LanguageCode language, final Long id, final ProductData data) {
		ProductLang.findByIdProductAndLanguageCode(id, language).map(pojo -> mapper.dataIntoEntity(data, pojo))
				.orElseThrow(NotFoundException::new);
	}

	@Transactional
	public final Long create(final LanguageCode languageCode, final ProductData data) {
		final ProductLang productLang = mapper.toProductLang(data);
		productLang.setLanguage(languageCode);
		productLang.persistAndFlush();

		return productLang.getProduct().getId();
	}

	@Transactional
	public void delete(final LanguageCode languageCode, final Long productId) {
		boolean isOK = ProductLang.deleteByIdProductAndLanguageCode(productId, languageCode);
		if (!isOK) {
			throw new NotFoundException(String.format("Suppression impossible, produit introuvable avec idProduct=%s, languageCode=%s",
					String.valueOf(productId), languageCode.toString()));
		}
	}

}