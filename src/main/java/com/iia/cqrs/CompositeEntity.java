/**
 * 
 */
package com.iia.cqrs;

/**
 * CompositeEntity. 
 *
 * @author jgt
 *
 */
public class CompositeEntity extends Entity implements RegisterEntity{
	
	private Aggregate aggregate;
	
	/**
	 * Build a new instance of CompositeEntity.
	 * @param aggregateFactory
	 */
	public CompositeEntity(AggregateFactory aggregateFactory) {
		super(aggregateFactory);
	}
 
	/**
	 * Build a new instance of CompositeEntity.
	 * @param aggregateFactory
	 * @param identifier
	 * @throws NullPointerException
	 */
	public CompositeEntity(AggregateFactory aggregateFactory, Identifier identifier) throws NullPointerException {
		super(aggregateFactory, identifier);
	}



	/**
	 * @see com.iia.cqrs.RegisterEntity#register(com.iia.cqrs.Entity)
	 */
	@Override
	public void register(Entity entity) {
	}

}
