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

