package org.intelligentsia.dowsers.eventstore.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.intelligentsia.dowsers.DowsersException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Modified version of org.hibernate.engine.TransactionHelper.
 * 
 * Here we using only a datasource and return a value for IsolatedWork instance.
 * 
 *@author <a href="mailto:jguibert@intelligents-ia.com">Jerome Guibert</a>
 */
public class JdbcDelegate {
	private static final Logger log = LoggerFactory.getLogger(JdbcDelegate.class);

	private final DataSource dataSource;

	/**
	 * Build a new instance of JdbcDelegate.
	 */
	public JdbcDelegate(final DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public <T> T delegateWork(IsolatedWork<T> work, boolean transacted) throws DowsersException {
		boolean wasAutoCommit = false;
		try {
			Connection connection = dataSource.getConnection();
			try {
				if (transacted) {
					if (connection.getAutoCommit()) {
						wasAutoCommit = true;
						connection.setAutoCommit(false);
					}
				}

				T result = work.doWork(connection);

				if (transacted) {
					connection.commit();
				}
				return result;
			} catch (Exception e) {
				try {
					if (transacted && !connection.isClosed()) {
						connection.rollback();
					}
				} catch (Exception ignore) {
					log.info("unable to rollback connection on exception [" + ignore + "]");
				}

				if (e instanceof DowsersException) {
					throw (DowsersException) e;
				} else {
					throw new DowsersException("error performing isolated work", e);
				}
			} finally {
				if (transacted && wasAutoCommit) {
					try {
						connection.setAutoCommit(true);
					} catch (Exception ignore) {
						log.trace("was unable to reset connection back to auto-commit");
					}
				}
				try {
					connection.close();
				} catch (Exception ignore) {
					log.info("Unable to release isolated connection [" + ignore + "]");
				}
			}
		} catch (SQLException sqle) {
			throw new DowsersException("unable to obtain isolated JDBC connection", sqle);
		}
	}
}
