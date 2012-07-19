/*
 * Licencia de Caja de Valores S.A., Versión 1.0
 *
 * Copyright (c) 2006 Caja de Valores S.A.
 * 25 de Mayo 362, Ciudad Autónoma de Buenos Aires, República Argentina
 * Todos los derechos reservados.
 *
 * Este software es información confidencial y propietaria de Caja de Valores S.A. ("Información
 * Confidencial"). Usted no divulgará tal Información Confidencial y la usará solamente de acuerdo a
 * los términos del acuerdo de licencia que posee con Caja de Valores S.A.
 */
/*
 * $Id: SelectionColumnAdapter.java,v 1.3 2007/04/16 18:04:28 cvschioc Exp $
 */
package commons.gui.table;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;

import commons.pref.PreferencesManager;

/**
 * @version $Revision: 1.3 $ $Date: 2007/04/16 18:04:28 $
 */
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
