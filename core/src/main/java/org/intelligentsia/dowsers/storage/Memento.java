/**
 * 
 */
package org.intelligentsia.dowsers.storage;

import java.io.Serializable;

/**
 * Memento the lock box that is written and read by the Originator, and
 * shepherded by the Caretaker. Caretaker - the object that knows why and when
 * the Originator needs to save and restore itself.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Memento extends Serializable {

}
