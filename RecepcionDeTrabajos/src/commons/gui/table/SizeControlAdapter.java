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
 * $Id: SizeControlAdapter.java,v 1.4 2007/04/12 20:43:28 cvschioc Exp $
 */
package commons.gui.table;

import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.widgets.TableColumn;

import commons.pref.PreferencesManager;

/**
 * @author Margarita Buriano
 * @version $Revision: 1.4 $ $Date: 2007/04/12 20:43:28 $
 */
public class SizeControlAdapter extends ControlAdapter {

	public SizeControlAdapter(String tableName) {
		super();
		this.tableName = tableName;
	}

	@Override
	public void controlResized(ControlEvent event) {
		TableColumn tableColumn = (TableColumn) event.getSource();
		ColumnInfo columnInfo = new ColumnInfo((String) tableColumn.getData(), tableColumn
				.getStyle(), tableColumn.getWidth());
		PreferencesManager.getInstance().setColumnInfo(this.tableName, columnInfo);
	}

	private final String tableName;
}