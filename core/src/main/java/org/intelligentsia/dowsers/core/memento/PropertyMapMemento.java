/**
 *        Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 *
 */
package org.intelligentsia.dowsers.core.memento;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * PropertyMapMemento.
 * 
 * Exploit idea that we could save an entity state using only all this
 * properties.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class PropertyMapMemento implements Memento {

	private Map<String, Object> properties;

	/**
	 * Build a new instance of PropertyMapMemento.
	 */
	public PropertyMapMemento() {
		super();
	}

	/**
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		if (properties != null) {
			out.writeInt(properties.size());
			for (Entry<String, Object> property : properties.entrySet()) {
				out.writeUTF(property.getKey());
				out.writeObject(property.getValue());
			}
		} else {
			out.writeInt(0);
		}
	}

	/**
	 * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		int size = in.readInt();
		if (size > 0) {
			properties = new HashMap<String, Object>(size);
			for (int i = 0; i < size; i++) {
				properties.put(in.readUTF(), in.readObject());
			}
		}
	}

	/**
	 * @return
	 * @see java.util.Map#size()
	 */
	public int size() {
		return properties.size();
	}

	/**
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return properties.get(key);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(String key, Object value) {
		return properties.put(key, value);
	}

	/**
	 * @return
	 * @see java.util.Map#entrySet()
	 */
	public Set<Entry<String, Object>> entrySet() {
		return properties.entrySet();
	}

}
