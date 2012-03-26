/**
 * 
 */
package org.intelligentsia.dowsers.domain;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainEntityFactory {

	public <T extends DomainEntity> T create(Class<T> exceptedType);
}
