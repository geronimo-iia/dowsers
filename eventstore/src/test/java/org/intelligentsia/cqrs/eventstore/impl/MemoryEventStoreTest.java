package org.intelligentsia.cqrs.eventstore.impl;

import org.intelligentsia.cqrs.eventstore.AbstractEventStoreTest;

public class MemoryEventStoreTest extends AbstractEventStoreTest {

	@Override
	protected MemoryEventStore<String> createSubject() {
		return new MemoryEventStore<String>();
	}

}
