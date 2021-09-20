package fr.commerces.commons.hypermedia;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.eclipse.microprofile.openapi.models.PathItem.HttpMethod;

@Inherited
@Target({METHOD, ElementType.TYPE}) 
@Retention(RUNTIME)
public @interface HypermediaApi {
	
	
	Class<?> resource()  default Object.class;
	
	String rel()  default "self";
	
	HttpMethod httpMethod()  default HttpMethod.GET;
	
	String version()  default "1.0";
	
	String title()  default "Informations";
	
	HypermediaLink delete() default @HypermediaLink;
	HypermediaLink create() default @HypermediaLink;
	HypermediaLink update() default @HypermediaLink;
	
	HypermediaLink[] links() default {};
	

}