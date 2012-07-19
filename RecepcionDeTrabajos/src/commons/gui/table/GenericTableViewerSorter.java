package commons.gui.table;

import java.util.logging.Level;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import commons.logging.AppLogger;
import commons.pref.TableInfo;
import commons.util.ClassUtils;

public abstract class GenericTableViewerSorter extends ViewerSorter {

	public GenericTableViewerSorter(TableInfo tableInfo) {
		super();
		this.columnsInfo = tableInfo.getColumnsInfo();
		this.doSort(tableInfo.getColumnIndexOrder());
	}

	public void doSort(int column) {
		if ((columnIndex!=null)&&(column == this.columnIndex)) {
			// Same column as last sort; toggle the direction
			direction = 1 - direction;
		} else {
			// New column; do an ascending sort
			this.columnIndex = column;
			direction = ASCENDING;
		}
	}

	/**
	 * Compara dos objetos, bajo ciertas circunstancias, delega la comparación de String en
	 * RuleBasedCollator.
	 * @see RuleBasedCollator
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int compare(Viewer viewer, Object element1, Object element2) {
		int result = 0;
		ColumnInfo columnInfo = columnsInfo[columnIndex];
		String fieldName = columnInfo.fieldName;
		try {
			Object value1 = ClassUtils.getObject(element1, fieldName);
			Object value2 = ClassUtils.getObject(element2, fieldName);
			if (value1 == null) {
				result = -1;
			} else if (value2 == null) {
				result = 1;
			} else if (value1 instanceof String) {
				try {
					// intento comparar como enteros, si ambos no son enteros, comparo como Strings.
					Integer intValue1 = Integer.parseInt((String) value1);
					Integer intValue2 = Integer.parseInt((String) value2);
					result = intValue1.compareTo(intValue2);
				} catch (NumberFormatException e) {
					result = collator.compare((String) value1, (String) value2);
				}
			} else if (value1 instanceof Comparable) {
				result = ((Comparable) value1).compareTo(value2);
			}
		} catch (Exception e) {
			AppLogger.getLogger().log(Level.SEVERE,
					"Error al comparar 2 campos para ordenar: " + fieldName, e);
		}

		// If descending order, flip the direction
		if (direction == DESCENDING) {
			result = -result;
		}

		return result;
	}

	protected abstract Class getOrderedClass();

	private final ColumnInfo[] columnsInfo;

	private Integer columnIndex;

	private int direction;

	private static final int ASCENDING = 0;

	private static final int DESCENDING = 1;
}
