package com.intelligentsia.dowsers.entity.serializer;

import java.util.Map;

import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.EntityDynamic;

/**
 * 
 * EntityDynamicFactory declare methods to instantiate child class if
 * {@link EntityDynamic}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 * @param <T>
 */
public interface EntityDynamicFactory<T extends Entity> {

	public T newInstance(String identity, Map<String, Object> attributes);

}