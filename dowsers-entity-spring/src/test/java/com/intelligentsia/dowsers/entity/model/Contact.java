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
package com.intelligentsia.dowsers.entity.model;

import java.net.URL;
import java.util.Date;

import com.intelligentsia.dowsers.entity.Entity;
import com.intelligentsia.dowsers.entity.reference.Reference;
import com.intelligentsia.dowsers.entity.validation.MetaCompliant;

/**
 * Entity representing
 * {@link com.mega.gp.portal.service.userprofile.api.model.User} contact
 * informations.
 * 
 * @author LRI
 * 
 */
@MetaCompliant
public interface Contact extends Entity {

	public String getPhoneNumber();

	public void setPhoneNumber(String phoneNumber);

	public String getEmail();

	public void setEmail(String email);

	public Reference getBelongingOrg();

	public void setBelongingOrg(Reference belongingOrg);

	public URL getAvatarUrl();

	public void setAvatarUrl(URL avatar);

	public Long getYearLong();

	public void setYearLong(Long y);

	public Integer getYearInteger();

	public void setYearInteger(Integer y);

	public Boolean isBot();

	public void setBot(Boolean y);

	public Date dob();

	public void dob(Date dob);

}