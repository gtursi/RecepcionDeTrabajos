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
 * $Id: WarningDialog.java,v 1.5 2007/10/23 19:18:46 cvstursi Exp $
 */
package commons.gui.widget.dialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;

/**
 *
 * @author Gabriel Tursi
 * @version $Revision: 1.5 $ $Date: 2007/10/23 19:18:46 $
 */
public class WarningDialog extends MessageDialog {

	protected WarningDialog(String dialogTitle, Image dialogTitleImage, String dialogMessage) {
		super(null, dialogTitle, dialogTitleImage, dialogMessage, MessageDialog.WARNING,
				new String[] { "&Aceptar" }, 0);
	}

	public static void open(String dialogTitle, Image dialogTitleImage, String dialogMessage) {
		WarningDialog dialog = new WarningDialog(dialogTitle, dialogTitleImage, dialogMessage);
		dialog.open();
	}

}

