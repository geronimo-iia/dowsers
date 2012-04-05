/**
 * 
 */
package org.intelligentsia.dowsers.eventstore.memory;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import net.sf.persist.Persist;

import org.intelligentsia.dowsers.DowsersException;
import org.intelligentsia.dowsers.domain.ConcurrencyException;
import org.intelligentsia.dowsers.event.DomainEvent;
import org.intelligentsia.dowsers.eventstore.EmptyResultException;
import org.intelligentsia.dowsers.eventstore.EventStore;
import org.intelligentsia.dowsers.eventstore.StreamEverExistsException;
import org.intelligentsia.dowsers.eventstore.jdbc.EventsEntry;
import org.intelligentsia.dowsers.eventstore.jdbc.IsolatedWork;
import org.intelligentsia.dowsers.eventstore.jdbc.JdbcDelegate;
import org.intelligentsia.dowsers.eventstore.jdbc.SpringJdbcTemplateEventStore.EventStream;
import org.intelligentsia.dowsers.storage.Serializer;

import com.google.common.base.Preconditions;

/**
 * PersistEventStore implement anEventStore using underlaying Persist framework.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class PersistEventStore<EventType> implements EventStore<EventType> {

	private final JdbcDelegate jdbcDelegate;
	/**
	 * Cache name.
	 */
	private final String cacheName;
	private Serializer<List<EventType>> serializer;
	/**
	 * Build a new instance of PersistEventStore.
	 * 
	 * @param dataSource
	 * @param name
	 */
	public PersistEventStore(DataSource dataSource, String cacheName) throws NullPointerException {
		super();
		this.jdbcDelegate = new JdbcDelegate(Preconditions.checkNotNull(dataSource));
		this.cacheName = Preconditions.checkNotNull(cacheName);
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#create(java.lang.String,
	 *      long, java.lang.String, java.util.Collection)
	 */
	@Override
	public void create(String identity, long initialVersion, String name, Collection<EventType> initialEvents) throws StreamEverExistsException {
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#store(java.lang.String,
	 *      long, java.util.Collection)
	 */
	@Override
	public long store(String identity, long expectedVersion, Collection<EventType> events) throws EmptyResultException, ConcurrencyException, IllegalArgumentException {
		return 0;
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#loadFromLatestVersion(java.lang.String,
	 *      java.util.Collection)
	 */
	@Override
	public long loadFromLatestVersion(final String identity, final Collection<EventType> events) throws EmptyResultException {
		return jdbcDelegate.delegateWork(new IsolatedWork<Long>() {

			@Override
			public Long doWork(Connection connection) throws DowsersException {
				Persist persist = new Persist(cacheName, connection);
				EventStream eventStream = persist.readByPrimaryKey(EventStream.class, identity);
				if (eventStream == null) {
					throw new EmptyResultException(identity);
				}

				List<EventsEntry> eventsEntries = persist.readList(EventsEntry.class, "select * from eventsentry where id=? order by version ASC", identity);
				for(EventsEntry entry:eventsEntries) {
					try {
						events.addAll(serializer.deserialize(entry.getData()));
					} catch (Exception e) {
						throw new DowsersException(identity, e);
					}
				}
				return eventStream.getVersion();
			}
		}, false);
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#currentVersion(java.lang.String)
	 */
	@Override
	public long currentVersion(String identity) throws EmptyResultException {
		return 0;
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#loadUptoVersion(java.lang.String,
	 *      long, java.util.Collection)
	 */
	@Override
	public void loadUptoVersion(String identity, long version, Collection<EventType> events) throws EmptyResultException {
	}

	/**
	 * @see org.intelligentsia.dowsers.eventstore.EventStore#loadFromVersion(java.lang.String,
	 *      long, java.util.Collection)
	 */
	@Override
	public void loadFromVersion(String identity, long version, Collection<EventType> events) throws EmptyResultException {
	}

}
