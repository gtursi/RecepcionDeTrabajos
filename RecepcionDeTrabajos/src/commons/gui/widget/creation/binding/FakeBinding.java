package commons.gui.widget.creation.binding;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import commons.gui.form.BasicPublisher;

public class FakeBinding extends BasicPublisher implements Binding {

	public FakeBinding(Object value, String propertyName) {
		super();
		this.value = (value == null) ? " " : value.toString();
		this.propertyName = propertyName;
	}

	public void setEnabled(boolean enabled) {
		if (control != null) {
			control.setEnabled(enabled);
		}
	}

	public boolean getEnabled() {
		return control.getEnabled();
	}

	public String getValue() {
		return value;
	}

	public void bind(final Control aControl) {
		this.control = aControl;
	}

	public void bind(final Label label) {
		control = label;
	}

	public void bind(final Text text) {
		control = text;
	}

	public void bind(final Combo combo, final boolean useEnums) {
		control = combo;
	}

	public void bind(final Button button) {
		SelectionListener listener = new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				String data = Boolean.valueOf(button.getSelection()).toString();
				doProcess(data);
			}
		};

		button.addSelectionListener(listener);
	}

	public void doProcess(String newValue) {
		// do nothing
	}

	public void change(String key, Object aValue) {
		if ((control instanceof Label) && (aValue != null)) {
			((Label) control).setText(aValue.toString());
		}
	}

	public String getId() {
		return propertyName;
	}

	public void setVisible(boolean enabled) {
		if (control != null) {
			control.setVisible(enabled);
		}
	}

	private final String value;

	private Control control;

	private final String propertyName;

}
