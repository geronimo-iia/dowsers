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
package com.intelligentsia.dowsers.entity.validation;

import java.util.Iterator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.meta.MetaAttribute;
import com.intelligentsia.dowsers.entity.meta.MetaEntityContext;

/**
 * MetaCompliantValidator.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaCompliantValidator implements ConstraintValidator<MetaCompliant, Entity> {

	@Override
	public void initialize(MetaCompliant constraintAnnotation) {
	}

	@Override
	public boolean isValid(Entity value, ConstraintValidatorContext context) {
		boolean valid = true;
		MetaEntityContext entityContext = value.metaEntityContext();
		Iterator<String> iterator = value.attributeNames().iterator();
		while (iterator.hasNext() && valid) {
			String name = iterator.next();
			MetaAttribute metaAttribute = entityContext.metaAttribute(name);
			if (metaAttribute == null) {
				valid = false;
			} else {
				Object attribute = value.attribute(name);
				if (attribute != null) {
					valid = metaAttribute.valueClass().getType().isAssignableFrom(attribute.getClass());
				}
			}
		}
		return valid;
	}

}
