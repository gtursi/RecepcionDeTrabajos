package commons.gui.table;

import org.eclipse.swt.SWT;

public class ColumnInfo {

	public ColumnInfo(String fieldName, Integer style, Integer width) {
		this.fieldName = fieldName;
		this.style = style;
		this.width = width;
	}

	public ColumnInfo(String fieldName, Integer style) {
		this(fieldName, style, -1);
	}

	public ColumnInfo(String fieldName) {
		this(fieldName, SWT.LEFT, -1);
	}

	@Override
	public boolean equals(Object obj) {
		boolean iguales = false;
		if (obj instanceof ColumnInfo) {
			ColumnInfo columnInfoComparado = (ColumnInfo) obj;
			iguales = this.equalsTo(columnInfoComparado);
		}
		return iguales;
	}

	private boolean equalsTo(ColumnInfo columnInfo) {
		return columnInfo.fieldName.equals(this.fieldName);
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (this.fieldName != null) {
			result = (37 * result) + this.fieldName.hashCode();
		}
		return result;
	}

	public String fieldName;

	public Integer style;

	public Integer width;
}