package commons.gui.widget.creation;

import org.eclipse.swt.widgets.Text;

import recepciondetrabajos.MainWindow;

import commons.gui.util.ListenerHelper;

public enum TextFieldListenerType {

	NOT_NULL_LISTENER {

		@Override
		public void addListeners(Text textBox) {
			ListenerHelper.addNotNullValidationListener(textBox,
					MainWindow.getInstance().currentPreferencePage);
		}
	},
	EMAIL_LISTENER {

		@Override
		public void addListeners(Text textBox) {
			ListenerHelper.addEmailValidationListener(textBox,
					MainWindow.getInstance().currentPreferencePage);
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
			// TODO: implementar NOT_NUMBER_FIELD_LISTENER!
			throw new RuntimeException("implementar NOT_NUMBER_FIELD_LISTENER!");
		}
	},
	NORMALIZER_FIELD_LISTENER {

		@Override
		public void addListeners(Text textBox) {
			ListenerHelper.addNormalizerFormatValidationListener(textBox);
			// textBox.addKeyListener(new KeyAdapter)
		}
	};

	public abstract void addListeners(Text textBox);

}
