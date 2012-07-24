package commons.gui.util;

import java.lang.reflect.Field;
import java.util.logging.Level;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import commons.gui.GuiStyle;
import commons.gui.widget.composite.SimpleComposite;
import commons.gui.widget.creation.TextFieldListenerType;
import commons.gui.widget.creation.binding.BindingInfo;
import commons.gui.widget.creation.metainfo.BooleanFieldMetainfo;
import commons.gui.widget.creation.metainfo.StringValueMetaInfo;
import commons.gui.widget.creation.metainfo.TextFieldMetainfo;
import commons.logging.AppLogger;
import commons.util.ClassUtils;

public abstract class TextFieldHelper {

	private static final int MULTILINE_ROWS = 3;

	public static <T> Control createNumeroDocumentoField(Composite composite, String labelKey,
			StringValueMetaInfo valueMetaInfo, boolean readOnly) {
		Control result;
		if (readOnly) {
			String value = formatDniValue(valueMetaInfo.getValue());
			result = LabelHelper.createReadOnlyField(composite, value, labelKey);
		} else {
			TextFieldMetainfo metainfo = TextFieldMetainfo.create(composite, labelKey,
					valueMetaInfo, readOnly, 8, false);
			result = createTextField(metainfo);
		}
		return result;
	}

	public static <T> Button createBooleanField(BooleanFieldMetainfo metainfo) {
		Composite booleanFieldComposite = new SimpleComposite(metainfo.composite,
				metainfo.readOnly, 2);
		final Button button = new Button(booleanFieldComposite, SWT.CHECK);
		if (metainfo.bindingInfo != null) {
			button.setSelection(metainfo.bindingInfo.getValue());
		}
		button.setEnabled(!metainfo.readOnly);
		LabelHelper.createLabelBasic(booleanFieldComposite, metainfo.labelKey);
		setBooleanReflectionInfo(metainfo, button);
		return button;
	}

	public static <T> Button createBooleanFieldB(BooleanFieldMetainfo metainfo) {
		assert metainfo.binding != null;
		Composite booleanFieldComposite = new SimpleComposite(metainfo.composite,
				metainfo.readOnly, 2);
		final Button button = new Button(booleanFieldComposite, SWT.CHECK);
		boolean value = metainfo.binding.getValue().equals(Boolean.TRUE.toString());
		button.setSelection(value);
		button.setEnabled(!metainfo.readOnly);
		if (metainfo.labelKey != null) {
			LabelHelper.createLabelBasic(booleanFieldComposite, metainfo.labelKey);
		}
		if (metainfo.label != null) {
			LabelHelper.createDirectLabel(booleanFieldComposite, metainfo.label, metainfo.readOnly
					&& value);
		}
		metainfo.binding.bind(button);

		return button;
	}

