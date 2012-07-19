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
 * $Id: CuitComposite.java,v 1.4 2008/12/10 18:40:43 cvstursi Exp $
 */

package commons.gui.widget.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import commons.gui.GuiStyle;
import commons.gui.util.LabelHelper;
import commons.gui.util.ListenerHelper;
import commons.gui.util.PageHelper;
import commons.util.ClassUtils;
import commons.util.SbaStringUtils;

/**
 * Modela un campo de CUIT / CUIL con las restricciones numéricas necesarias y el layout usual para
 * este tipo de campos.
 * 
 * @author Jonathan Chiocchio
 * @version $Revision: 1.4 $ $Date: 2008/12/10 18:40:43 $
 */
public class CuitComposite extends SimpleComposite {

	public CuitComposite(Composite parent, Object instance, String propertyName, boolean readOnly) {
		super(parent, readOnly, 1);// No usa la cantidad de columnas
		String currentValue = ClassUtils.getValue(instance, propertyName);

		if (readOnly) {
			String formattedValue = formatCuitValue(currentValue);
			LabelHelper.createValue(this, formattedValue);
		} else {
			this.propertyName = propertyName;
			this.instance = instance;

			this.prefixTextBox = this.createTextBox(2);
			createSeparatorLabel();
			this.dniTextBox = this.createTextBox(8);
			createSeparatorLabel();
			this.digitTextBox = this.createTextBox(1);

			fillTextBoxesWith(currentValue);
		}
	}

	/**
	 * Modifica el layout por defecto para poder ubicar los 3 campos en una misma línea.
	 */
	@Override
	protected void applyLayout() {
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		layout.marginLeft = 0;
		this.setLayout(layout);
	}

	private Text createTextBox(int maxLength) {
		// dynamic calculus of textbox size
		final int cantPixels = PageHelper.getCantidadDePixels(maxLength);
		Text textBox = new Text(this, GuiStyle.DEFAULT_TEXTBOX_STYLE);
		textBox.setLayoutData(new RowData(cantPixels, SWT.DEFAULT));
		textBox.setTextLimit(maxLength);
		ListenerHelper.addNumberFieldKeyPressListener(textBox);
		ListenerHelper.addIntegerFieldModifyListener(textBox);
		this.addModifyListener(textBox);
		return textBox;
	}

	private void createSeparatorLabel() {
		LabelHelper.createDirectLabel(this, SEPARATOR, false);
	}

	private void addModifyListener(final Text textBox) {
		ModifyListener modifyListener = new ModifyListener() {

			public void modifyText(ModifyEvent event) {
				// TODO ver cuando el documento tiene 7 cifras si hay que poner un cero a la izq...
				String concatValue = SbaStringUtils.concat(prefixTextBox.getText(),
						dniTextBox.getText(), digitTextBox.getText());
				ClassUtils.setValueByReflection(instance, propertyName, concatValue);

				// changes the focus automatically
				if (textBox.getCharCount() == textBox.getTextLimit()) {
					this.getNextTextBox(textBox).setFocus();
				}
			}

			private Text getNextTextBox(Text textbox) {
				Text nextTextBox = digitTextBox;
				if (textbox.equals(prefixTextBox)) {
					nextTextBox = dniTextBox;
				}
				return nextTextBox;
			}
		};
		textBox.addModifyListener(modifyListener);
	}

	private void fillTextBoxesWith(String currentValue) {
		if (currentValue != null) {
			for (int i = 0; i < currentValue.length(); i++) {
				if (i < 2) {
					this.prefixTextBox.setText(this.prefixTextBox.getText()
							+ currentValue.charAt(i));
				} else if (i < 10) {
					this.dniTextBox.setText(this.dniTextBox.getText() + currentValue.charAt(i));
				} else {
					this.digitTextBox.setText(this.digitTextBox.getText() + currentValue.charAt(i));
				}
			}
		}
	}

	private static String formatCuitValue(String cuitValue) {
		StringBuilder sBuilder = new StringBuilder(13); // 11 caracteres + 2 guiones
		for (int i = 0; i < cuitValue.length(); i++) {
			if ((i == 2) || (i == 10)) {
				sBuilder.append(SEPARATOR);
			}
			sBuilder.append(cuitValue.charAt(i));
		}
		return sBuilder.toString();
	}

	private Object instance;

	private String propertyName;

	private Text prefixTextBox;

	private Text dniTextBox;

	private Text digitTextBox;

	private static final String SEPARATOR = "-";
}