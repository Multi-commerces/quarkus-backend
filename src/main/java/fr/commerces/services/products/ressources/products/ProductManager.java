package fr.commerces.services.products.ressources.products;

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
import io.quarkus.panache.common.Page;

@Logged
@ApplicationScoped

//@AlternativePriority(Interceptor.Priority.APPLICATION)
public class ProductManager implements ProductService {

	//private static final Logger logger = LoggerFactory.getLogger(ProductManager.class);

	private static final int SIZE = 10;

	@Inject
	ProductMapper mapper;
	
	Function<ProductLang, GenericResponse<ProductData, Long>> bind = pojo -> {
		GenericResponse<ProductData, Long> response = mapper.toResponse(pojo);
		return mapper.toResponse(pojo.getProduct(), response);
	};

	@Transactional
	@Override
	public final List<GenericResponse<ProductData, Long>> list(final LanguageCode language,
			final Optional<Integer> page, final Optional<Integer> size) {
		try (final Stream<ProductLang> streamEntity = ProductLang.findByLanguageCode(language)
				.page(Page.of(page.orElse(1) - 1, size.orElse(SIZE))).stream()) {
			return streamEntity.map(bind).collect(Collectors.toList());
		}
	}

	@Override
	public final GenericResponse<ProductData, Long> findByIdProductAndLanguageCode(final Long id,
			final LanguageCode language) {
		ProductLang pojo = ProductLang.findByIdProductAndLanguageCode(id, language).orElseThrow(NotFoundException::new);
		return bind.apply(pojo);

	}

	@Transactional
	@Override
	public final void update(final LanguageCode language, final Long id, final ProductData data) {
		ProductLang.findByIdProductAndLanguageCode(id, language).map(pojo -> mapper.dataIntoEntity(data, pojo))
				.orElseThrow(NotFoundException::new);
	}

	@Transactional
	@Override
	public final Long create(final LanguageCode languageCode, final ProductData data) {
		final ProductLang productLang = mapper.toProductLang(data);
		productLang.setLanguage(languageCode);
		productLang.persistAndFlush();

		return productLang.getProduct().getId();
	}

}