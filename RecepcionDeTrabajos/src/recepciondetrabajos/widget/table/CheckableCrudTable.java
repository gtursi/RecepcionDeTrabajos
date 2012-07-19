package recepciondetrabajos.widget.table;

import java.util.Properties;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;

import commons.gui.table.CrudTable;
import commons.gui.table.handler.RowSelectionHandler;

/**
 * @author Jonathan Chiocchio
 * @version $Revision: 1.2 $ $Date: 2007/05/28 17:54:58 $
 */

public abstract class CheckableCrudTable<ITEM_TYPE, R extends RowSelectionHandler> extends
		CrudTable {

	public CheckableCrudTable(Composite parent, Class clazz, String tableName, Properties labels,
			R rowSelectionHandler, boolean sorteable, boolean readOnly) {
		this(parent, clazz, tableName, labels, rowSelectionHandler, sorteable, readOnly,
				DEFAULT_TABLE_STYLE);
	}

	public CheckableCrudTable(Composite parent, Class clazz, String tableName, Properties labels,
			R rowSelectionHandler, boolean sorteable, boolean readOnly, int style) {
		super(parent, clazz, tableName, labels, rowSelectionHandler, sorteable, readOnly,
				FULL_ROW_SELECTABLE | style);
		this.rowSelectionHandler = rowSelectionHandler;
		this.setUpAllRowsStatus();
		this.addPostSelectionChangedListener(this.getCheckedListener());
	}

	/**
	 * Define el comportamiento al chequear una fila de la tabla.
	 * 
	 * @return un listener que define dicho comportamiento.
	 */
	protected abstract ISelectionChangedListener getCheckedListener();

	/**
	 * Especifica el status de "checked" o "not checked" en cada uno de los ítems de la tabla.
	 */
	@SuppressWarnings("unchecked")
	public void setUpAllRowsStatus() {
		TableItem[] items = super.getTable().getItems();
		ITEM_TYPE rowObject;
		for (TableItem item : items) {
			rowObject = (ITEM_TYPE) item.getData();
			item.setChecked(hasToBeChecked(rowObject));
		}
	}

	protected abstract boolean hasToBeChecked(ITEM_TYPE rowObject);

	protected R rowSelectionHandler;

	private static final int FULL_ROW_SELECTABLE = SWT.CHECK | SWT.FULL_SELECTION;
}
