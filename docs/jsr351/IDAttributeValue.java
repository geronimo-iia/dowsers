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
import java.util.Collection;

/**
 * java representation of the value of an attribute.
 *
 * @author ronmonzillo
 */
public interface IDAttributeValue<T extends Serializable> {

        /**
     * get the single-value of the attribute
     *
     * @return the singular value of the attribute if the attribute is not
     * multi-valued, or null otherwise.
     */
    T getValue();

    /**
     * get the value of the multi-valued attribute as a Collection.
     *
     * @return The values of the attribute as a collection. Order, and
     * uniqueness need to be appropriately handled by the returned
     * collection type.  An empty collection indicates an empty value. 
     */
    Collection<T> getValues();

}
