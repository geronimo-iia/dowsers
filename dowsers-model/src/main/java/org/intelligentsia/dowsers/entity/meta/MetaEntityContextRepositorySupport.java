package org.intelligentsia.dowsers.entity.meta;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.intelligentsia.dowsers.core.reflection.ClassInformation;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * MetaEntityContextRepositorySupport.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class MetaEntityContextRepositorySupport implements MetaEntityContextRepository {

	/**
	 * {@link LoadingCache} instance.
	 */
	private LoadingCache<String, ClassInformation> classes;

	/**
	 * Build a new instance of MetaEntityContextRepositorySupport with default
	 * cache:
	 * <ul>
	 * <li>Maximum size : 1000 elements</li>
	 * <li>expire after access : 1 days</li>
	 * </ul>
	 */
	public MetaEntityContextRepositorySupport() {
		this(CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(1, TimeUnit.DAYS));
	}

	/**
	 * Build a new instance of <code>MetaEntityContextRepositorySupport</code>
	 * 
	 * @param cacheBuilder
	 */
	public MetaEntityContextRepositorySupport(final CacheBuilder<Object, Object> cacheBuilder) {
		super();
		/**
		 * Add specific cache loader.
		 */
		classes = cacheBuilder.build(new CacheLoader<String, ClassInformation>() {
			@Override
			public ClassInformation load(final String key) throws Exception {
				return ClassInformation.parse(key);
			}
		});
	}

	@Override
	public MetaEntityContext find(final String className) throws NullPointerException, MetaEntityContextNotFoundException, IllegalStateException {
		try {
			return find(classes.get(Preconditions.checkNotNull(className)).getType());
		} catch (final ExecutionException e) {
			throw new IllegalStateException(e);
		}
	}

}
