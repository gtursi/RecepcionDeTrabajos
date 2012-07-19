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
 * $Id: TextFieldListenerType.java,v 1.11 2009/01/22 12:31:16 cvsgalea Exp $
 */
package commons.gui.widget.creation;

import org.eclipse.swt.widgets.Text;

import recepciondetrabajos.MainWindow;




import commons.gui.util.ListenerHelper;

/**
 *
 * @author Gabriel Tursi
 * @version $Revision: 1.11 $ $Date: 2009/01/22 12:31:16 $
 */
public enum TextFieldListenerType {

	NOT_NULL_LISTENER {
		@Override
		public void addListeners(Text textBox) {
			ListenerHelper.addNotNullValidationListener(textBox, MainWindow.getInstance()
					.currentPreferencePage);
		}
	},
	EMAIL_LISTENER {
		@Override
		public void addListeners(Text textBox) {
			ListenerHelper.addEmailValidationListener(textBox, MainWindow.getInstance()
					.currentPreferencePage);
		}
	},
	INTEGER_FIELD_LISTENER {
		@Override
		public void addListeners(Text textBox) {
			ListenerHelper.addNumberFieldKeyPressListener(textBox);
			ListenerHelper.addIntegerFieldModifyListener(textBox);
			ListenerHelper.addIntegerFieldFocusListener(textBox);
		}
	},
	NUMBER_FIELD_LISTENER {
		@Override
		public void addListeners(Text textBox) {
			ListenerHelper.addNumberFieldKeyPressListener(textBox);
			ListenerHelper.addNumberFieldModifyListener(textBox);
		}
	},
	NOT_NUMBER_FIELD_LISTENER {
		@Override
		public void addListeners(Text textBox) {
			//TODO: implementar NOT_NUMBER_FIELD_LISTENER!
			throw new RuntimeException("implementar NOT_NUMBER_FIELD_LISTENER!");
		}
	},
	NORMALIZER_FIELD_LISTENER {
		@Override
		public void addListeners(Text textBox) {
			ListenerHelper.addNormalizerFormatValidationListener(textBox);
			//textBox.addKeyListener(new KeyAdapter)
		}
	};

	public abstract void addListeners(Text textBox);

}
