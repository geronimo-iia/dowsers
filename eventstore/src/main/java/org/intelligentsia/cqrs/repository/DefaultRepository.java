package org.intelligentsia.cqrs.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.intelligentsia.cqrs.domain.DomainEvent;
import org.intelligentsia.cqrs.domain.EntityRootNotFoundException;
import org.intelligentsia.cqrs.domain.Repository;
import org.intelligentsia.cqrs.domain.RootEntity;
import org.intelligentsia.cqrs.domain.VersionedIdentifier;
import org.intelligentsia.cqrs.eventstore.ConcurrencyException;
import org.intelligentsia.cqrs.eventstore.EmptyResultException;
import org.intelligentsia.cqrs.eventstore.EventStore;
import org.intelligentsia.dowsers.eventstore.StreamEverExistsException;

import com.google.common.base.Preconditions;

///, BusSynchronization
/**
 * 
 * DefaultRepository implements Repository interface by delegating call on inner
 * Session bounded as a ThreadLocal variable.
 * 
 * 
 */
public class DefaultRepository implements Repository {

	/**
	 * event store instance.
	 */
	private final EventStore<DomainEvent> eventStore;
	// private final Bus bus;

	/**
	 * thread local variable session.
	 */
	private final ThreadLocal<Session> sessions = new ThreadLocal<Session>() {
		@Override
		protected Session initialValue() {
			return new Session();
		}
	};

	/**
	 * Build a new instance of DefaultRepository.
	 * 
	 * @param eventStore
	 *            event store to use.
	 * @throws NullPointerException
	 *             if {@link EventStore} is null
	 */
	public DefaultRepository(final EventStore<DomainEvent> eventStore) throws NullPointerException {
		this.eventStore = Preconditions.checkNotNull(eventStore);
		// this.bus = bus;
	}

	@Override
	public <T extends RootEntity> T findByIdentity(final Class<T> type, final UUID identity) throws EntityRootNotFoundException, NullPointerException {
		return sessions.get().findByIdentity(Preconditions.checkNotNull(type), Preconditions.checkNotNull(identity));
	}

	@Override
	public <T extends RootEntity> T findByVersionedIdentifier(final Class<T> type, final VersionedIdentifier versionedIdentifier) throws EntityRootNotFoundException, ConcurrencyException, NullPointerException {
		return sessions.get().findByVersionedIdentifier(Preconditions.checkNotNull(type), Preconditions.checkNotNull(versionedIdentifier));
	}

	@Override
	public <T extends RootEntity> void add(final T rootEntity) throws ConcurrencyException, IllegalArgumentException, NullPointerException {
		sessions.get().add(Preconditions.checkNotNull(rootEntity));
	}

	@Override
	public void commitChanges() throws StreamEverExistsException, EmptyResultException, ConcurrencyException {
		sessions.get().commitChanges();
	}

	// public void afterHandleMessage() {
	// sessions.get().afterHandleMessage();
	// }
	//
	// public void beforeHandleMessage() {
	// sessions.get().beforeHandleMessage();
	// }

	/**
	 * Session.
	 * 
	 * Store loaded entity in the next version.
	 */
	private class Session {

		private final Map<UUID, RootEntity> rootEntitiesByIdentity = new HashMap<UUID, RootEntity>();

		/**
		 * Build a new instance of DefaultRepository.Session.
		 */
		public Session() {
			super();
		}

		/**
		 * @see Repository#findByIdentity(Class, UUID)
		 */
		public <T extends RootEntity> T findByIdentity(final Class<T> expectedType, final UUID identity) throws EntityRootNotFoundException {
			// ever in session ?
			final T result = expectedType.cast(rootEntitiesByIdentity.get(identity));
			if (result != null) {
				return result;
			}
			// load it
			try {
				final RootEntitySink<T> sink = new RootEntitySink<T>(expectedType, identity);
				eventStore.loadEventsFromLatestStreamVersion(identity, sink);
				return sink.getRootEntity();
			} catch (final EmptyResultException e) {
				throw new EntityRootNotFoundException(expectedType.getName(), identity, e);
			}
		}

