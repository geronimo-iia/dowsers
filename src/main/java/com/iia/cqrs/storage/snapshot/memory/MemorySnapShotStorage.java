/**
 * 
 */
package com.iia.cqrs.storage.snapshot.memory;

import java.util.UUID;

import com.google.common.collect.Multimap;
import com.iia.cqrs.Identifier;
import com.iia.cqrs.storage.snapshot.SnapShot;
import com.iia.cqrs.storage.snapshot.SnapShotStorage;

/**
 * MemorySnapShotStorage. 
 *
 * @author jgt
 *
 */
public class MemorySnapShotStorage implements SnapShotStorage {
	
	private Multimap<UUID, SnapShot> snapshots;
	
	/**
	 * Build a new instance of MemorySnapShotStorage.
	 */
	public MemorySnapShotStorage() {
		super();
	}
 

}
