/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2008, Red Hat Middleware LLC or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Middleware LLC.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 *
 */
package org.intelligentsia.dowsers.eventstore.support.jdbc;

import java.sql.Connection;

import org.intelligentsia.dowsers.core.DowsersException;

/**
 * Represents work that needs to be performed in a manner which isolates it from
 * any current application unit of work transaction.
 * 
 * Here we throw a DowsersException and add a way to retrun a value.
 * 
 * 
 * 
 * @author Steve Ebersole
 * @author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public interface IsolatedWork<T> {
	/**
	 * Perform the actual work to be done.
	 * 
	 * @param connection
	 *            The JDBC connection to use.
	 * @throws DowsersException
	 */
	public T doWork(Connection connection) throws DowsersException;
}
