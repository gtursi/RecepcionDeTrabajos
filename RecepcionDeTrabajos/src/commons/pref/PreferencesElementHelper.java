package commons.pref;

import org.eclipse.swt.SWT;

import commons.gui.table.ColumnInfo;
import commons.logging.AppLogger;

public abstract class PreferencesElementHelper {

	public static void createTableNode(IMemento root, TableInfo table) {
		AppLogger.getLogger().finer("Creando Nodo para la tabla " + table.getName());
		IMemento newTable = root.createChild(XmlConstants.TABLE);
		newTable.putString(XmlConstants.NAME, table.getName());
		newTable.putString(XmlConstants.ORDER_BY, table.getOrder());
		ColumnInfo[] columns = table.getColumnsInfo();
		for (ColumnInfo column : columns) {
			PreferencesElementHelper.createColumnNode(newTable, column);
		}
	}

	public static void createColumnNode(IMemento root, ColumnInfo column) {
		AppLogger.getLogger().finer("Creando Nodo para la columna " + column.fieldName);
		IMemento newColumn = root.createChild("column");
		newColumn.putString(XmlConstants.NAME, column.fieldName);
		newColumn.putInteger(XmlConstants.WIDTH, column.width);
		newColumn.putString(XmlConstants.ALIGNMENT, PreferencesHelper.getStyle(column.style));
	}

	/**
	 * Toma la estructura de las preferencias por default, y las modifica con los atributos de las
	 * preferencias del usuario.
	 * 
	 * @param root
	 * @param defaultTableInfo
	 * @return La información de las tablas ya mergeada
	 */
	public static void updateDefaultableInfo(IMemento root, TableInfo defaultTableInfo) {
		// FIXME Corregir mergeo de información de preferencias!!!
		AppLogger.getLogger().finer("Actualizando la tabla " + defaultTableInfo.getName());

		if (PreferencesElementHelper.existsTableNode(root, defaultTableInfo.getName())) {
			IMemento userTableNode = PreferencesElementHelper.getTableNode(root,
					defaultTableInfo.getName());

			for (int i = 0; i < defaultTableInfo.getColumnsInfo().length; i++) {
				PreferencesElementHelper.updateDefaultColumnInfo(userTableNode,
						defaultTableInfo.getColumnsInfo()[i]); // actualizo arbol
			}
			if (userTableNode.getString(XmlConstants.ORDER_BY) != null) {
				// si la columna ha sido removida la ignora, la proxima vez que ordene se corregirá
				defaultTableInfo.setOrder(userTableNode.getString(XmlConstants.ORDER_BY));
			}
		}
	}

	/**
	 * Actualiza la información actual (root) con la información del archivo de preferencias del
	 * usuario.
	 * 
	 * @param root
	 *            Preferencias por defector
	 * @param defaultColumnInfo
	 *            Información de las preferencias del usuario
	 * @return
	 */
	public static void updateDefaultColumnInfo(IMemento root, ColumnInfo defaultColumnInfo) {
		AppLogger.getLogger().finer("Actualizando la columna " + defaultColumnInfo.fieldName);

		if (PreferencesElementHelper.existsColumnNode(root, defaultColumnInfo.fieldName)) {
			// me quedo con la estructura default pero con la información del usuario
			IMemento rootColumnInfo = PreferencesElementHelper.getColumnNode(root,
					defaultColumnInfo.fieldName);
			if (rootColumnInfo.getString(XmlConstants.WIDTH) != null) {
				defaultColumnInfo.width = rootColumnInfo.getInteger(XmlConstants.WIDTH);
			}
			if (rootColumnInfo.getString(XmlConstants.ALIGNMENT) != null) {
				String alignmentCode = rootColumnInfo.getString(XmlConstants.ALIGNMENT);
				defaultColumnInfo.style = PreferencesHelper.getStyleCode(alignmentCode);
			}
		}
	}

	public static boolean existsTableNode(IMemento root, String tableName) {
		boolean existsTableNode = false;
		IMemento[] tables = root.getChildren(XmlConstants.TABLE);
		for (IMemento table : tables) {
			// obtengo columna que corresponde(info)
			if (table.getString(XmlConstants.NAME).equals(tableName)) {
				AppLogger.getLogger().finer("Se encontró información sobre la tabla " + tableName);
				existsTableNode = true;
				break;
			}
		}
		return existsTableNode;
	}

