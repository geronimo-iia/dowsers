/**
 * 
 */
package org.intelligentsia.dowsers.domain;

/**
 * AggregateFactory acts as a factory for Aggregate.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface AggregateFactory {

	/**
	 * Create a new instance of aggregate for specified domain entity.
	 * 
	 * @param domainEntity
	 *            domain entity instance.
	 * @return an instance of Aggregate
	 * @throws NullPointerException
	 *             if domainEntity is null
	 */
	public <T extends DomainEntity> Aggregate create(T domainEntity) throws NullPointerException;
}
