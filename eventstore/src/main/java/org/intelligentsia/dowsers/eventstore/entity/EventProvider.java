/**
 * 
 */
package org.intelligentsia.dowsers.eventstore.entity;

/**
 *
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EventProvider {

	
	public void create(String identity, String className, long initialVersion);
	
	public void loadFromRepository(String identity);
	
	/**
	 * 
	 * @param identity
	 * @param expectedVersion
	 * @param events
	 * @param finalVersion 
	 */
	public void update(String identity, long expectedVersion, Object[] events, long finalVersion);
	
	public void remove(String identity);
	
	
	/** extended */
	// to manage conflict on update ?
	public long getCurrentVersion(String identity);
	
	// use full to take snapshot ?
	public void loadUpToVersionFromRepository(String identity, long version);
	
	// use full
	public void ressurect(String identity);
	
	
}
