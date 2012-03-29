/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * IdentifierTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class IdentifierFactoryProviderTest {

	@Test
	public void shouldGenerateIdentifier() {
		Assert.assertNotNull(IdentifierFactoryProvider.generateNewIdentifier());
	}

	@Test
	public void shouldGenerateNotSameIdentifier() {
		final String id = IdentifierFactoryProvider.generateNewIdentifier();
		Assert.assertNotSame(id, IdentifierFactoryProvider.generateNewIdentifier());
	}
}
