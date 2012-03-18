/**
 * 
 */
package com.iia.cqrs;

import java.util.UUID;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Identifier {

	private UUID identity;
	private long version;

	/**
	 * Build a new instance of <code>Identifier</code>
	 * 
	 * @param identity
	 * @param version
	 */
	public Identifier(UUID identity, long version) {
		super();
		this.identity = identity;
		this.version = version;
	}

	public UUID getIdentity() {
		return this.identity;
	}

	public long getVersion() {
		return this.version;
	}

}
