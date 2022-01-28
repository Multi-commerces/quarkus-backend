package fr.webmaker.exception;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;


public class UtilityException {

	public static BiConsumer<String, Object[]> requireNonNulls = (String objectName, Object... value) -> {

		Objects.requireNonNull(value, String.format("param %s ne doit pas être null !", objectName));
//		final Class<?> classe = value.getClass();

		Arrays.stream(value).forEach(o -> {
//			log.debug("param {} (type {}) = {}", objectName, classe, value);
		});

	};

	public static BiConsumer<String, Object> requireNonNull = (String objectName, Object value) -> {

		Objects.requireNonNull(value, String.format("param %s ne doit pas être null !", objectName));
//		final Class<?> classe = value.getClass();

//		log.debug("param {} (type {}) = {}", objectName, classe, value);

	};
}
