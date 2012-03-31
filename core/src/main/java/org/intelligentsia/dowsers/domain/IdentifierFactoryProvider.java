/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.UUID;

/**
 * This provider declare a method in order to generate new identifer using an
 * IdentifierFactory implementation.
 * 
 * <pre>IdentifierFactoryProvider.generateNewIdentifier();
 * 
 * <pre>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public final class IdentifierFactoryProvider {

	/**
	 * Inner IdentifierFactory instance.
	 */
	private final IdentifierFactory identifierFactory;

	/**
	 * Singleton Holder Pattern.
	 */
	private static class IdentifierFactoryHolder {
		/**
		 * IdentifierFactoryProvider instance.
		 */
		private static IdentifierFactoryProvider identifierFactoryProvider = new IdentifierFactoryProvider();
	}

	/**
	 * @return a new generated identifier.
	 */
	public static String generateNewIdentifier() {
		return IdentifierFactoryHolder.identifierFactoryProvider.identifierFactory.generateNewIdentifier();
	}

	/**
	 * Build a new instance of IdentifierFactoryProvider.
	 * 
	 * Lookup with classloader of current thread, if none is found, try with
	 * IdentifierFactory.class, and default with inner implementation using
	 * UUID.
	 */
	private IdentifierFactoryProvider() {
		super();
		IdentifierFactory factory = lookup(Thread.currentThread().getContextClassLoader());
		if (factory == null) {
			factory = lookup(IdentifierFactory.class.getClassLoader());
		}
		if (factory == null) {
			factory = new DefaultIdentifierFactory();
		}
		identifierFactory = factory;
	}

	/**
	 * Try to locate a IdentifierFactory implementation using specifie
	 * classloader.
	 * 
	 * @param classLoader
	 * @return an IdentifierFactory instance or null ir none was found.
	 */
	private IdentifierFactory lookup(final ClassLoader classLoader) {
		final ServiceLoader<IdentifierFactory> loader = ServiceLoader.load(IdentifierFactory.class, classLoader);
		final Iterator<IdentifierFactory> iterator = loader.iterator();
		IdentifierFactory identifierFactory = null;
		while (identifierFactory == null && iterator.hasNext()) {
			try {
				identifierFactory = iterator.next();
			} catch (final Throwable e) {
			}
		}
		return identifierFactory;
	}

	/**
	 * IdentifierFactory declare methode to generate new identifier.
	 * 
	 * <pre>
	 * IdentifierFactoryProvider.generateNewIdentifier();
	 * 
	 * <pre>
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	public interface IdentifierFactory {

		/**
		 * @return a new generated identifier.
		 */
		public abstract String generateNewIdentifier();

	}

	/**
	 * 
	 * DefaultIdentifierFactory extends abstract and use UUID class as
	 * identifier.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	private class DefaultIdentifierFactory implements IdentifierFactory {

		/**
		 * @see org.intelligentsia.dowsers.domain.IdentifierFactory#generateNewIdentifier()
		 */
		@Override
		public String generateNewIdentifier() {
			return UUID.randomUUID().toString();
		}

	}
}
