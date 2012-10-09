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
package javax.security.identity.client.expression;

import java.io.Serializable;

/**
 * An object that represents the value of an attribute or of a property of an
 * attribute, and that is used in an IExpression or a PropertyExpression to make
 * a statement about the value of an attribute or of a property of an attribute
 * (respectively).
 *
 * @author ronmonzillo
 */
public class IDValue<T extends Serializable> implements Serializable {

    public static final long serialVersionUID = 1L;
    T value;

    public IDValue() {
    }

    public IDValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
