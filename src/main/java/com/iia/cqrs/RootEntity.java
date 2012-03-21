/**
 * 
 */
package com.iia.cqrs;

/**
 * RootEntity. 
 *
 * @author jgt
 *
 */
public class RootEntity extends Entity {

	/**
	 * Build a new instance of RootEntity.
	 */
	public RootEntity() {
		super();
	}

	/**
	 * Build a new instance of RootEntity.
	 * @param identifier
	 */
	public RootEntity(Identifier identifier) {
		super(identifier);
	}

}
