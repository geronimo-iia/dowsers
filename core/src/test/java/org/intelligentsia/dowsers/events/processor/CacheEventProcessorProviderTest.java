/**
 * 
 */
package org.intelligentsia.dowsers.events.processor;

import org.intelligentsia.dowsers.events.processor.CacheEventProcessorProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.cache.CacheBuilder;

/**
 * CacheEventProcessorProviderTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class CacheEventProcessorProviderTest {

	private CacheEventProcessorProvider processorProvider;

	@Before
	public void initialize() {
		processorProvider = new CacheEventProcessorProvider(CacheBuilder.newBuilder());
	}

	@Test
	public void testRegister() {
		try {
			processorProvider.register(FakeEntity.class);
			Assert.fail("Expected RuntimeException");
		} catch (final RuntimeException e) {
		}
		processorProvider.register(FakeBeautifullEntity.class);
	}

	@Test
	public void testAutomaticRegistration() {
		try {
			processorProvider.get(FakeEntity.class);
			Assert.fail("Expected RuntimeException");
		} catch (final RuntimeException e) {
		}
		processorProvider.get(FakeBeautifullEntity.class);
	}

	@Test
	public void testMutipleCallFromRegistration() {
		processorProvider.register(FakeBeautifullEntity.class);
		processorProvider.get(FakeBeautifullEntity.class);
		processorProvider.get(FakeBeautifullEntity.class);
		processorProvider.get(FakeBeautifullEntity.class);
	}

	@Test
	public void testMutipleCall() {
		processorProvider.get(FakeBeautifullEntity.class);
		processorProvider.get(FakeBeautifullEntity.class);
		processorProvider.get(FakeBeautifullEntity.class);
	}
}
