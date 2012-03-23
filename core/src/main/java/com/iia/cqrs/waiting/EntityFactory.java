/**
 * 
 */
package com.iia.cqrs.waiting;

import com.iia.cqrs.domain.Entity;

/**
 * EntityFactory.
 * 
 * @author jgt
 * 
 */
public interface EntityFactory {

	public <T extends Entity> T create();
}
