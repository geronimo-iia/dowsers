/**
 * 
 */
package org.intelligentsia.dowsers.events.processor;

import org.intelligentsia.dowsers.domain.DomainEvent;
import org.intelligentsia.dowsers.domain.Entity;

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