		/**
		 * @see Repository#findByVersionedIdentifier(Class, VersionedIdentifier)
		 */
		public <T extends RootEntity> T findByVersionedIdentifier(final Class<T> expectedType, final VersionedIdentifier versionedIdentifier) throws ConcurrencyException, EntityRootNotFoundException {
			// ever in session ?
			T result = expectedType.cast(rootEntitiesByIdentity.get(versionedIdentifier.getIdentity()));
			if (result != null) {
				// check version identifier
				if (!versionedIdentifier.nextVersion().equals(result.getVersionedIdentifier())) {
					// uncommited version: n + 1
					// actual loaded version: n
					throw new ConcurrencyException(versionedIdentifier.getIdentity(), "actual: " + (result.getVersionedIdentifier().getVersion() - 1) + ", expected: " + versionedIdentifier.getVersion());
				}
				return result;
			}
			// load it
			try {
				final RootEntitySink<T> sink = new RootEntitySink<T>(expectedType, versionedIdentifier.getIdentity());
				eventStore.loadEventsFromExpectedStreamVersion(versionedIdentifier.getIdentity(), versionedIdentifier.getVersion(), sink);
				result = sink.getRootEntity();
				addToSession(result);
				return result;
			} catch (final EmptyResultException ex) {
				throw new EntityRootNotFoundException(expectedType.getName(), versionedIdentifier.getIdentity(), ex);
			}
		}

		/**
		 * @see Repository#add(RootEntity)
		 */
		public <T extends RootEntity> void add(final T rootEntity) throws ConcurrencyException, IllegalArgumentException {
			if (rootEntity.getUncommittedChanges().isEmpty()) {
				throw new IllegalArgumentException("root entity has no unsaved changes");
			}
			addToSession(rootEntity);
		}

		/**
		 * Add specified root entity instance to current session.
		 * 
		 * @param rootEntity
		 *            instance to add
		 * @throws ConcurrencyException
		 *             if different version of the specified entity is ever in
		 *             this session
		 */
		private <T extends RootEntity> void addToSession(final T rootEntity) throws ConcurrencyException {
			final RootEntity previous = rootEntitiesByIdentity.put(rootEntity.getVersionedIdentifier().getIdentity(), rootEntity);
			if (previous != null && previous != rootEntity) {
				throw new ConcurrencyException(rootEntity.getVersionedIdentifier().getIdentity(), "multiple instances with same id " + rootEntity.getVersionedIdentifier().getIdentity());
			}
		}

		/**
		 * Commit all session change.
		 */
		public void commitChanges() throws StreamEverExistsException, EmptyResultException, ConcurrencyException {
			for (final RootEntity rootEntity : rootEntitiesByIdentity.values()) {
				final List<? extends DomainEvent> uncommittedChanges = rootEntity.getUncommittedChanges();
				if (!uncommittedChanges.isEmpty()) {
					saveEntityRoot(rootEntity);
				}
			}
			// should be done just before transaction commit...
			rootEntitiesByIdentity.clear();
		}

		/**
		 * Save change of specified entity root.
		 * 
		 * @param rootEntity
		 */
		private void saveEntityRoot(final RootEntity rootEntity) throws StreamEverExistsException, EmptyResultException, ConcurrencyException {
			if (rootEntity.getVersionedIdentifier().isForInitialVersion()) {
				eventStore.createEventStream(rootEntity.getVersionedIdentifier().getIdentity(), new RootEntitySource(rootEntity));
			} else {
				eventStore.storeEventsIntoStream(rootEntity.getVersionedIdentifier().getIdentity(), rootEntity.getVersionedIdentifier().getVersion() - 1, new RootEntitySource(rootEntity));
			}
			rootEntity.markChangesCommitted();
			rootEntity.incrementVersion();
		}

		// public void afterHandleMessage() {
		// // Collection<Object> notifications = new ArrayList<Object>();
		// for (RootEntity rootEntity : rootEntitiesByIdentity.values()) {
		// // notifications.addAll(aggregate.getNotifications());
		// // aggregate.clearNotifications();
		//
		// List<? extends DomainEvent> uncommittedChanges =
		// rootEntity.getUncommittedChanges();
		// if (!uncommittedChanges.isEmpty()) {
		// // bus.publish(unsavedEvents);
		// saveAggregate(rootEntity);
		// }
		// }
		// // bus.reply(notifications);
		// //
		// // should be done just before transaction commit...
		// rootEntitiesByIdentity.clear();
		// }

	}
}
