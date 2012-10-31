/**
 * 
 */
package com.intelligentsia.dowsers.entity.meta.provider;

import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContextProvider;
import com.intelligentsia.dowsers.entity.reference.Reference;

/**
 * MetaEntityContextProviderSupport.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * 
 */
public class MetaEntityContextProviderSupport implements MetaEntityContextProvider {

	/**
	 * Build a new instance of MetaEntityContextProviderSupport.
	 */
	public MetaEntityContextProviderSupport() {
	}

	@Override
	public MetaEntityContext find(Reference reference) throws IllegalArgumentException, NullPointerException {
		return null;
	}

}