	public static boolean existsColumnNode(IMemento root, String columnName) {
		boolean existsColumnNode = false;
		IMemento[] columns = root.getChildren(XmlConstants.COLUMN);
		for (IMemento column : columns) {
			if (column.getString(XmlConstants.NAME).equals(columnName)) {
				AppLogger.getLogger()
						.finer("Se encontró información sobre la columna" + columnName);
				existsColumnNode = true;
				break;
			}
		}
		return existsColumnNode;
	}

	public static IMemento getTableNode(IMemento root, String name) {
		IMemento result = null;
		IMemento[] tables = root.getChildren(XmlConstants.TABLE);
		for (IMemento table : tables) {
			// obtengo columna que corresponde(info)
			if (table.getString(XmlConstants.NAME).equals(name)) {
				result = table;
				break;
			}
		}
		return result;
	}

	public static IMemento getColumnNode(IMemento root, String name) {
		IMemento result = null;
		IMemento[] columns = root.getChildren(XmlConstants.COLUMN);
		for (IMemento column : columns) {
			// obtengo columna que corresponde(info)
			if (column.getString(XmlConstants.NAME).equals(name)) {
				result = column;
				break;
			}
		}
		return result;
	}

	/**
	 * A partir del xml genera el objeto TableInfo
	 * 
	 * @param aTable
	 * @return
	 */
	public static TableInfo getTableInfo(IMemento aListOfTables, String name) {
		IMemento root = PreferencesElementHelper.getTableNode(aListOfTables, name);

		IMemento[] columns = root.getChildren(XmlConstants.COLUMN);
		ColumnInfo[] cols = new ColumnInfo[columns.length];
		for (int i = 0; i < columns.length; i++) {
			IMemento col = columns[i];
			ColumnInfo column = PreferencesElementHelper.getColumnInfo(col);
			cols[i] = column;
		}
		return new TableInfo(root.getString(XmlConstants.NAME),
				root.getString(XmlConstants.ORDER_BY), cols);
	}

	/**
	 * A partir del xml genera el objeto ColumnInfo
	 * 
	 * @param column
	 * @return
	 */
	public static ColumnInfo getColumnInfo(IMemento column) {
		String alignment = column.getString(XmlConstants.ALIGNMENT);
		Integer code = SWT.LEFT;
		if (alignment == null) {
			code = SWT.LEFT;
		} else {
			code = PreferencesHelper.getStyleCode(alignment);
		}
		return new ColumnInfo(column.getString(XmlConstants.NAME), code,
				column.getInteger(XmlConstants.WIDTH));
	}

	/**
	 * Pisa la información de columna sobre la tabla por la nueva información
	 * 
	 * @param root
	 * @param tableName
	 * @param columnInfo
	 */
	public static void modifyColumnInfo(IMemento root, String tableName, ColumnInfo columnInfo) {
		PreferencesElementHelper.getTableNode(root, tableName);
		IMemento tableNode = PreferencesElementHelper.getTableNode(root, tableName);
		if (tableNode != null) {
			IMemento[] columns = tableNode.getChildren(XmlConstants.COLUMN);
			for (IMemento col : columns) {
				if (col.getString(XmlConstants.NAME).equals(columnInfo.fieldName)) {
					col.putInteger(XmlConstants.WIDTH, columnInfo.width);
					col.putString(XmlConstants.ALIGNMENT,
							PreferencesHelper.getStyle(columnInfo.style));
				}
			}
		}
	}

	/**
	 * Mantiene el nuevo orden de la tabla
	 * 
	 * @param root
	 * @param tableName
	 * @param columnInfo
	 */
	public static void modifyTableInfo(IMemento root, String tableName, String columnOrderBy) {
		PreferencesElementHelper.getTableNode(root, tableName);
		IMemento tableNode = PreferencesElementHelper.getTableNode(root, tableName);
		tableNode.putString(XmlConstants.ORDER_BY, columnOrderBy);
	}

}