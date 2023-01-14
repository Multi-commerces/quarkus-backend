package fr.webmaker.exception;

import java.util.Objects;
import java.util.function.BiConsumer;

public final class ExceptionUtility
{

	/**
	 * Constructeur privé (classe utilitaire)
	 */
	private ExceptionUtility()
	{
		// ignore
	}

	public static final BiConsumer<String, Object[]> requireNonNulls = (String objectName, Object... value) -> Objects
		.requireNonNull(value, String.format("param %s ne doit pas être null !", objectName));

	public static final BiConsumer<String, Object> requireNonNull = (String objectName, Object value) -> Objects
		.requireNonNull(value, String.format("param %s ne doit pas être null !", objectName));

}
