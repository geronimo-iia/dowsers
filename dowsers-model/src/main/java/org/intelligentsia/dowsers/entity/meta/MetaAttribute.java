package org.intelligentsia.dowsers.entity.meta;

import java.io.Serializable;

import org.intelligentsia.dowsers.entity.Entity;

/**
 * 
 * MetaAttribute: Ash nazg durbatulûk, ash nazg gimbatul, ash nazg thrakatulûk
 * agh burzum-ishi krimpatul.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface MetaAttribute extends Entity {

	/**
	 * Returns the attribute name.
	 * 
	 * @return non-<code>null</code> textual attribute name
	 */
	public String getName();

	/**
	 * Returns the <code>{@link Class}</code> object representing the
	 * <code>Value</code> of attribute.
	 * 
	 * @return non-<code>null</code> <code>{@link Class}</code> instance
	 */
	public Class<?> getValueClass();

	/**
	 * Returns the attribute default value.
	 * 
	 * @return <code>null</code> or non-<code>null</code> <code>Value</code>
	 *         instance
	 */
	public <Value extends Serializable> Value getDefaultValue();

}
