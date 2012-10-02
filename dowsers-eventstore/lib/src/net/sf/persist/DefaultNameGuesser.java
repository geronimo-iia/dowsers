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
// $Id$

package net.sf.persist;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Default NameGuesser implementation.
 */
public final class DefaultNameGuesser implements NameGuesser {

	/**
	 * Given a field or class name in the form CompoundName (for classes) or
	 * compoundName (for fields) will return a set of guessed names such as
	 * [compound_name, compound_names, compoundname, compoundnames].
	 */
	public Set<String> guessColumn(final String fieldOrClassName) {

		final String nameUnderscore = fieldOrClassName.replaceAll("([A-Z])", "_$1").toLowerCase();
		final String nameLowercase = fieldOrClassName.toLowerCase(Locale.ENGLISH);

		final Set<String> names = new LinkedHashSet();
		names.add(nameUnderscore);
		names.add(nameLowercase);
		names.add(nameUnderscore + "s");
		names.add(nameLowercase + "s");
		return names;
	}

}
