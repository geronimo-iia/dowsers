/**
 * 
 */
package com.iia.cqrs;

import java.util.UUID;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface DomainRepository {

	public <T> T findByIdentifier(UUID identifier);

	public <T> void add(T entity);

	public <T> void remove(Class<T> type, UUID identifier);

}
