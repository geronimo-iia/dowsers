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
 * Immutable mark a type or field as immutable. This declaration could be use
 * for design check on large project.
 * 
 * @author JGT
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface Immutable {

}
