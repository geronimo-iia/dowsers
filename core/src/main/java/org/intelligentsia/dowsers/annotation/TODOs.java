/**
 * 
 */
package org.intelligentsia.dowsers.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Thanks to adiguba (@see
 * http://adiguba.developpez.com/tutoriels/java/tiger/annotations/)
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface TODOs {
	/** Le Tableau des différentes annotations TODO. */
	TODO[] value();
}