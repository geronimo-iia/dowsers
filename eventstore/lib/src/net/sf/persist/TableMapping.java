// $Id$

package net.sf.persist;

import java.lang.reflect.Method;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Holds mapping data from a given class and a table
 */
public final class TableMapping extends Mapping {

	private final Class objectClass;

	private final net.sf.persist.annotations.Table tableAnnotation;
	private final String tableName;

	private final String[] fields; // list of fields which have getters and setters
	private final Map<String, net.sf.persist.annotations.Column> annotationsMap; // maps field names to annotations
	private final Map<String, Method> gettersMap; // maps field names to getters
	private final Map<String, Method> settersMap; // maps field names to setters

	private final boolean supportsGetGeneratedKeys;
	private final boolean supportsBatchUpdates;

	private final Map<String, String> columnsMap = new LinkedHashMap(); // maps table columns to property names
	private final String[] columns;
	private final String[] primaryKeys;
	private final String[] notPrimaryKeys;
	private final String[] autoGeneratedColumns;
	private final String[] notAutoGeneratedColumns;

	private final String selectSql;
	private final String selectAllSql;
	private final String insertSql;
	private final String updateSql;
	private final String deleteSql;

	public TableMapping(final DatabaseMetaData metaData, final Class objectClass, final NameGuesser nameGuesser)
			throws SQLException {

		ResultSet resultSet = null;

		// object class
		this.objectClass = objectClass;

		// database support for auto increment keys
		supportsGetGeneratedKeys = metaData.supportsGetGeneratedKeys();

		// database support for batch updates
		supportsBatchUpdates = metaData.supportsBatchUpdates();

		// database name
		final String databaseProductName = metaData.getDatabaseProductName();

		// table annotation
		tableAnnotation = (net.sf.persist.annotations.Table) objectClass
				.getAnnotation(net.sf.persist.annotations.Table.class);

		// schema pattern
		String schemaPattern = null;
		if (databaseProductName.equalsIgnoreCase("Oracle")) {
			schemaPattern = "%"; // oracle expects a pattern such as "%" to work
		}

		// table name and annotation
		tableName = getTableName(metaData, schemaPattern, objectClass, nameGuesser);

		// all column names and types (from db)

		final List<String> columnsList = new ArrayList();
		resultSet = metaData.getColumns(null, schemaPattern, tableName, "%");
		while (resultSet.next()) {
			final String columnName = resultSet.getString(4);
			columnsList.add(columnName);
		}
		resultSet.close();
		columns = toArray(columnsList);

		// all primary keys (from db)

		final List<String> primaryKeysList = new ArrayList();
		resultSet = metaData.getPrimaryKeys(null, schemaPattern, tableName);
		while (resultSet.next()) {
			final String columnName = resultSet.getString(4);
			primaryKeysList.add(columnName);
		}
		resultSet.close();
		primaryKeys = toArray(primaryKeysList);

		// not primary keys

		final List<String> notPrimaryKeysList = new ArrayList();
		for (String columnName : columns) {
			if (!primaryKeysList.contains(columnName)) {
				notPrimaryKeysList.add(columnName);
			}
		}
		notPrimaryKeys = toArray(notPrimaryKeysList);

		// map field names to annotations, getters and setters

		final Map[] fieldsMaps = getFieldsMaps(objectClass);
		annotationsMap = fieldsMaps[0];
		gettersMap = fieldsMaps[1];
		settersMap = fieldsMaps[2];
		fields = toArray(gettersMap.keySet());

		// map column names to field names; create list of auto-increment columns
		// columnsMap use keys in lower case

		// the actual autoGeneratedColumns list should have columns in the database order
		final Set<String> autoGeneratedColumnsTemp = new HashSet();
		for (String fieldName : fields) {
			final String columnName = getColumnName(objectClass, nameGuesser, annotationsMap, columnsList, tableName,
					fieldName);
			columnsMap.put(columnName.toLowerCase(Locale.ENGLISH), fieldName);
			final net.sf.persist.annotations.Column annotation = annotationsMap.get(fieldName);
			if (annotation != null && annotation.autoGenerated()) {
				autoGeneratedColumnsTemp.add(columnName);
			}
		}

		// auto-increment and not-auto-increment columns, in the database order

		final List<String> notAutoGeneratedColumnsList = new ArrayList();
		final List<String> autoGeneratedColumnsList = new ArrayList();
		for (String columnName : columns) {
			if (autoGeneratedColumnsTemp.contains(columnName)) {
				autoGeneratedColumnsList.add(columnName);
			} else {
				notAutoGeneratedColumnsList.add(columnName);
			}
		}
		notAutoGeneratedColumns = toArray(notAutoGeneratedColumnsList);
		autoGeneratedColumns = toArray(autoGeneratedColumnsList);

		// assemble sql blocks to be used by crud sql statements

		final String allColumns = join(columns, "", ",");
		final String noAutoColumns = join(notAutoGeneratedColumns, "", ",");
		final String allPlaceholders = multiply("?", columns.length, ",");
		final String noAutoPlaceholders = multiply("?", notAutoGeneratedColumns.length, ",");
		final String where = join(primaryKeys, "=?", " and ");
		final String updateSet = join(notPrimaryKeys, "=?", ",");

		// assemble crud sql statements

		selectSql = "select " + allColumns + " from " + tableName + " where " + where;
		selectAllSql = "select " + allColumns + " from " + tableName;

		if (autoGeneratedColumns.length == 0) {
			insertSql = "insert into " + tableName + "(" + allColumns + ")values(" + allPlaceholders + ")";
		} else {
			insertSql = "insert into " + tableName + "(" + noAutoColumns + ")values(" + noAutoPlaceholders + ")";
		}

		updateSql = "update " + tableName + " set " + updateSet + " where " + where;
		deleteSql = "delete from " + tableName + " where " + where;

	}

