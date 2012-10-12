package org.intelligentsia.dowsers.entity.factory;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.intelligentsia.dowsers.core.DowsersException;
import org.intelligentsia.dowsers.entity.Entity;
import org.intelligentsia.dowsers.entity.EntityDynamicSupport;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import org.intelligentsia.dowsers.entity.meta.MetaEntityContextRepository;
import org.intelligentsia.dowsers.entity.meta.MetaProperty;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;

/**
 * EntityFactoryDynamicCachedSupport extends {@link EntityFactoryDynamicSupport}
 * with attributes definition cache.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class EntityFactoryDynamicCachedSupport extends EntityFactoryDynamicSupport {
	/**
	 * {@link Cache} instance.
	 */
	private final LoadingCache<MetaEntityContext, Map<String, Object>> definitions;

	public EntityFactoryDynamicCachedSupport(final MetaEntityContextRepository metaEntityContextRepository) throws NullPointerException {
		super(metaEntityContextRepository);
		definitions = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build(new CacheLoader<MetaEntityContext, Map<String, Object>>() {
			@Override
			public Map<String, Object> load(final MetaEntityContext metaEntityContext) throws Exception {
				final Map<String, Object> properties = Maps.newHashMap();
				final Iterator<String> iterator = metaEntityContext.getMetaPropertyNames();
				while (iterator.hasNext()) {
					final MetaProperty metaProperty = metaEntityContext.getMetaProperty(iterator.next());
					// TODO clone it
					properties.put(metaProperty.getName(), metaProperty.getDefaultValue());
				}
				return properties;
			}
		});
	}

	@Override
	protected Entity instanciateEntity(final String identity, final MetaEntityContext metaEntityContext) {
		return new EntityDynamicSupport(identity, metaEntityContext, getAttributes(metaEntityContext));
	}

	private Map<String, Object> getAttributes(final MetaEntityContext metaEntityContext) {
		try {
			return Maps.newHashMap(definitions.get(metaEntityContext));
		} catch (final ExecutionException e) {
			throw new DowsersException(e);
		}
	}

}
