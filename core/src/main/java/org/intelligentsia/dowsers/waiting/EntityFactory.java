/**
 * 
 */
package org.intelligentsia.dowsers.waiting;

import org.intelligentsia.dowsers.domain.Entity;

/**
 * EntityFactory.
 * 
 * @author jgt
 * 
 */
public interface EntityFactory {

	public <T extends Entity> T create();
}
