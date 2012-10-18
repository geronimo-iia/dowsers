package com.intelligentsia.dowsers.entity.serializer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.google.common.base.Objects;
import com.intelligentsia.dowsers.entity.EntityProxy;

/**
 * EntityNameResolvers.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum EntityNameResolvers {
	;

	public static EntityNameResolver newEntityNameResolver() {
		return new ProxyEntityNameResolver(new ClassNameEntityNameResolver());
	}

	/**
	 * EntityNameResolver interface is a contract for resolving the entity name
	 * of a given entity instance.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	public interface EntityNameResolver {

		/**
		 * Resolve entity name for specified instance.
		 * 
		 * @param entity
		 *            instance
		 * @return entity name
		 */
		public String resolveEntityName(Object entity);
	}

	/**
	 * EntityNameResolverSupport implements {@link EntityNameResolver} .
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	public static abstract class EntityNameResolverSupport implements EntityNameResolver {

		private EntityNameResolver next;

		/**
		 * Build a new instance of EntityNameResolverSupport.java.
		 */
		public EntityNameResolverSupport() {
			this(null);
		}

		/**
		 * Build a new instance of EntityNameResolverSupport.java.
		 */
		public EntityNameResolverSupport(final EntityNameResolver next) {
			this.next = next;
		}

		/**
		 * @return next {@link EntityNameResolver} instance or null if no
		 *         successor.
		 */
		public EntityNameResolver next() {
			return next;
		}

		public boolean hasNext() {
			return next != null;
		}
	}

	/**
	 * ProxyEntityNameResolver implements {@link EntityNameResolver} and try to
	 * resolve name with information from {@link EntityDynamicProxyHandler}.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 * 
	 */
	public static class ProxyEntityNameResolver extends EntityNameResolverSupport {
		/**
		 * Build a new instance of ProxyEntityNameResolver.java.
		 */
		public ProxyEntityNameResolver() {
			super();
		}

		public ProxyEntityNameResolver(EntityNameResolver next) {
			super(next);
		}

		@Override
		public String resolveEntityName(final Object entity) {
			if (Proxy.isProxyClass(entity.getClass())) {
				final InvocationHandler handler = Proxy.getInvocationHandler(entity);
				if (EntityProxy.class.isAssignableFrom(handler.getClass())) {
					final EntityProxy myHandler = (EntityProxy) handler;
					return myHandler.getInterfaceName().getName();
				}
			}
			if (hasNext()) {
				return next().resolveEntityName(entity);
			}
			return null;
		}

		@Override
		public final int hashCode() {
			return Objects.hashCode(getClass());
		}

		/**
		 * Entities compare by identity, not by attributes.
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public final boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			return true;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return Objects.toStringHelper(this).toString();
		}

	}

	/**
	 * ClassNameEntityNameResolver implements {@link EntityNameResolver}.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	public static class ClassNameEntityNameResolver extends EntityNameResolverSupport {

		/**
		 * Build a new instance of ClassNameEntityNameResolver.java.
		 */
		public ClassNameEntityNameResolver() {
			super();
		}

		public ClassNameEntityNameResolver(EntityNameResolver next) {
			super(next);
		}

		@Override
		public String resolveEntityName(final Object entity) {
			if (entity != null) {
				return entity.getClass().getName();
			}
			if (hasNext()) {
				return next().resolveEntityName(entity);
			}
			return null;
		}

		@Override
		public final int hashCode() {
			return Objects.hashCode(getClass());
		}

		/**
		 * Entities compare by identity, not by attributes.
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public final boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			return true;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return Objects.toStringHelper(this).toString();
		}
	}
}
