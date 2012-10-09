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
 * An object that represents the name of an attribute or of a property of an
 * attribute, and that is used in a NameExpression or a PropertyNameExpression
 * to make a statement about the name of an attribute or of a property of an
 * attribute (respectively).
 *
 * @author ronmonzillo
 */
public class IDName implements Comparable<IDName>, Serializable {

    public static final long serialVersionUID = 1L;
    java.lang.String name;

    public IDName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof IDName) {
            return name.equals(((IDName) that).getName());
        }
        return false;
    }

    @Override
    public int compareTo(IDName that) {
        return name.compareTo(that.name);
    }
}
