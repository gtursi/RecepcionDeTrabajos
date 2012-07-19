/*
 * Licencia de Caja de Valores S.A., Versi�n 1.0
 *
 * Copyright (c) 2006 Caja de Valores S.A.
 * 25 de Mayo 362, Ciudad Aut�noma de Buenos Aires, Rep�blica Argentina
 * Todos los derechos reservados.
 *
 * Este software es informaci�n confidencial y propietaria de Caja de Valores S.A. ("Informaci�n
 * Confidencial"). Usted no divulgar� tal Informaci�n Confidencial y la usar� solamente de acuerdo a
 * los t�rminos del acuerdo de licencia que posee con Caja de Valores S.A.
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
