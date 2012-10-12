package org.intelligentsia.dowsers.entity.factory;

import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.dowsers.entity.EntityDynamicSupport;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContextNotFoundException;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContextRepository;

/**
 * EntityFactoryDynamicSupport implements {@link EntityFactory} for
 * {@link EntityDynamicSupport}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class EntityFactoryDynamicSupport extends AbstractEntityFactorySupport {

	/**
	 * Build a new instance of EntityFactoryDynamicSupport.java ( @see
	 * {@link AbstractEntityFactorySupport} )
	 * 
	 * @param metaEntityContextRepository
	 * @throws NullPointerException
	 */
	public EntityFactoryDynamicSupport(final MetaEntityContextRepository metaEntityContextRepository) throws NullPointerException {
		super(metaEntityContextRepository);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> T newInstance(final Class<?> className, final String identity) throws NullPointerException, MetaEntityContextNotFoundException {
		final MetaEntityContext metaEntityContext = getMetaEntityContextRepository().find(className);
		return (T) instanciateEntity(identity, metaEntityContext);
	}

	/**
	 * Instantiate a new {@link EntityDynamicSupport}.
	 * 
	 * @param identity
	 * @param metaEntityContext
	 */
	protected Entity instanciateEntity(final String identity, final MetaEntityContext metaEntityContext) {
		return new EntityDynamicSupport(identity, metaEntityContext);
	}

}
