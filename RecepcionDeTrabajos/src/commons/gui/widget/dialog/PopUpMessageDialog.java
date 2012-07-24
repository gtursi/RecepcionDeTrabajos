package commons.gui.widget.dialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;

import commons.gui.util.PageHelper;

/**
 * Modela un diálogo pop-up genérico, parametrizable con el tipo de diálogo.
 */
public class PopUpMessageDialog extends MessageDialog {

	protected PopUpMessageDialog(String dialogTitle, Image dialogTitleImage, String dialogMessage,
			PopUpMessageDialogType tipo) {
		super(null, dialogTitle, dialogTitleImage, dialogMessage, tipo.getDialogConstant(), tipo
				.getDialogButtonLabels(), 0);
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
	 * 
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