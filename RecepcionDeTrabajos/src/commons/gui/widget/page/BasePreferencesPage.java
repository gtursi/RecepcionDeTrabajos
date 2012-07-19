package commons.gui.widget.page;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import recepciondetrabajos.MainWindow;




import commons.gui.form.Form;
import commons.gui.widget.DefaultLayoutFactory;
import commons.gui.widget.creation.binding.Binding;

/**
 * @author Gabriel Tursi
 * @version $Revision: 1.42 $ $Date: 2007/12/11 17:54:24 $
 */

public abstract class BasePreferencesPage<T> extends PreferencePage {

	public BasePreferencesPage(T model, String title, String description, boolean readOnly) {
		super(title);
		this.readOnly = readOnly;
		setDescription(description);
		noDefaultAndApplyButton();
		this.form = new Form(model);
		this.readOnly = false;
	}

	public BasePreferencesPage(T model, String title, boolean readOnly) {
		this(model, title, null, readOnly);
	}

	@SuppressWarnings("unchecked")
	public T getModel() {
		return (T) form.getModel();
	}

	public void exportBinding(final String controlName, final Binding binding) {
		form.exportBinding(controlName, binding);
	}

	public Binding getBinding(final String controlName) {
		return form.getBinding(controlName);
	}

	public Form getForm() {
		return form;
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(1, false);
		composite.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		composite.setLayoutData(gridData);

		basicAddFields(composite);
		return composite;
	}

	protected void basicAddFields(Composite parent) {
		MainWindow.getInstance().currentPreferencePage = this;
		DefaultLayoutFactory.setDefaultGridLayout(parent, getNumColumns());
		this.addFields(parent);
	}

	protected int getNumColumns() {
		return 2;
	}

	protected abstract void addFields(Composite parent);

	private Form form;

	protected boolean readOnly = false;
}