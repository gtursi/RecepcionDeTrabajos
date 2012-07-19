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
 * $Id: ListenerHelper.java,v 1.27 2009/02/21 15:39:46 cvsgalea Exp $
 */

package commons.gui.util;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import recepciondetrabajos.MainWindow;




import commons.gui.validators.Validations;
import commons.util.SbaStringUtils;

/**
 * @author
 * @version $Revision: 1.27 $ $Date: 2009/02/21 15:39:46 $
 */

public abstract class ListenerHelper {

	public static void addFocusListener(Text textBox) {
		FocusListener listener = new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent event) {
				Text text = (Text) event.getSource();
				text.selectAll();
			}

		};
		textBox.addFocusListener(listener);
	}

	/**
	 * Valida que se ingrese un caracter numerico, caso contrario no se concreta el evento
	 * 
	 * @param textBox
	 */
	public static void addNumberFieldKeyPressListener(final Text textBox) {
		KeyListener listener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				boolean isNumeric = org.apache.commons.lang.StringUtils.isNumeric(String.valueOf(event.character));
				if (!isNumeric && !SbaStringUtils.isSpecialCharacter(event.character)
						&& !SbaStringUtils.isSpecialKey(event.keyCode)) {
					event.doit = false;
				}
			}
		};
		textBox.addKeyListener(listener);
	}

	public static void addNumberFieldModifyListener(final Text textBox) {
		ModifyListener listener = new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				String text = org.apache.commons.lang.StringUtils.trimToEmpty(textBox.getText());
				try {
					new BigDecimal(text);
					textBox.setToolTipText(null);
					textBox.setForeground(PageHelper.getValidColor());
				} catch (NumberFormatException ex) {
					if (org.apache.commons.lang.StringUtils.isNotBlank(text)) {
						textBox.setForeground(PageHelper.getInvalidColor());
						textBox.setToolTipText("Número inválido");
					}
				}
			}
		};
		textBox.addModifyListener(listener);
	}

	public static void addIntegerFieldModifyListener(final Text textBox) {
		ModifyListener listener = new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				String text = org.apache.commons.lang.StringUtils.trimToEmpty(textBox.getText());
				try {
					Integer.parseInt(text);
					textBox.setToolTipText(null);
					textBox.setForeground(PageHelper.getValidColor());
				} catch (NumberFormatException ex) {
					if (org.apache.commons.lang.StringUtils.isNotBlank(text)) {
						textBox.setForeground(PageHelper.getInvalidColor());
						textBox.setToolTipText("Número inválido");
					}
				}
			}
		};
		textBox.addModifyListener(listener);
	}

	public static <T> void addIntegerFieldFocusListener(Text textBox) {
		FocusListener focusListener = new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent event) {
				Text textField = (Text) event.getSource();
				String text = (textField).getText();
				Integer num;
				try {
					num = Integer.valueOf(text);
					textField.setText(num.toString());
					textField.setForeground(PageHelper.getValidColor());
					textField.setToolTipText(null);
				} catch (NumberFormatException exc) {
					if (org.apache.commons.lang.StringUtils.isNotBlank(text)) {
						textField.setForeground(PageHelper.getInvalidColor());
						textField.setToolTipText("Número inválido");
					}
				}
			}
		};
		textBox.addFocusListener(focusListener);
	}

	public static void addIntegerRangeValidationListener(final Text textBox, final int infimo, final int supremo,
			final PreferencePage page) {
		textBox.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent event) {
				final List<String> mensajesDeValidacion = Validations.rango(textBox.getText(), infimo, supremo);
				boolean vacio = StringUtils.isBlank(textBox.getText());
				boolean fueraDeRango = !mensajesDeValidacion.isEmpty();
				if (!vacio && fueraDeRango) {
					page.setErrorMessage(mensajesDeValidacion.get(0));
					textBox.setToolTipText(mensajesDeValidacion.get(0));
					textBox.setForeground(PageHelper.getInvalidColor());
				}
			}

			public void focusLost(FocusEvent event) {
				page.setErrorMessage(null);
				textBox.setToolTipText(null);
				textBox.setForeground(PageHelper.getValidColor());
			}
		});

		textBox.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				boolean vacio = StringUtils.isBlank(textBox.getText());
				final List<String> mensajesDeValidacion = Validations.rango(textBox.getText(), infimo, supremo);
				boolean fueraDeRango = !mensajesDeValidacion.isEmpty();
				if (!vacio && fueraDeRango) {
					page.setErrorMessage(mensajesDeValidacion.get(0));
					textBox.setToolTipText(mensajesDeValidacion.get(0));
					textBox.setForeground(PageHelper.getInvalidColor());
				} else {
					page.setErrorMessage(null);
					textBox.setToolTipText(null);
					textBox.setForeground(PageHelper.getValidColor());
				}
			}
		});
	}

	public static void addIntegerMayorValidationListener(final Text textBox, final int infimo, final PreferencePage page) {
		textBox.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent event) {
				final List<String> mensajesDeValidacion = Validations.mayor(textBox.getText(), infimo);
				boolean vacio = StringUtils.isBlank(textBox.getText());
				boolean fueraDeRango = !mensajesDeValidacion.isEmpty();
				if (!vacio && fueraDeRango) {
					page.setErrorMessage(mensajesDeValidacion.get(0));
					textBox.setToolTipText(mensajesDeValidacion.get(0));
					textBox.setForeground(PageHelper.getInvalidColor());
				}
			}

			public void focusLost(FocusEvent event) {
				page.setErrorMessage(null);
				textBox.setToolTipText(null);
				textBox.setForeground(PageHelper.getValidColor());
			}
		});

		textBox.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				boolean vacio = StringUtils.isBlank(textBox.getText());
				final List<String> mensajesDeValidacion = Validations.mayor(textBox.getText(), infimo);
				boolean fueraDeRango = !mensajesDeValidacion.isEmpty();
				if (!vacio && fueraDeRango) {
					page.setErrorMessage(mensajesDeValidacion.get(0));
					textBox.setToolTipText(mensajesDeValidacion.get(0));
					textBox.setForeground(PageHelper.getInvalidColor());
				} else {
					page.setErrorMessage(null);
					textBox.setToolTipText(null);
					textBox.setForeground(PageHelper.getValidColor());
				}
			}
		});
	}

	public static Text addMultilineFieldTabListener(Text textBox) {
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				if (event.detail == SWT.TRAVERSE_TAB_NEXT) {
					event.doit = true;
				}
			}
		};
		textBox.addListener(SWT.Traverse, listener);
		return textBox;
	}

	public static void addNotAsciiPrintableCharacterListener(final Text textBox) {
		final String errMsg = "El campo no puede contener caracteres no imprimibles";
		ModifyListener listener = new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				boolean condicion = SbaStringUtils.isPrintable(textBox.getText())
						|| SbaStringUtils.containsSpecialCharacter(textBox.getText())
						|| SbaStringUtils.containsSpecialKey(textBox.getText());

				if (condicion) {
					textBox.setToolTipText(null);
					textBox.setForeground(PageHelper.getValidColor());
				} else {
					textBox.setForeground(PageHelper.getInvalidColor());
					textBox.setToolTipText(errMsg);
				}
			}
		};
		textBox.addModifyListener(listener);
	}

	public static void addNotNullValidationListener(final Text textBox, final PreferencePage page) {
		final String errMsg = "El campo no puede ser nulo";
		textBox.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent event) {
				if (StringUtils.isBlank(textBox.getText())) {
					page.setMessage(errMsg, IMessageProvider.INFORMATION);
					textBox.setToolTipText(errMsg);
				}
			}

			public void focusLost(FocusEvent event) {
				page.setMessage(null);
				textBox.setToolTipText(null);
			}
		});

		textBox.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				if (StringUtils.isBlank(textBox.getText())) {
					page.setMessage(errMsg, IMessageProvider.INFORMATION);
					textBox.setToolTipText(errMsg);
				} else {
					page.setMessage(null);
					textBox.setToolTipText(null);
				}
			}
		});
	}

	public static void addEmailValidationListener(final Text textBox, final PreferencePage page) {
		textBox.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent event) {
				List<String> result = Validations.email(textBox.getText());
				if (!result.isEmpty()) {
					if (page != null) {
						page.setMessage(result.get(0), IMessageProvider.INFORMATION);
					}
					textBox.setToolTipText(result.get(0));
					textBox.setForeground(PageHelper.getInvalidColor());
				}
			}

			public void focusLost(FocusEvent event) {
				if (page != null) {
					page.setMessage(null);
				}
				List<String> result = Validations.email(textBox.getText());
				if (result.isEmpty()) {
					textBox.setForeground(PageHelper.getValidColor());
					textBox.setToolTipText(null);
				}
			}
		});

		textBox.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				List<String> result = Validations.email(textBox.getText());
				if (result.isEmpty()) {
					if (page != null) {
						page.setMessage(null);
					}
					textBox.setForeground(PageHelper.getValidColor());
					textBox.setToolTipText(null);
				} else {
					if (page != null) {
						page.setMessage(result.get(0), IMessageProvider.INFORMATION);
					}
					textBox.setToolTipText(result.get(0));
					textBox.setForeground(PageHelper.getInvalidColor());
				}
			}
		});
	}

	public static void addDateKeyListener(final Text textBox) {
		KeyListener keyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				boolean isNumeric = org.apache.commons.lang.StringUtils.isNumeric(String.valueOf(event.character));
				if (!isNumeric && !SbaStringUtils.isDateSeparatorCharacter(event.character)
						&& !SbaStringUtils.isSpecialKey(event.keyCode)
						&& !SbaStringUtils.isSpecialCharacter(event.character)) {
					event.doit = false;
				}
			}
		};
		textBox.addKeyListener(keyListener);
	}

	public static void addDateModifyListener(final Text textBox) {
		ModifyListener modifyListener = new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				String message = Validations.validateDate(textBox.getText());
				if (message != null) {
					textBox.setForeground(PageHelper.getInvalidColor());
					textBox.setToolTipText(message);
				} else {
					textBox.setForeground(PageHelper.getValidColor());
					textBox.setToolTipText(null);
				}
			}
		};
		textBox.addModifyListener(modifyListener);
	}

	public static void addMsgInfoListener(Text textBox, final String Msg) {
		final PreferencePage page = MainWindow.getInstance().currentPreferencePage;
		textBox.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent event) {
				page.setMessage(Msg, IMessageProvider.INFORMATION);
			}

			public void focusLost(FocusEvent event) {
				page.setMessage(null);
			}
		});
	}

	public static void addNormalizerFormatValidationListener(final Text textBox) {
		textBox.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				String temp = normalizarCadena(textBox.getText());
				if (!textBox.getText().equals(temp)) {
					textBox.setText(temp);
					textBox.setSelection(textBox.getText().length()); // hace que el cursor se coloque al final del
																		// texto ingresado
				}
			}
		});
	}

	public static String normalizarCadena(String cadena) {
		String cadenaNormalizada = cadena;
		cadenaNormalizada = cadenaNormalizada.replaceAll("[áàâãä]", "a");
		cadenaNormalizada = cadenaNormalizada.replaceAll("[ÁÀÂÃÄ]", "A");
		cadenaNormalizada = cadenaNormalizada.replaceAll("[éèêë]", "e");
		cadenaNormalizada = cadenaNormalizada.replaceAll("[ÉÈÊË]", "E");
		cadenaNormalizada = cadenaNormalizada.replaceAll("[íìîï]", "i");
		cadenaNormalizada = cadenaNormalizada.replaceAll("[ÍÌÎÏ]", "I");
		cadenaNormalizada = cadenaNormalizada.replaceAll("[óòôõö]", "o");
		cadenaNormalizada = cadenaNormalizada.replaceAll("[ÓÒÔÕÖ]", "O");
		cadenaNormalizada = cadenaNormalizada.replaceAll("[úùûü]", "u");
		cadenaNormalizada = cadenaNormalizada.replaceAll("[ÚÙÛÜ]", "U");
		cadenaNormalizada = cadenaNormalizada.replaceAll("ç", "c");
		cadenaNormalizada = cadenaNormalizada.replaceAll("Ç", "C");
		cadenaNormalizada = cadenaNormalizada.replaceAll("ñ", "n");
		cadenaNormalizada = cadenaNormalizada.replaceAll("Ñ", "N");

		// se utilizó esta función y no sun.text.Normalizer, para evitar problemas de compatibilidad
		// para java 1.5 -> cadenaNormalizada = Normalizer.normalize(cadenaNormalizada, Normalizer.DECOMP, 0).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
		// para java 1.6 -> cadenaNormalizada = Normalizer.normalize(cadenaNormalizada, Form.NFD			).replaceAll("[^\\p{ASCII}]", "").toUpperCase();

		return cadenaNormalizada.toUpperCase();

	}
}