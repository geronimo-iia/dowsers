/**
 * 
 */
package com.iia.cqrs.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declare a method as an domain event handler. Method has an event signature
 * when:
 * <ul>
 * <li>declare annotation @DomainEventHandler</li>
 * <li>have a return type void</li>
 * <li>have one parameter</li>
 * <li>the parameter extends DomainEvent</li>
 * </ul>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DomainEventHandler {

}
