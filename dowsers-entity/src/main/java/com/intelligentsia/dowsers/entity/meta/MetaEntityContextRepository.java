package com.intelligentsia.dowsers.entity.meta;

import java.net.URI;

/**
 * MetaEntityContextRepository.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public interface MetaEntityContextRepository {

	/**
	 * 
	 * @param reference
	 * @return {@link MetaEntityContext} associated with thi reference
	 * 
	 * @throws IllegalArgumentException
	 *             if no {@link MetaEntityContext} was found
	 * @throws NullPointerException
	 *             if reference is null
	 */
	MetaEntityContext find(URI reference) throws IllegalArgumentException, NullPointerException;

}