	// TODO ver si se puede unificar algo con setReflectionInfo
	private static void setBooleanReflectionInfo(BooleanFieldMetainfo metainfo, final Button button) {
		final Object model = metainfo.bindingInfo.getModel();
		final String propertyName = metainfo.bindingInfo.getPropertyName();
		SelectionListener listener = new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				Boolean value = Boolean.valueOf(button.getSelection());
				if (model != null) {
					ClassUtils.setValueByReflection(model, propertyName, value);
				}
			}
		};
		button.addSelectionListener(listener);

		if (model != null) {
			Field field = ClassUtils.getField(model.getClass(), propertyName);
			try {
				if (field != null) {
					button.setSelection(field.getBoolean(model));
				}
			} catch (Exception exc) {
				AppLogger.getLogger().log(Level.SEVERE, null, exc);
			}
		}
	}

	/**
	 * Crea un campo de texto a partir de la meta informción brindada.
	 * 
	 * @param metainfo
	 *            : meta información de un campo de texto
	 * @return el campo de texto creado, puede ser un <code>Label</code> si es de solo lectura o un
	 *         <code>Text</code> si es de lectura escritura.
	 * 
	 * @see BindingInfo
	 */
	public static <T> Control createTextField(final TextFieldMetainfo metainfo) {
		Control result;
		if (metainfo.readOnly && !metainfo.multiline) {
			String value = metainfo.bindingInfo.getValue();
			if (value == null) {
				value = "";
			}
			result = LabelHelper.createReadOnlyField(metainfo.composite, value, metainfo.labelKey);
		} else {
			LabelHelper.createLabel(metainfo.composite, metainfo.labelKey);
			final Text textBox = createTextBox(metainfo);

			if (metainfo.bindingInfo != null) {
				if (metainfo.bindingInfo.getModel() == null) {
					if (metainfo.bindingInfo.getValue() != null) {
						textBox.setText(metainfo.bindingInfo.getValue());
					}
				} else {
					setReflectionInfo(metainfo.bindingInfo, textBox);
				}
			}

			addListeners(textBox, metainfo);
			result = textBox;
		}
		metainfo.restoreDefaults();
		return result;
	}

	/**
	 * Crea un campo de texto a partir de la meta informción brindada.
	 * 
	 * @param metainfo
	 *            : meta información de un campo de texto
	 * @return el campo de texto creado, puede ser un <code>Label</code> si es de solo lectura o un
	 *         <code>Text</code> si es de lectura escritura.
	 * 
	 * @see Binding
	 */
	public static <T> Control createTextFieldB(final TextFieldMetainfo metainfo) {
		Control result;
		if (metainfo.readOnly && !metainfo.multiline) {
			String value = metainfo.binding.getValue();
			result = LabelHelper.createReadOnlyField(metainfo.composite, value, metainfo.labelKey);
			metainfo.binding.bind((Label) result);
		} else {
			if ((metainfo.label != null) && !metainfo.label.equals(NULLSTRING)) {
				LabelHelper.createDirectLabel(metainfo.composite, metainfo.label, false);
			}
			if (metainfo.labelKey != null) {
				LabelHelper.createLabel(metainfo.composite, metainfo.labelKey);
			}
			final Text textBox = createTextBox(metainfo);

			metainfo.binding.bind(textBox);

			addListeners(textBox, metainfo);
			result = textBox;
		}
		metainfo.restoreDefaults();
		return result;
	}

	private static Text createTextBox(final TextFieldMetainfo metainfo) {
		int style = GuiStyle.DEFAULT_TEXTBOX_STYLE;
		int horizontalAlignment = SWT.BEGINNING;
		int verticalAlignment = SWT.BEGINNING;
		boolean grabExcessHorizontalSpace = true;
		boolean grabExcessVerticalSpace = false;
		int widthHint = SWT.DEFAULT;

		if (metainfo.password) {
			style = GuiStyle.PASSWORD_TEXTBOX_STYLE;
		}

		if (metainfo.multiline) {
			style = GuiStyle.DEFAULT_MULTILINE_TEXTBOX_STYLE;
			horizontalAlignment = SWT.FILL;
			verticalAlignment = SWT.FILL;
			grabExcessVerticalSpace = true;
		}

		final Text textBox = new Text(metainfo.composite, style);

		if (metainfo.maxLength == null) {
			if (metainfo.visibleSize != null) {
				widthHint = PageHelper.getCantidadDePixels(metainfo.visibleSize);
			}
		} else {
			textBox.setTextLimit(metainfo.maxLength);
			if ((metainfo.visibleSize != null) && (metainfo.visibleSize < metainfo.maxLength)) {
				widthHint = PageHelper.getCantidadDePixels(metainfo.visibleSize);
			} else {
				widthHint = PageHelper.getCantidadDePixels(metainfo.maxLength);
			}
		}

		if (metainfo.composite.getLayoutData() instanceof GridData) {
			GridData gridData = new GridData(horizontalAlignment, verticalAlignment,
					grabExcessHorizontalSpace, grabExcessVerticalSpace);
			gridData.widthHint = widthHint;
			gridData.minimumHeight = PageHelper.getMinimunCharHeight();
			if (metainfo.multiline) {
				gridData.minimumHeight = PageHelper.getHeightPerChars(MULTILINE_ROWS);
			}
			textBox.setLayoutData(gridData);
		} else {
			AppLogger.getLogger().warning(
					"No se ajustará el tamaño del campo para el atributo " + metainfo.labelKey
							+ " ya que solo esta implementado para layouts del tipo GridData");
		}

		if (metainfo.readOnly) {
			textBox.setFont(PageHelper.getNonEditableFont());
			textBox.setEditable(false);
		}
		return textBox;
	}

	private static void addListeners(Text textBox, TextFieldMetainfo metainfo) {
		addCommonListeners(textBox, metainfo);
		if (metainfo.listeners != null) {
			for (TextFieldListenerType listener : metainfo.listeners) {
				listener.addListeners(textBox);
			}
		}
	}

	private static void addCommonListeners(Text textBox, TextFieldMetainfo metainfo) {
		// Procesar los eventos que genera la tecla "Tab" en los campos de texto multilíneas
		if (metainfo.multiline) {
			ListenerHelper.addMultilineFieldTabListener(textBox);
		}
		ListenerHelper.addFocusListener(textBox);
		ListenerHelper.addNotAsciiPrintableCharacterListener(textBox);
	}

	private static void setReflectionInfo(final StringValueMetaInfo valueMetaInfo,
			final Text textBox) {
		textBox.setData(valueMetaInfo.getModel());

		ModifyListener modifyListener = new ModifyListener() {

			public void modifyText(ModifyEvent event) {
				if (textBox.getData() != null) {
					ClassUtils.setValueByReflection(textBox.getData(),
							valueMetaInfo.getPropertyName(), textBox.getText());
				}
			}
		};
		textBox.addModifyListener(modifyListener);

		if (textBox.getData() != null) {
			textBox.setText(ClassUtils.obtenerValor(textBox.getData(),
					valueMetaInfo.getPropertyName()));
		}
	}

	public static String formatDniValue(String dniValue) {
		String result = NULLSTRING;
		int cant = 0;
		for (int i = dniValue.length() - 1; i >= 0; i--) {
			if (cant == 3) {
				result = "." + result;
				cant = 0;
			}
			cant++;
			result = dniValue.charAt(i) + result;
		}
		return result;
	}

	private static final String NULLSTRING = "";
}