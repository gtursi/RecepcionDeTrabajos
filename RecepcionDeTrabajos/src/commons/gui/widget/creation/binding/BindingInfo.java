/*
 * $Id: BindingInfo.java,v 1.18 2009/09/16 19:16:12 cvsgalea Exp $
 *
 * Copyright (c) 2003 Caja de Valores S.A.
 * 25 de Mayo 362, Buenos Aires, República Argentina.
 * Todos los derechos reservados.
 *
 * Este software es información confidencial y propietaria de Caja de Valores
 * S.A. ("Información Confidencial"). Usted no divulgará tal Información
 * Confidencial y solamente la usará conforme a los terminos del Acuerdo que Ud.
 * posee con Caja de Valores S.A.
 */

package commons.gui.widget.creation.binding;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import commons.gui.form.BasicPublisher;
import commons.gui.form.Subscriber;
import commons.util.ClassUtils;

/**
 * @author Rodrigo Alonso
 * @version $Revision: 1.18 $ $Date: 2009/09/16 19:16:12 $
 */

public class BindingInfo extends BasicPublisher implements Binding {

	public BindingInfo(Object model, String propertyName) {
		super();
		if (StringUtils.isBlank(propertyName)) {
			throw new IllegalArgumentException("El atributo propertyName no puede ser nulo");
		}
		this.propertyName = propertyName;
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see commons.gui.widget.creation.binding.Binding#getId()
	 */
	public String getId() {
		return propertyName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see commons.gui.widget.creation.binding.Binding#setEnabled(boolean)
	 */
	public void setEnabled(boolean enabled) {
		if (control != null) {
			control.setEnabled(enabled);
		}
	}

	public boolean getEnabled() {
		return control.getEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see commons.gui.widget.creation.binding.Binding#getValue()
	 */
	public String getValue() {
		String value = "";
		if (model != null) {
			value = ClassUtils.getValue(model, propertyName);
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see commons.gui.widget.creation.binding.Binding#getModel()
	 */
	public Object getModel() {
		return this.model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see commons.gui.widget.creation.binding.Binding#getPropertyName()
	 */
	public String getPropertyName() {
		return this.propertyName;
	}

	public void bind(final Control aControl) {
		this.control = aControl;
	}

	public void bind(final Label label) {
		control = label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see commons.gui.widget.creation.binding.Binding#bind(org.eclipse.swt.widgets.Text)
	 */
	public void bind(final Text text) {
		control = text;

		if (getModel() == null) {
			text.setText(getValue());
		} else {
			text.setData(getModel());

			/*
			 * Antes de agregar el Listener debe setearse el valor al TextField, sino se disparan
			 * las eventuales reaglas asociadas al TextField.
			 */
			text.setText(ClassUtils.obtenerValor(getModel(), getPropertyName()));

			ModifyListener modifyListener = new ModifyListener() {
				public void modifyText(ModifyEvent event) {
					cycleAvoidanceFlag = true;
					change(propertyName, text.getText());
					cycleAvoidanceFlag = false;
				}
			};

			text.addModifyListener(modifyListener);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see commons.gui.widget.creation.binding.Binding#bind(org.eclipse.swt.widgets.Combo, boolean)
	 */
	public void bind(final Combo combo, final boolean useEnums) {
		control = combo;

		ModifyListener modifyListener = new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				cycleAvoidanceFlag = true;
				Object data = combo.getData(combo.getText());
				if (useEnums && data == null) {
					data = EMPTY_ITEM;
				}
				change(propertyName, data);
				cycleAvoidanceFlag = false;
			}
		};

		combo.addModifyListener(modifyListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see commons.gui.widget.creation.binding.Binding#bind(org.eclipse.swt.widgets.Button)
	 */
	public void bind(final Button button) {
		control = button;

		SelectionListener listener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				cycleAvoidanceFlag = true;
				Boolean data = Boolean.valueOf(button.getSelection());
				change(propertyName, data);
				cycleAvoidanceFlag = false;
			}
		};

		button.addSelectionListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see commons.gui.widget.creation.binding.Binding#change(java.lang.String, java.lang.Object)
	 */
	public void change(String key, Object value) {
		ClassUtils.setValueByReflection(getModel(), getPropertyName(), value);
		for (Subscriber s : subscribers) {
			s.change(propertyName, value);
		}

		if (!cycleAvoidanceFlag && control != null) {
			if (control instanceof Text) {
				((Text) control).setText(ClassUtils.obtenerValor(getModel(), getPropertyName()));
			} else if (control instanceof Combo) {
				((Combo) control).setText(ClassUtils.obtenerValor(getModel(), getPropertyName()));
			} else if (control instanceof Button) {
				ClassUtils.obtenerValor(getModel(), getPropertyName(), (Button) control);
			} else if (control instanceof Label) {
				((Label) control).setText(value.toString());
			}
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see commons.gui.widget.creation.binding.Binding#setVisible(boolean)
	 */
	public void setVisible(boolean enabled) {
		if (control != null) {
			control.setVisible(enabled);
		}
	}

	private boolean cycleAvoidanceFlag = false;

	private Object model;

	private final String propertyName;

	private Control control;

}
