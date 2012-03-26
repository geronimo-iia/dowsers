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
public @interface TODO {

	/** Message décrivant la tâche à effectuer. */
	String value();

	/** Niveau de criticité de la tâche. */
	Level level() default Level.NORMAL;

	/** Enumération des différents niveaux de criticités. */
	public static enum Level {
		MINOR, NORMAL, IMPORTANT
	};

}