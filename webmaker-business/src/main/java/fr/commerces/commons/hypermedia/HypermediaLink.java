package fr.commerces.commons.hypermedia;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.eclipse.microprofile.openapi.models.PathItem.HttpMethod;


@Target({METHOD, ElementType.ANNOTATION_TYPE,ElementType.TYPE}) 
@Retention(RUNTIME)
public @interface HypermediaLink {
	
	
	Class<?> resource()  default Object.class;
	
	String rel()  default "self";
	
	HttpMethod httpMethod()  default HttpMethod.GET;

	
	String methode() default "";
	

	

}