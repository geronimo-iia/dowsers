/**
 * 
 */
package com.iia.cqrs.storage.memento;

import java.util.UUID;

import com.iia.cqrs.storage.Memento;
import com.iia.cqrs.storage.Originator;

/**
 * MementoProvider.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface MementoProvider {

	public <T extends Memento> T load(Class<T> type, UUID uuid);

	public <T extends Memento> UUID store(Originator originator);

}
