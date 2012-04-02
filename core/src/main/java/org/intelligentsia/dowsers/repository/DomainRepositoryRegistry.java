/**
 * 
 */
package org.intelligentsia.dowsers.repository;

import org.intelligentsia.dowsers.domain.DomainRepository;

/**
 * DomainRepositoryRegistry. 
 *
 */
public interface DomainRepositoryRegistry {

	DomainRepository getDomainRepository(Class<?> className);
}
