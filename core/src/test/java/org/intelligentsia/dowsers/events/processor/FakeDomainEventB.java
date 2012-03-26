/**
 * 
 */
package org.intelligentsia.dowsers.events.processor;

import org.intelligentsia.dowsers.domain.Entity;
import org.intelligentsia.dowsers.events.DomainEvent;

/**
 * FakeDomainEventB.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class FakeDomainEventB extends DomainEvent {

	/**
	 * Build a new instance of FakeDomainEventB.
	 * 
	 * @param entity
	 */
	public FakeDomainEventB(Entity entity) {
		super(entity);
	}

}
