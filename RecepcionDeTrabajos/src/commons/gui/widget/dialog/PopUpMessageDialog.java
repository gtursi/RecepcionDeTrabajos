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
 * $Id: PopUpMessageDialog.java,v 1.6 2008/03/17 12:20:55 cvsmdiaz Exp $
 */

package commons.gui.widget.dialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;

import commons.gui.util.PageHelper;

/**
 * Modela un diálogo pop-up genérico, parametrizable con el tipo de diálogo.
 * @author Jonathan Chiocchio
 * @version $Revision: 1.6 $ $Date: 2008/03/17 12:20:55 $
 */

public class PopUpMessageDialog extends MessageDialog {

	protected PopUpMessageDialog(String dialogTitle, Image dialogTitleImage, String dialogMessage,
			PopUpMessageDialogType tipo) {
		super(null, dialogTitle, dialogTitleImage, dialogMessage,
				tipo.getDialogConstant(), tipo.getDialogButtonLabels(), 0);
	}

	public static int open(String dialogTitle, Image dialogTitleImage, String dialogMessage,
			PopUpMessageDialogType tipo) {
		PopUpMessageDialog dialog = new PopUpMessageDialog(dialogTitle, dialogTitleImage,
				dialogMessage, tipo);
		PageHelper.getDisplay().beep();
		return dialog.open();
	}

	/**
	 * Wrapper para abstraerse de las constantes enteras ("error-prone")
	 * @author Jonathan Chiocchio
	 */
	public enum PopUpMessageDialogType {
		WARNING_TYPE() {
			@Override
			public int getDialogConstant() {
				return MessageDialog.WARNING;
			}

			@Override
			public String[] getDialogButtonLabels() {
				return new String[] { ACEPTAR };
			}
		},
		INFORMATION_TYPE() {
			@Override
			public int getDialogConstant() {
				return MessageDialog.INFORMATION;
			}

			@Override
			public String[] getDialogButtonLabels() {
				return new String[] { ACEPTAR };
			}
		},
		ERROR_TYPE() {
			@Override
			public int getDialogConstant() {
				return MessageDialog.ERROR;
			}

			@Override
			public String[] getDialogButtonLabels() {
				return new String[] { ACEPTAR };
			}
		},
		QUESTION_TYPE() {
			@Override
			public int getDialogConstant() {
				return MessageDialog.QUESTION;
			}

			@Override
			public String[] getDialogButtonLabels() {
				return new String[] { ACEPTAR, "&Cancelar" };
			}
		};
		public abstract int getDialogConstant();

		public abstract String[] getDialogButtonLabels();

		private static final String ACEPTAR = "&Aceptar";
	}
}