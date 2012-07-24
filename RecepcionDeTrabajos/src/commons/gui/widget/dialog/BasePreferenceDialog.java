package commons.gui.widget.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import commons.gui.widget.PreferenceNode;

public abstract class BasePreferenceDialog extends PreferenceDialog {

	public BasePreferenceDialog(Shell parentShell, String title, boolean readOnly) {
		super(parentShell, new PreferenceManager());
		this.readOnly = readOnly;
		super.setSelectedNode(null);
		this.title = title;
	}

	private Button closeButton;

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		if (readOnly) {
			closeButton = createButton(parent, IDialogConstants.CLOSE_ID,
					IDialogConstants.CLOSE_LABEL, true);
			getShell().setDefaultButton(closeButton);
		} else {
			super.createButtonsForButtonBar(parent);
		}
	}

	@Override
	public void updateButtons() {
		if (readOnly) {
			closeButton.setEnabled(isCurrentPageValid());
		} else {
			super.updateButtons();
		}
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.CLOSE_ID) {
			cancelPressed();
			return;
		}
		super.buttonPressed(buttonId);
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (this.title != null) {
			shell.setText(this.title);
		}
	}

	@Override
	protected Control createTreeAreaContents(Composite parent) {
		Control control = super.createTreeAreaContents(parent);
		getTreeViewer().expandAll();
		return control;
	}

	@Override
	protected void okPressed() {
		SafeRunnable.run(new SafeRunnable() {

			public void run() {
				if (performOK()) {
					close();
				}
			}
		});
	}

	protected IPreferenceNode addNode(IPreferenceNode parent, String identif, PreferencePage page) {
		PreferenceNode node = new PreferenceNode(identif, page);
		if (parent == null) {
			this.getPreferenceManager().addToRoot(node);
		} else {
			parent.add(node);
		}
		return node;
	}

	protected abstract boolean performOK();

	private final String title;

	protected boolean readOnly;

}