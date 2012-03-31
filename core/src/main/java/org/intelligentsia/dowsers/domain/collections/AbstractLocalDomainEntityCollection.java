/**
 * 
 */
package org.intelligentsia.dowsers.domain.collections;

import java.util.Collection;

import org.intelligentsia.dowsers.domain.LocalDomainEntity;
import org.intelligentsia.dowsers.util.Registry;

import com.google.common.base.Preconditions;

/**
 * AbstractLocalDomainEntityCollection.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class AbstractLocalDomainEntityCollection<T extends LocalDomainEntity> {

	private final Registry<LocalDomainEntity> registerEntity;

	/**
	 * Build a new instance of AbstractLocalDomainEntityCollection.
	 * 
	 * @param registerEntity
	 * @throws NullPointerException
	 *             if registerEntity is null
	 */
	public AbstractLocalDomainEntityCollection(Registry<LocalDomainEntity> registerEntity) throws NullPointerException {
		super();
		this.registerEntity = Preconditions.checkNotNull(registerEntity);
	}

	protected void register(T entity) {
		registerEntity.register(entity);
	}

	protected void register(Collection<? extends T> collection) {
		for (T entity : collection) {
			registerEntity.register(entity);
		}
	}
}
