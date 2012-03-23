/**
 * 
 */
package com.iia.cqrs.events;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

/**
 * EventProviderList.
 * 
 * 
 */
public class EventProviderList implements RegisterEventProvider {

	private final RegisterEventProvider aggregate;
	private final Map<UUID, EventProvider> providers;

	/**
	 * Build a new instance of EventProviderList.
	 * 
	 * @param aggregate
	 */
	public EventProviderList(final RegisterEventProvider aggregate) {
		super();
		this.aggregate = aggregate;
		providers = Maps.newHashMap();
	}

	/**
	 * @see com.iia.cqrs.events.RegisterEventProvider#register(com.iia.cqrs.events.EventProvider)
	 */
	@Override
	public void register(EventProvider eventProvider) {
		aggregate.register(eventProvider);
		providers.put(eventProvider.getIdentifier().getIdentity(), eventProvider);
	}

}