	// ---------- getters and setters ----------

	public boolean supportsGetGeneratedKeys() {
		return supportsGetGeneratedKeys;
	}

	public boolean supportsBatchUpdates() {
		return supportsBatchUpdates;
	}

	public Class getObjectClass() {
		return objectClass;
	}

	public String getTableName() {
		return tableName;
	}

	public net.sf.persist.annotations.Table getTableAnnotation() {
		return tableAnnotation;
	}

	public String[] getColumns() {
		return columns;
	}

	public Map<String, String> getColumnsMap() {
		return columnsMap;
	}

	public String[] getPrimaryKeys() {
		return primaryKeys;
	}

	public String[] getNotPrimaryKeys() {
		return notPrimaryKeys;
	}

	public String[] getAutoGeneratedColumns() {
		return autoGeneratedColumns;
	}

	public String[] getNotAutoGeneratedColumns() {
		return notAutoGeneratedColumns;
	}

	public String[] getFields() {
		return fields;
	}

	public Map<String, net.sf.persist.annotations.Column> getAnnotationsMap() {
		return annotationsMap;
	}

	public Map<String, Method> getGettersMap() {
		return gettersMap;
	}

	public Map<String, Method> getSettersMap() {
		return settersMap;
	}

	public Method getGetterForColumn(final String columnName) {
		final String fieldName = columnsMap.get(columnName.toLowerCase(Locale.ENGLISH));
		return gettersMap.get(fieldName);
	}

	public Method getSetterForColumn(final String columnName) {
		final String fieldName = columnsMap.get(columnName.toLowerCase(Locale.ENGLISH));
		return settersMap.get(fieldName);
	}

	public String getSelectSql() {
		return selectSql;
	}

	public String getSelectAllSql() {
		return selectAllSql;
	}

	public String getInsertSql() {
		return insertSql;
	}

	public String getUpdateSql() {
		return updateSql;
	}

	public String getDeleteSql() {
		return deleteSql;
	}

	// ---------- utility methods ----------

	private static String getTableName(final DatabaseMetaData metaData, final String schema, final Class objectClass,
			final NameGuesser nameGuesser) throws SQLException {

		String name = null;

		final net.sf.persist.annotations.Table tableAnnotation = (net.sf.persist.annotations.Table) objectClass
				.getAnnotation(net.sf.persist.annotations.Table.class);

		if (tableAnnotation != null && !tableAnnotation.name().equals("")) {
			// if there's a Table annotation, use it
			name = checkTableName(metaData, schema, tableAnnotation.name());

			// test if the specified table name actually exists
			if (name == null) {
				throw new PersistException("Class [" + objectClass.getName() + "] specifies table ["
						+ tableAnnotation.name() + "] that does not exist in the database");
			}
		} else {
			// if no annotation, try guessed table names
			final String className = objectClass.getSimpleName().substring(0, 1).toLowerCase()
					+ objectClass.getSimpleName().substring(1);
			final Set<String> guessedNames = nameGuesser.guessColumn(className);
			for (String guessedTableName : guessedNames) {
				name = checkTableName(metaData, schema, guessedTableName);
				if (name != null) {
					break;
				}
			}
			if (name == null) {
				throw new PersistException("Class [" + objectClass.getName()
						+ "] does not specify a table name through a Table annotation and no guessed table names "
						+ guessedNames + " exist in the database");
			}
		}

		return name;
	}

