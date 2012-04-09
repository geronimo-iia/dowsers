package org.intelligentsia.dowsers.eventstore;

import org.intelligentsia.cqrs.eventstore.EventSerializer;
import org.intelligentsia.cqrs.eventstore.EventStore;
import org.intelligentsia.dowsers.eventstore.support.jdbc.SpringJdbcTemplateEventStore;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-application-context.xml")
public class SpringJdbcTemplateEventStoreTest extends AbstractEventStoreTest {

	@Autowired
	private SimpleJdbcTemplate simpleJdbcTemplate;

	private final EventSerializer<String> eventSerializer = new EventSerializer<String>() {

		@Override
		public Object serialize(final String event) {
			return event;
		}

		@Override
		public String deserialize(final Object serialized) {
			return (String) serialized;
		}
	};

	@Override
	protected EventStore<String> createSubject() {
		Assert.assertNotNull("jdbcTemplate not injected", simpleJdbcTemplate);
		final SpringJdbcTemplateEventStore<String> result = new SpringJdbcTemplateEventStore<String>(simpleJdbcTemplate, eventSerializer);
		result.initialize();
		return result;
	}

}
