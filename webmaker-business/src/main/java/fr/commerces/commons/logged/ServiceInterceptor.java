package fr.commerces.commons.logged;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Priority;
import javax.interceptor.AroundConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.jboss.logging.MDC;

import fr.commerces.commons.exceptions.crud.NotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * Logged Interceptor
 * <p>
 * capture des exceptions afin d'enrichir le log pour la résolution d'anomalie.
 * </p>
 * 
 * @author Julien ILARI
 *
 */
@Slf4j
@ManagerInterceptor
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class ServiceInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void postConstructInterception(InvocationContext invocationContext) throws Exception {
		log.debug(invocationContext.getClass() + " is ready for manipulation");
		try {
			invocationContext.proceed();
		} catch (Exception e) {
			throw e;
		} 
	}

	@PreDestroy
	public void preDestroyInterception(InvocationContext invocationContext) throws Exception {
		log.debug("Stopped manipulating of class " + invocationContext.getMethod().getDeclaringClass());
		invocationContext.proceed();
	}

	@AroundConstruct
	public void aroundConstructLoggedInterceptor(InvocationContext invocationContext) throws Exception {
		log.debug(invocationContext.getConstructor().getDeclaringClass() + " will be manipulated");
		invocationContext.proceed();
	}

	@AroundInvoke
	public Object aroundInvokeLoggedInterceptor(InvocationContext invocationContext) throws Exception {
		MDC.clear();
		MDC.put("method", invocationContext.getMethod().getDeclaringClass().getSimpleName());
		MDC.put("class", invocationContext.getTarget().getClass().getSimpleName());
		log.info("Entering method: " + invocationContext.getMethod().getName() + " in class "
				+ invocationContext.getMethod().getDeclaringClass().getSimpleName());
		try {
			return invocationContext.proceed();
		} catch (PersistenceException e) {
			log.error("L'action n'a pas pu aboutir en raison d'une violation de contrainte");
			throw e;
		} catch (NotFoundException e) {
			log.warn(e.getMessage().concat(" [ID]={{}}"), e.getIdentifierDebug());
			throw e;
		} catch (ConstraintViolationException e) {
			log.error("L'action n'a pas pu aboutir en raison d'une violation de contrainte");
			log.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			MDC.clear();
		}
	}

}