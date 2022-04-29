package fr.mycommerce.exception;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {

	/**
	 * Méthode d'acceptation qui lève une exception
	 * 
	 * @param t
	 * @throws E
	 */
	void accept(T t) throws E;
}