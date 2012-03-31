/**
 * 
 */
package org.intelligentsia.dowsers.event.processor;

import org.intelligentsia.dowsers.domain.Entity;
import org.intelligentsia.dowsers.event.DomainEvent;

/**
 * FakeDomainEventB.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class FakeDomainEventB extends DomainEvent {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -8220197363563967951L;

	/**
	 * Build a new instance of FakeDomainEventB.
	 * 
	 * @param entity
	 */
	public FakeDomainEventB(Entity entity) {
		super(entity);
	}

}
