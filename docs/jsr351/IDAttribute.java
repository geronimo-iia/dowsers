/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the JSR 351 Specification
 * License at:
 *
 * http://jcp.org/aboutJava/communityprocess/licenses/jsr351/351SpecLicenseIdentityJSR.pdf
 *
 * The JSR 351 Reference Implementation is Licensed under Apache License, 
 * version 2.0, at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package javax.security.identity;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.Map;
import javax.security.identity.client.ProviderLookupContext;
import javax.security.identity.client.expression.IDName;

/**
 * The identity Attribute Interface; a container of attributes names, 
 * values, and properties.
 * 
 * @author ronmonzillo
 */
public interface IDAttribute<T extends Serializable> {

    /** Get the URI the unique identifier of the attribute within the repository.
     *
     * @return A URI that identifies the attribute within a repository.
     * 
     */
    URI getAttributeIdentifier();

    /**
     * Get the common names by which the attribute is known at the provider.
     *
     * @return a Collection<IDname> containing the names of the attribute.
     */
    Collection<IDName> getAttributeNames();

    /**
     * Get the value of the attribute.
     * 
     * @return an IDAttributeValue that contains the value(s) of the attribute,
     * or null if the value of the attribute is missing.
     */
    IDAttributeValue<T> getAttributeValue();
    
    /**
     * Get the map of properties(i.e., meta-data) associated with the
     * attribute instance.
     * 
     * @return a Map containing the meta-data associated with the attribute.
     * Any attempt to modify the returned Map must throw an 
     * UnsupportedOperationException.
     */

    Map<IDName,IDAttributeValue> getPropertyMap();
    

    /**
     * Get the context that may be used to acquire an attribute provider at
     * which the attribute may be exchanged for an attribute reference.
     *
     * @return the ProviderLookupContext corresponding to an AttributeProvider
     * at which the IDAttribute may be exchanged for an IDAttributeReference.
     */
    ProviderLookupContext getProviderLookupContext();
}
