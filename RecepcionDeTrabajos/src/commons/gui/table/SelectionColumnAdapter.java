package commons.gui.table;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;

import commons.pref.PreferencesManager;

public class SelectionColumnAdapter extends SelectionAdapter {

	public SelectionColumnAdapter(String tableName, int columnIndex, GenericTable table) {
		super();
		this.tableName = tableName;
		this.table = table;
		this.columnIndex = columnIndex;
	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		event.getSource();
		((GenericTableViewerSorter) table.getSorter()).doSort(columnIndex);
		TableColumn column = (TableColumn) event.getSource();
		PreferencesManager.getInstance().setTableInfo(tableName, (String) column.getData());
		table.refresh();
	}

	private final String tableName;

	private final GenericTable table;

	private final int columnIndex;
}
