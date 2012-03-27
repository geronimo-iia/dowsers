package org.intelligentsia.cqrs.repository;

import org.intelligentsia.cqrs.domain.DomainEvent;
import org.intelligentsia.cqrs.domain.EntityRootNotFoundException;
import org.intelligentsia.cqrs.domain.FakeRootEntity;
import org.intelligentsia.cqrs.domain.VersionedIdentifier;
import org.intelligentsia.cqrs.eventstore.ConcurrencyException;
import org.intelligentsia.cqrs.eventstore.EventStore;
import org.intelligentsia.cqrs.eventstore.impl.MemoryEventStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * DefaultRepositoryTest test DefaultRepository implementation.
 * 
 * 
 * @author JGT
 * 
 */
public class DefaultRepositoryTest {

	private static final VersionedIdentifier TEST_ID = VersionedIdentifier.random();

	// private Bus bus;
	private FakeRootEntity fakeRootEntity;
	private EventStore<DomainEvent> eventStore;
	private DefaultRepository repository;

	@Before
	public void setUp() {
		eventStore = new MemoryEventStore<DomainEvent>();
		repository = new DefaultRepository(eventStore);

		fakeRootEntity = new FakeRootEntity(DefaultRepositoryTest.TEST_ID);
		fakeRootEntity.sayHello("Steeve");
		fakeRootEntity.sayHello("Jobs");
		repository.add(fakeRootEntity);
	}

	@Test
	public void shouldFailToAddAggregateWithoutAnyUnsavedChanges() {
		final FakeRootEntity a = new FakeRootEntity(VersionedIdentifier.random());
		try {
			repository.add(a);
			Assert.fail("IllegalArgumentException expected");
		} catch (final IllegalArgumentException expected) {
		}
	}

	@Test
	public void shouldFailOnNonExistingfakeRootEntity() {
		final VersionedIdentifier versionedIdentifier = VersionedIdentifier.random();
		try {
			repository.findByVersionedIdentifier(FakeRootEntity.class, versionedIdentifier);
			Assert.fail("EntityRootNotFoundException expected");
		} catch (final EntityRootNotFoundException expected) {
			Assert.assertEquals("type name must be equals", FakeRootEntity.class.getName(), expected.getType());
			Assert.assertEquals("identity name must be equals", versionedIdentifier.getIdentity(), expected.getIdentity());
		}
	}

	@Test
	public void shouldLoadfakeRootEntityFromEventStore() {
		repository.commitChanges();
		final FakeRootEntity result = repository.findByVersionedIdentifier(FakeRootEntity.class, DefaultRepositoryTest.TEST_ID);
		Assert.assertNotNull(result);
		Assert.assertEquals(fakeRootEntity.getName(), result.getName());
	}

	@Test
	public void shouldLoadAggregateOnlyOnce() {
		final FakeRootEntity a = repository.findByIdentity(FakeRootEntity.class, DefaultRepositoryTest.TEST_ID.getIdentity());
		Assert.assertSame(fakeRootEntity, a);
	}

	@Test
	public void shouldRejectDifferentAggregatesWithSameId() {
		final FakeRootEntity a = fakeRootEntity;
		final FakeRootEntity b = new FakeRootEntity(DefaultRepositoryTest.TEST_ID);
		b.sayHello("Bob");
		repository.add(a);
		try {
			repository.add(b);
			Assert.fail("ConcurrencyException expected");
		} catch (final ConcurrencyException expected) {
			Assert.assertEquals("identity name must be equals", b.getIdentity(), expected.getIdentity());
		}
	}

	@Test
	public void shouldCheckAggregateVersionOnLoadFromSession() {
		try {
			repository.findByVersionedIdentifier(FakeRootEntity.class, DefaultRepositoryTest.TEST_ID.withVersion(0));
			Assert.fail("ConcurrencyException expected");
		} catch (final ConcurrencyException expected) {
			Assert.assertEquals("identity name must be equals", DefaultRepositoryTest.TEST_ID.getIdentity(), expected.getIdentity());
		}
	}

	@Test
	public void shouldStoreAddedAggregate() {
		fakeRootEntity.sayHello("Cathy");
		repository.commitChanges();
	}

	@Test
	public void shouldStoreLoadedAggregateWithNextVersion() {
		repository.commitChanges();

		final FakeRootEntity result = repository.findByVersionedIdentifier(FakeRootEntity.class, DefaultRepositoryTest.TEST_ID);
		result.sayHello("Leloo");
		repository.commitChanges();

		final FakeRootEntity loaded = repository.findByVersionedIdentifier(FakeRootEntity.class, DefaultRepositoryTest.TEST_ID.nextVersion());
		Assert.assertNotNull(loaded);
		Assert.assertEquals("Leloo", loaded.getName());
	}

	@Test
	public void shouldPublishChangeEventsOnSave() {
		// fakeRootEntity.greetPerson("Erik");
		//
		// bus.publish(eq(fakeRootEntity.getUnsavedEvents()));
		// expectLastCall();
		// replay(bus);
		//
		// repository.afterHandleMessage();
		//
		// verify(bus);
	}

	@Test
	public void shouldReplyWithNotificationsOnSave() {
		// fakeRootEntity.greetPerson("Erik");
		// bus.reply(eq(fakeRootEntity.getNotifications()));
		// expectLastCall();
		// replay(bus);
		// repository.afterHandleMessage();
		// verify(bus);
	}

}
