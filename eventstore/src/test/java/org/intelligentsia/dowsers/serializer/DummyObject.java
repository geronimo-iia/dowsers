/**
 * 
 */
package org.intelligentsia.dowsers.serializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DummyObject.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class DummyObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private List<String> listName = new ArrayList<String>();

	/**
	 * Build a new instance of DummyObject.
	 */
	public DummyObject() {
		super();
	}

	/**
	 * Build a new instance of DummyObject.
	 * 
	 * @param name
	 */
	public DummyObject(final String name) {
		super();
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the listName
	 */
	public List<String> getListName() {
		return listName;
	}

	/**
	 * @param listName
	 *            the listName to set
	 */
	public void setListName(final List<String> listName) {
		this.listName = listName;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (listName == null ? 0 : listName.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DummyObject other = (DummyObject) obj;
		if (listName == null) {
			if (other.listName != null) {
				return false;
			}
		} else if (!listName.equals(other.listName)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
