/**
 * 
 */
package org.intelligentsia.dowsers.domain;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * LocalDomainEntityTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class LocalDomainEntityTest {

	@Test
	public void checkNextVersionMustThrowIllegalStateException() {
		DummyLocalEntity domainEntity = new DummyLocalEntity();
		try {
			domainEntity.nextVersion();
			fail("Expected IllegalStateException");
		} catch (IllegalStateException e) {
		}
	}

	@Test
	public void checkSetVersionMustThrowIllegalStateException() {
		DummyLocalEntity domainEntity = new DummyLocalEntity();
		try {
			domainEntity.setVersion(123l);
			fail("Expected IllegalStateException");
		} catch (IllegalStateException e) {
		}
	}
	
	@Test
	public void checkVersionMustBeInitial() {
		DummyLocalEntity domainEntity = new DummyLocalEntity();
		assertTrue(domainEntity.getIdentifier().isForInitialVersion());
	}

}
