package commons.gui.widget.dialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;

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
