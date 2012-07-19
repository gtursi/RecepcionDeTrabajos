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
 * $Id: DefaultLayoutFactory.java,v 1.4 2007/05/22 19:22:41 cvstursi Exp $
 */
package commons.gui.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author Gabriel Tursi
 * @version $Revision: 1.4 $ $Date: 2007/05/22 19:22:41 $
 */
public abstract class DefaultLayoutFactory {

	public static void setDefaultGridLayout(Composite composite) {
		setDefaultGridLayout(composite, 2);
	}

	public static void setDefaultGridLayout(Composite composite, int numColumns, GridData gridData) {
		boolean makeColumnsEqualWidth = false;
		GridLayout layout = new GridLayout(numColumns, makeColumnsEqualWidth);
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		layout.verticalSpacing = 6;
		composite.setLayout(layout);
		composite.setLayoutData(gridData);
	}

	public static void setDefaultGridLayout(Composite composite, int numColumns) {
		DefaultLayoutFactory.setDefaultGridLayout(composite, numColumns, getDefaultGridData());
	}

	private static GridData getDefaultGridData() {
		return new GridData(DEFAULT_GRID_DATA_STYLE);
	}

	public static void setDefaultRowLayout(Composite composite) {
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		layout.marginWidth = 5;
		layout.marginHeight = 10;
		throw new RuntimeException("Metodo no implementado");
	}

	public static final int DEFAULT_GRID_DATA_STYLE = GridData.GRAB_HORIZONTAL
			| GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING;

}
