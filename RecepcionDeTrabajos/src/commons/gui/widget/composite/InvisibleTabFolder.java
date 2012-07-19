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
 * $Id: InvisibleTabFolder.java,v 1.4 2007/04/16 18:04:31 cvschioc Exp $
 */
package commons.gui.widget.composite;

import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * 
 * @author Rodrigo Alonso
 * @version $Revision: 1.4 $ $Date: 2007/04/16 18:04:31 $
 */
public class InvisibleTabFolder extends Composite {
	
	public InvisibleTabFolder(Composite parent, int style) {
		super(parent, style);
		StackLayout stackLayout = new StackLayout();
		stackLayout.marginWidth = -5;
		super.setLayout(stackLayout);
	}

	@Override
	public void setLayout(Layout layout) {
		// no se puede establecer un nuevo Layout
	}

	public void setSelection(int index) {
		Control[] children = getChildren();
		if (index >= 0 && index < children.length) {
			((StackLayout) getLayout()).topControl = children[index];
			layout();
		}
	}
	
}
