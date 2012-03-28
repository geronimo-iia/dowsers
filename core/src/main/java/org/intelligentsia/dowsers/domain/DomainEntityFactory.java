/**
 * 
 */
package org.intelligentsia.dowsers.domain;

/**
 * DomainEntityFactory declare method to create DomainEntity instance.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainEntityFactory {

	/**
	 * @param exceptedType
	 *            excepted type instance.
	 * @return an instance of type T.
	 * @throws NullPointerException
	 *             if exceptedType is null
	 */
	public <T extends DomainEntity> T create(Class<T> exceptedType) throws NullPointerException;
}
