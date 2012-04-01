package org.intelligentsia.dowsers.eventstore;

import org.intelligentsia.cqrs.eventstore.impl.MemoryEventStore;

public class MemoryEventStoreTest extends AbstractEventStoreTest {

	@Override
	protected MemoryEventStore<String> createSubject() {
		return new MemoryEventStore<String>();
	}

}
