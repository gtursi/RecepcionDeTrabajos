package commons.pref;

import commons.gui.table.ColumnInfo;

public class TableInfo {

	public TableInfo(String name, String order, ColumnInfo[] columnsInfo) {
		this.name = name;
		this.columnsInfo = columnsInfo;
		this.setOrder(order); // default (ordena por la primer columna de la tabla)
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	public void setOrder(String order) {
		this.order = order;
		if (columnsInfo == null) {
			columnIndexOrder = 0; // default column
		} else {
			for (int i = 0; i < columnsInfo.length; i++) {
				if (columnsInfo[i].fieldName.equals(order)) {
					columnIndexOrder = i;
				}
			}
		}
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		return this.order;
	}

	/**
	 * @return el índice de columna por la cual se ordena
	 */
	public int getColumnIndexOrder() {
		return columnIndexOrder;
	}

	/**
	 * @return información de columnas
	 */
	public ColumnInfo[] getColumnsInfo() {
		return columnsInfo;
	}

	public void setColumnInfo(ColumnInfo[] columnsInfo) {
		this.columnsInfo = columnsInfo;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj instanceof TableInfo) {
			TableInfo tableInfo = (TableInfo) obj;
			equals = this.equalsTo(tableInfo);
		}
		return equals;
	}

	public boolean equalsTo(TableInfo tableInfo) {
		return name.equals(tableInfo.name);
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (this.name != null) {
			result = (37 * result) + this.name.hashCode();
		}
		return result;
	}

	private final String name;

	private String order;

	private ColumnInfo[] columnsInfo;

	private int columnIndexOrder;

}
