package commons.gui.table;

import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.widgets.TableColumn;

import commons.pref.PreferencesManager;

public class SizeControlAdapter extends ControlAdapter {

	public SizeControlAdapter(String tableName) {
		super();
		this.tableName = tableName;
	}

	@Override
	public void controlResized(ControlEvent event) {
		TableColumn tableColumn = (TableColumn) event.getSource();
		ColumnInfo columnInfo = new ColumnInfo((String) tableColumn.getData(),
				tableColumn.getStyle(), tableColumn.getWidth());
		PreferencesManager.getInstance().setColumnInfo(this.tableName, columnInfo);
	}

	private final String tableName;
}