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
 * $Id: BaseDialog.java,v 1.7 2009/02/23 18:01:22 cvstursi Exp $
 */
package commons.gui.widget.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 *
 * @author Gabriel Tursi
 * @version $Revision: 1.7 $ $Date: 2009/02/23 18:01:22 $
 */
public abstract class BaseDialog extends Dialog {

	public BaseDialog(String title, boolean readOnly) {
		super((Shell)null);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		this.readOnly = readOnly;
		this.title = title;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(title);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		applyLayout(parent);
		return addFields(parent);
	}

	protected abstract Control addFields(Composite parent);

	protected void applyLayout(Composite parent) {
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginTop = 10;
		gridLayout.marginBottom = 10;
		gridLayout.marginLeft = 5;
		gridLayout.marginRight = 5;
		gridLayout.verticalSpacing = 10;
		parent.setLayout(gridLayout);
	}

	protected boolean readOnly;

	private final String title;

}
