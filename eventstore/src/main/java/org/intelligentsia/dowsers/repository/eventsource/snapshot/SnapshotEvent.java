/**
 * 
 */
package org.intelligentsia.dowsers.repository.eventsource.snapshot;

import org.intelligentsia.dowsers.domain.Entity;
import org.intelligentsia.dowsers.event.DomainEvent;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class SnapshotEvent extends DomainEvent {

	/**
	 * serialVersionUID:long.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Build a new instance of <code>SnapshotEvent</code>
	 * 
	 * @param entity
	 * @throws NullPointerException
	 */
	public SnapshotEvent(Entity entity) throws NullPointerException {
		super(entity);
	}

}
