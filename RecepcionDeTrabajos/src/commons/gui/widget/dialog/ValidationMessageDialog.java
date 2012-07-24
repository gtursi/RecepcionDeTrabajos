package commons.gui.widget.dialog;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.springframework.util.CollectionUtils;

import commons.gui.util.PageHelper;
import commons.gui.util.TextJfaceUtils;

public class ValidationMessageDialog extends PopUpMessageDialog {

	private static final String DIALOG_MESSAGE = "Validaciones:";

	protected ValidationMessageDialog(String dialogTitle, Image dialogTitleImage,
			String dialogMessage, String... messages) {
		super(dialogTitle, dialogTitleImage, dialogMessage, PopUpMessageDialogType.WARNING_TYPE);
		validationMessages = messages;
	}

	public static void open(String dialogTitle, List<String> messages) {
		String[] array = null;
		if (!CollectionUtils.isEmpty(messages)) {
			array = messages.toArray(new String[messages.size()]);
		}
		open(dialogTitle, array);
	}

	public static void open(String dialogTitle, String... messages) {
		if (StringUtils.isBlank(dialogTitle)) {
			dialogTitle = "Validaci�n";
		}
		PageHelper.getDisplay().beep();
		ValidationMessageDialog dialog = new ValidationMessageDialog(dialogTitle, null,
				DIALOG_MESSAGE, messages);
		dialog.open();
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		Composite composite = null;
		text = "";
		if ((validationMessages != null) && (validationMessages.length > 0)) {
			composite = new Composite(parent, SWT.NONE);
			composite.setLayout(new RowLayout(SWT.VERTICAL));
			StyledText textbox = new StyledText(composite, SWT.LEFT);
			textbox.setEnabled(false);
			textbox.setBackground(parent.getShell().getBackground());
			textbox.setEditable(false);
			for (String msg : validationMessages) {
				textbox.setText(textbox.getText() + "\t\t- " + msg
						+ System.getProperty("line.separator"));
			}
			text = textbox.getText();
		}
		return composite;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, -1, "&Copiar Todo", false);
		super.createButtonsForButtonBar(parent);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				TextJfaceUtils.copyToClipboard(text);
				setBlockOnOpen(true);
			}
		});
		getButton(IDialogConstants.OK_ID).setFocus();
	}

	@Override
	protected void buttonPressed(int buttonId) {
		setReturnCode(buttonId);
		if (buttonId == OK) {
			close();
		}
	}

	private final String[] validationMessages;

	private String text;
}