	/**
	 * Check if the given name corresponds to a table in the database and
	 * returns the corresponding name with the capitalization returned by the
	 * database metadata
	 */
	private static String checkTableName(final DatabaseMetaData metaData, final String schema, final String tableName)
			throws SQLException {

		ResultSet resultSet;
		String ret = null;

		// try name in upper case -- should work in most databases
		resultSet = metaData.getTables(null, schema, tableName.toUpperCase(Locale.ENGLISH), null);
		if (resultSet.next()) {
			ret = tableName.toUpperCase(Locale.ENGLISH);
		}
		resultSet.close();

		if (ret == null) {
			// try name in lower case
			resultSet = metaData.getTables(null, schema, tableName.toLowerCase(Locale.ENGLISH), null);
			if (resultSet.next()) {
				ret = tableName.toLowerCase(Locale.ENGLISH);
			}
			resultSet.close();
		}

		if (ret == null) {
			// last resort: compare with all table names in the schema 
			// (may be very expensive in databases such as oracle)
			// this may end up being used in databases that allow case sensitive names (such as postgresql)
			resultSet = metaData.getTables(null, schema, "%", null);
			while (resultSet.next()) {
				final String dbTableName = resultSet.getString(3);
				if (tableName.equalsIgnoreCase(dbTableName)) {
					ret = dbTableName;
					break;
				}
			}
			resultSet.close();
		}

		return ret;
	}

	private static String getColumnName(final Class objectClass, final NameGuesser nameGuesser,
			final Map<String, net.sf.persist.annotations.Column> annotationsMap, final List<String> columnsList,
			final String tableName, final String fieldName) throws SQLException {

		String columnName = null;

		final net.sf.persist.annotations.Column annotation = annotationsMap.get(fieldName);
		if (annotation != null && !annotation.name().equals("")) {
			// if there's an annotation, use it
			columnName = getIgnoreCase(columnsList, annotation.name());

			// check if the specified column actually exists in the table
			if (columnName == null) {
				throw new PersistException("Field [" + fieldName + "] from class [" + objectClass.getName()
						+ "] specifies column [" + annotation.name() + "] on table ["
						+ tableName.toLowerCase(Locale.ENGLISH) + "] that does not exist in the database");
			}
		} else {
			// if no annotation, try guessed column names
			final Set<String> guessedNames = nameGuesser.guessColumn(fieldName);
			for (String guessedColumnName : guessedNames) {
				columnName = getIgnoreCase(columnsList, guessedColumnName);
				if (columnName != null) {
					break;
				}
			}
			if (columnName == null) {
				throw new PersistException("Field [" + fieldName + "] from class [" + objectClass.getName()
						+ "] does not specify a column name through a Column annotation and no guessed column names "
						+ guessedNames + " exist in the database. If this field is not supposed to be associated "
						+ "with the database, please annotate it with @NoColumn");
			}
		}

		return columnName;
	}

	// ---------- helpers ----------

	/**
	 * Returns the first entry from the provided collection that matches the
	 * provided string ignoring case during the comparison.
	 */
	private static String getIgnoreCase(final Collection<String> collection, final String str) {
		String ret = null;
		for (String s : collection) {
			if (s.equalsIgnoreCase(str)) {
				ret = s;
				break;
			}
		}
		return ret;
	}

	private static String[] toArray(final List<String> list) {
		String[] array = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	private static String[] toArray(final Set<String> set) {
		final String[] array = new String[set.size()];
		int i = 0;
		for (String s : set) {
			array[i] = s;
			i++;
		}
		return array;
	}

	private static String join(final String[] list, final String suffix, final String separator) {
		final StringBuffer buf = new StringBuffer();
		for (String obj : list) {
			buf.append(obj.toString()).append(suffix).append(separator);
		}
		if (buf.length() > 0 && separator.length() > 0) {
			buf.delete(buf.length() - separator.length(), buf.length());
		}
		return buf.toString();
	}

	private static String multiply(final String str, final int times, final String separator) {
		final StringBuffer buf = new StringBuffer();
		for (int i = 0; i < times; i++) {
			buf.append(str).append(separator);
		}
		if (separator.length() > 0) {
			buf.delete(buf.length() - separator.length(), buf.length());
		}
		return buf.toString();
	}

}
