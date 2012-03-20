/**
 * 
 */
package com.iia.cqrs.storage.memento;

import java.util.UUID;

/**
 * MementoProvider.
 * 
 * @author jgt
 * 
 */
public interface MementoProvider {

	public <T extends Memento> T load(Class<T> type, UUID uuid);

	public <T extends Memento> UUID store(T memento);

}
