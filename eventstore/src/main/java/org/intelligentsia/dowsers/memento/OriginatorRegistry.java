/**
 * 
 */
package org.intelligentsia.dowsers.memento;

import org.intelligentsia.dowsers.core.Registry;
import org.intelligentsia.dowsers.domain.DomainEntity;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface OriginatorRegistry extends Registry<Originator> {

	public <T extends DomainEntity> Originator find(Class<T> entityType);
}
