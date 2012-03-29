/**
 * 
 */
package org.intelligentsia.dowsers.eventprocessor;

import org.intelligentsia.dowsers.domain.Entity;
import org.intelligentsia.dowsers.events.DomainEvent;

/**
 * FakeDomainEventA.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class FakeDomainEventA extends DomainEvent {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -934076764921960654L;

	/**
	 * Build a new instance of FakeDomainEventA.
	 * 
	 * @param entity
	 */
	public FakeDomainEventA(Entity entity) {
		super(entity);
	}

}
