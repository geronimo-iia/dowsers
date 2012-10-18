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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import javax.security.identity.client.RepositoryDescriptor;

/**
 * A statement about the value of an attribute or of a property of an attribute.
 *
 * @author ronmonzillo
 */
public class IDExpression<T extends Serializable> extends IDValue<T> {

    public static enum ExpressionType {

        Name,
        Value,
        PropertyName,
        PropertyValue
    };
    Collection<T> values;
    boolean ignoreCase = false;
    RepositoryDescriptor repositoryBinding = null;

    public IDExpression(T value) {
        super(value);
    }

    public IDExpression(Collection<T> values) {
        this.values = values;
    }

    public IDExpression(T... values) {
        this.values = new ArrayList<T>();
        this.values.addAll(Arrays.asList(values));
    }

    public IDExpression(IDExpression<T> x) {
        super((T) x.getValue());
        values = x.getValues();
        ignoreCase = x.getIgnoreCase();
    }

    public boolean getIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public Collection<T> getValues() {
        return values;
    }

    public ExpressionType getExpressionType() {
        return ExpressionType.Value;
    }
    
    /**
     * Get the descriptor of the repository to which this expression is bound. 
     * @return A repository descriptor if the expression has been explicitly
     * bound, or null if the expression has not been explicitly bound.
     */
    public RepositoryDescriptor getRepositoryBinding() {
        return this.repositoryBinding;
    }
    
    /**
     * Bind this expression to a specific leaf repository.
     * 
     * @param repositoryBinding a RepositoryDescriptor that identifies the
     * repository to which the expression is to be bound. 
     */
    public void setRepositoryBinding(RepositoryDescriptor repositoryBinding) {
        this.repositoryBinding = repositoryBinding;
    }

    /**
     *
     * @param left
     * @param right
     * @return 0 if both null, 1 or -1 if only 1 is null, 2 if both are not null
     */
    private static int comparePointers(Object left, Object right) {
        int rvalue = 2;
        if (left == null) {
            if (right != null) {
                rvalue = -1;
            } else {
                rvalue = 0;
            }
        } else if (right == null) {
            rvalue = 1;
        }
        return rvalue;
    }

    public static <C extends Serializable & Comparable> int compareTo(IDExpression<C> thisExpression, IDExpression<C> thatExpression) {
        int rvalue = comparePointers(thisExpression, thatExpression);
        if (rvalue == 2) {
            rvalue = thisExpression.getExpressionType().compareTo(thatExpression.getExpressionType());
            if (rvalue == 0) {
                rvalue = thisExpression.getIgnoreCase() == thatExpression.getIgnoreCase() ? 0 : 1;
            }
            if (rvalue == 0) {
                C thisValue = thisExpression.getValue();
                C thatValue = thatExpression.getValue();
                rvalue = comparePointers(thisValue, thatValue);
                if (rvalue == 2) {
                    rvalue = thisValue.compareTo(thatValue);
                } else if (rvalue == 0) {
                    Collection<C> thisValues = thisExpression.getValues();
                    Collection<C> thatValues = thatExpression.getValues();
                    rvalue = comparePointers(thisValues, thatValues);
                    if (rvalue == 2) {
                        rvalue = thisValues.size() - thatValues.size();
                        if (rvalue != 0) {
                            rvalue = rvalue > 0 ? 1 : -1;
                        } else {
                            Iterator<C> thisIt = thisValues.iterator();
                            Iterator<C> thatIt = thatValues.iterator();
                            while (rvalue == 0 && thisIt.hasNext()) {
                                rvalue = thisIt.next().compareTo(thatIt.next());
                            }
                        }
                    }
                    if (rvalue == 0) {
                        RepositoryDescriptor thisRepoBinding = thisExpression.getRepositoryBinding();
                        RepositoryDescriptor thatRepoBinding = thatExpression.getRepositoryBinding();
                        rvalue = comparePointers(thisRepoBinding, thatRepoBinding);
                        if (rvalue == 2) {
                            rvalue = thisRepoBinding.compareTo(thatRepoBinding);
                        }
                    }
                }
            }
        }
        return rvalue;
    }
}
