package org.intelligentsia.cqrs.eventstore;

/**
 * Responsible for (de-)serializing events.
 * 
 */
public interface EventSerializer<E> {

	/**
	 * Serialize specified event.
	 * 
	 * @param event
	 * @return serialized object.
	 */
	public Object serialize(final E event);

	/**
	 * Deserialize specified object.
	 * 
	 * @param serialized
	 * @return event <E>
	 */
	public E deserialize(final Object serialized);

}
