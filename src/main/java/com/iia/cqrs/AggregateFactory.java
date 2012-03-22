/**
 * 
 */
package com.iia.cqrs;

/**
 * AggregateFactory. 
 *
 * @author jgt
 *
 */
public interface AggregateFactory {

	/**
	 * Create a new instance of aggregate.
	 * @return
	 */
	public Aggregate create();
}
