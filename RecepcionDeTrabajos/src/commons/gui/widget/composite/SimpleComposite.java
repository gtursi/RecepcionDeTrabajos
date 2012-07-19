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
 * $Id: SimpleComposite.java,v 1.4 2007/05/09 20:16:41 cvstursi Exp $
 */

package commons.gui.widget.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import commons.gui.widget.DefaultLayoutFactory;


/**
 * Modela un composite básico de depositantes y cuentas.
 * @author Jonathan Chiocchio
 * @version $Revision: 1.4 $ $Date: 2007/05/09 20:16:41 $
 */

public class SimpleComposite extends Composite {

	public SimpleComposite(Composite parent, boolean readOnly, int numColumns) {
		super(parent, SWT.NONE);
		this.readOnly = readOnly;
		this.numColumns = numColumns;
		applyLayout();
	}
	
	/**
	 * Habilita todos los controles que tienen como padre a éste Composite.
	 */
	@Override
	public void setEnabled(boolean enabled) {
		Control[] children = getChildren();
		for (int i = 0; i < children.length; i++) {
			children[i].setEnabled(enabled);
		}
		super.setEnabled(enabled);
	}
	
	/**
	 * Provee un layout por default, sobreescribir este método si se desea otro layout.
	 */
	protected void applyLayout() {
		DefaultLayoutFactory.setDefaultGridLayout(this, getNumColumns());
		GridLayout layout = (GridLayout) this.getLayout();
		layout.marginLeft = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
	}

	protected int getNumColumns(){
		return numColumns;
	}
	
	protected boolean readOnly;

	private final int numColumns;


}
