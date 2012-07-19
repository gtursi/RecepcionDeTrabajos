/*
 * Licencia de Caja de Valores S.A., Versi�n 1.0
 *
 * Copyright (c) 2006 Caja de Valores S.A.
 * 25 de Mayo 362, Ciudad Aut�noma de Buenos Aires, Rep�blica Argentina
 * Todos los derechos reservados.
 *
 * Este software es informaci�n confidencial y propietaria de Caja de Valores S.A. ("Informaci�n
 * Confidencial"). Usted no divulgar� tal Informaci�n Confidencial y la usar� solamente de acuerdo a
 * los t�rminos del acuerdo de licencia que posee con Caja de Valores S.A.
 */

/*
 * $Id: LabelHelper.java,v 1.19 2009/09/16 20:22:56 cvsgalea Exp $
 */
package commons.gui.util;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import recepciondetrabajos.Labels;




/**
 * @author Gabriel Tursi
 * @version $Revision: 1.19 $ $Date: 2009/09/16 20:22:56 $
 */
public abstract class LabelHelper {

	/**
	 * Crea un campo read-only que consta de un Label y un <i>value</i> de s�lo lectura.
	 * @param composite
	 *            el composite donde se inserta el campo.
	 * @param value
	 *            el valor expl�cito que tendr� el campo <i>value</i>
	 * @param labelKey
	 *            la key property para reemplazar en la creaci�n del Label.
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
	 * @param composite
	 *            el composite donde se inserta el Label.
	 * @param labelKey
	 *            la key property para reemplazar en la creaci�n del Label.
	 * @return un Label.
	 */
	public static Label createLabel(Composite composite, String labelKey) {
		Label label = createLabelBasic(composite, labelKey);
		label.setText(label.getText() + ":");
		return label;
	}


	/**
	 * Crea un Label cuyo texto es obtenido de un properties
	 * <br/>
	 * <code>Nota: NO agrega los dos puntos (":")</code>
	 * @param composite
	 *            el composite donde se inserta el Label.
	 * @param labelKey
	 *            la key property para reemplazar en la creaci�n del Label.
	 * @return un Label.
	 */
	public static Label createLabelBasic(Composite composite, String labelKey) {
		String labelText = Labels.getProperty(labelKey);
		Label label = new Label(composite, SWT.LEFT);
		label.setText(labelText);
		return label;
	}

	/**
	 * Crea un Label cuyo texto es suministrado
	 * <br/>
	 * <code>Nota: NO agrega los dos puntos (":")</code>
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
	 * Crea un campo del tipo "value", es decir, un simple texto de s�lo lectura.
	 * @param composite
	 *            el composite donde se inserta el campo.
	 * @param value
	 *            el valor expl�cito que tendr� el campo.
	 * @return un campo de texto de s�lo lectura con el valor especificado por par�metro. Si el
	 *         mismo fuera <code>null</code>, dicho valor se fija en el caracter gui�n ("-")
	 */
	public static Label createValue(Composite composite, String value) {
		Label label = createValueBasic(composite, value);
		if (StringUtils.isBlank(value)) {
			label.setText("-");
		}
		return label;
	}

	/**
	 * Crea un campo del tipo "value", es decir, un simple texto de s�lo lectura.
	 * @param composite
	 *            el composite donde se inserta el campo.
	 * @param value
	 *            el valor expl�cito que tendr� el campo.
	 * @return un campo de texto de s�lo lectura con el valor especificado por par�metro. NOTA: Si
	 *         el mismo fuera <code>null</code>, este m�todo crea el "Value" con un String vac�o
	 *         (i.e.<code>""</code>). En otro caso, setea el mismo valor recibido por par�metro.
	 */
	public static Label createValueBasic(Composite composite, String value) {
		final Label label = new Label(composite, SWT.LEFT);
		if(composite.getLayoutData() instanceof GridData){
			label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		label.setFont(PageHelper.getNonEditableFont());
		if (!StringUtils.isBlank(value)) {
			label.setText(value);
		}
		return label;
	}

}