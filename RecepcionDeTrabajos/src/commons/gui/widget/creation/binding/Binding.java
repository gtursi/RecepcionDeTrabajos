package commons.gui.widget.creation.binding;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import commons.gui.form.Publisher;
import commons.gui.form.Subscriber;

public interface Binding extends Publisher, Subscriber {

	boolean getEnabled();

	void setEnabled(boolean enabled);

	void setVisible(boolean enabled);

	String getValue();

	void bind(final Control control);

	void bind(final Label label);

	void bind(final Text text);

	void bind(final Combo combo, final boolean useEnums);

	void bind(final Button button);

	String EMPTY_ITEM = "";
}