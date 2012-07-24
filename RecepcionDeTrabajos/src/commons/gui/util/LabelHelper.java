package commons.gui.util;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import recepciondetrabajos.Labels;

public abstract class LabelHelper {

	/**
	 * Crea un campo read-only que consta de un Label y un <i>value</i> de sólo lectura.
	 * 
	 * @param composite
	 *            el composite donde se inserta el campo.
	 * @param value
	 *            el valor explícito que tendrá el campo <i>value</i>
	 * @param labelKey
	 *            la key property para reemplazar en la creación del Label.
	 * @return un campo read-only.
	 */
	public static Label createReadOnlyField(Composite composite, String value, String labelKey) {
		if (!StringUtils.isBlank(labelKey)) {
			createLabel(composite, labelKey);
		}
		return createValue(composite, value);
	}

	/**
	 * Crea un Label seguido de dos puntos (":")
	 * 
	 * @param composite
	 *            el composite donde se inserta el Label.
	 * @param labelKey
	 *            la key property para reemplazar en la creación del Label.
	 * @return un Label.
	 */
	public static Label createLabel(Composite composite, String labelKey) {
		Label label = createLabelBasic(composite, labelKey);
		label.setText(label.getText() + ":");
		return label;
	}

	/**
	 * Crea un Label cuyo texto es obtenido de un properties <br/>
	 * <code>Nota: NO agrega los dos puntos (":")</code>
	 * 
	 * @param composite
	 *            el composite donde se inserta el Label.
	 * @param labelKey
	 *            la key property para reemplazar en la creación del Label.
	 * @return un Label.
	 */
	public static Label createLabelBasic(Composite composite, String labelKey) {
		String labelText = Labels.getProperty(labelKey);
		Label label = new Label(composite, SWT.LEFT);
		label.setText(labelText);
		return label;
	}

	/**
	 * Crea un Label cuyo texto es suministrado <br/>
	 * <code>Nota: NO agrega los dos puntos (":")</code>
	 * 
	 * @param composite
	 *            el composite donde se inserta el Label.
	 * @param label
	 *            el String del Label.
	 * @return un Label.
	 */
	public static Label createDirectLabel(Composite composite, String labelText, boolean bold) {
		Label label = new Label(composite, SWT.LEFT);
		if (bold) {
			label.setFont(PageHelper.getNonEditableFont());
		}
		label.setText(labelText);
		return label;
	}

	/**
	 * Crea un campo del tipo "value", es decir, un simple texto de sólo lectura.
	 * 
	 * @param composite
	 *            el composite donde se inserta el campo.
	 * @param value
	 *            el valor explícito que tendrá el campo.
	 * @return un campo de texto de sólo lectura con el valor especificado por parámetro. Si el
	 *         mismo fuera <code>null</code>, dicho valor se fija en el caracter guión ("-")
	 */
	public static Label createValue(Composite composite, String value) {
		Label label = createValueBasic(composite, value);
		if (StringUtils.isBlank(value)) {
			label.setText("-");
		}
		return label;
	}

	/**
	 * Crea un campo del tipo "value", es decir, un simple texto de sólo lectura.
	 * 
	 * @param composite
	 *            el composite donde se inserta el campo.
	 * @param value
	 *            el valor explícito que tendrá el campo.
	 * @return un campo de texto de sólo lectura con el valor especificado por parámetro. NOTA: Si
	 *         el mismo fuera <code>null</code>, este método crea el "Value" con un String vacío
	 *         (i.e.<code>""</code>). En otro caso, setea el mismo valor recibido por parámetro.
	 */
	public static Label createValueBasic(Composite composite, String value) {
		final Label label = new Label(composite, SWT.LEFT);
		if (composite.getLayoutData() instanceof GridData) {
			label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		label.setFont(PageHelper.getNonEditableFont());
		if (!StringUtils.isBlank(value)) {
			label.setText(value);
		}
		return label;
	}

}