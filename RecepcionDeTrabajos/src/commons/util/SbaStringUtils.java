/*
 * $Id: SbaStringUtils.java,v 1.4 2008/05/05 19:51:08 cvstursi Exp $
 */
package commons.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;

/**
 * Utilidades para la manipulación de Strings en forma eficiente, y operaciones que complementan el API de la clase
 * {@link String}.
 * 
 * @author H. Adrián Uribe
 */
public abstract class SbaStringUtils {

	/**
	 * Concatena varios Strings de la forma más eficiente posible.
	 * 
	 * @param strings
	 *            Strings a concatenar.
	 * @return String concatenado.
	 */
	public static String concat(String... strings) {
		int len = 0;
		for (String str : strings) {
			len += (str == null) ? NULL_LENGTH : str.length();
		}

		StringBuffer stringBuilder = new StringBuffer(len);
		synchronized (stringBuilder) {
			for (String str : strings) {
				stringBuilder.append(str);
			}
			return stringBuilder.toString();
		}
	}

	/**
	 * Crea un mensaje a partir de los mensajes recibidos, para poder ser mostrado en un diálogo como un único String
	 * 
	 * @param errorMessages
	 *            Mensajes a mostrar en un diálogo
	 * @return String único a listo para ser mostrado en un diálogo
	 */
	public static String formatErrorMessage(List<String> errorMessages) {
		List<String> strings = new ArrayList<String>();
		for (String errorMessage : errorMessages) {
			strings.add(errorMessage);
			strings.add("\n");
		}
		return concat(strings.toArray(new String[strings.size()]));
	}

	public static String trim(String str) {
		String result = null;
		if (str != null) {
			str = str.trim();
			result = (str.length() == 0) ? null : str;
		}
		return result;
	}

	public static String rtrim(String str) {
		String result = null;
		if (str != null) {
			int last = str.length() - 1;
			int index = last;
			while (index >= 0 && str.charAt(index) <= ' ') {
				--index;
			}

			if (index == last) {
				result = str;
			} else {
				result = (index < 0) ? "" : str.substring(0, index + 1);
			}
		}
		return result;
	}

	/**
	 * Comprime secuencias de 1 ó más de caracteres de espaciado en un carácter de espacio.
	 * 
	 * @param str
	 *            Valor a compactar.
	 * @return Valor compactado.
	 */
	public static String squeeze(String str) {
		String result = null;
		if (str != null) {
			// To-Do: Optimize internal StringBuffer allocation
			result = SQUEEZE_PATTERN.matcher(str).replaceAll(" ");
		}
		return result;
	}

	/**
	 * Comprime secuencias de 1 ó más de caracteres de espaciado en un carácter de espacio, o las suprime en caso de
	 * encontrarse entre 2 caracteres que no sean ambos alfanuméricos.
	 * 
	 * @param str
	 *            Valor a compactar.
	 * @return Valor compactado.
	 */
	public static String squeezeFully(String str) {
		String result = null;
		if (str != null) {
			// To-Do: Optimize internal StringBuffer allocation
			str = SQUEEZE_PATTERN.matcher(str).replaceAll(" ");
			for (Pattern element : SQUEEZE_FULLY_PATTERNS) {
				str = element.matcher(str).replaceAll("$1");
			}
			result = str;
		}
		return result;
	}

	public static String toString(Class clss, Object[] attrValPairs) {
		String className = null;
		int len = 0;
		if (clss != null) {
			className = clss.getName();
			len = className.length() + ATTR_BEGIN.length() + ATTR_END.length();
		}

		int index = attrValPairs.length / 2;
		len += (index - 1) * ATTR_SEPARATOR.length();
		len += index * ATTR_VALUE_SEPARATOR.length();

		Object val;
		for (index = 0; index < attrValPairs.length; index++) {
			val = attrValPairs[index];
			if (val == null) {
				len += NULL_LENGTH;
			} else {
				if (val instanceof Collection) {
					val = toString((Collection) val);
				} else if (val.getClass().isArray()) {
					val = toString((Object[]) val);
				} else {
					val = toString(val);
				}
				len += ((String) val).length();
				attrValPairs[index] = val;
			}
		}

		StringBuffer sBuffer = new StringBuffer(len);
		synchronized (sBuffer) {
			if (className != null) {
				sBuffer.append(className);
				sBuffer.append(ATTR_BEGIN);
			}
			for (index = 0; index < attrValPairs.length; index += 2) {
				if (index != 0) {
					sBuffer.append(ATTR_SEPARATOR);
				}
				sBuffer.append(attrValPairs[index]);
				sBuffer.append(ATTR_VALUE_SEPARATOR);
				sBuffer.append(attrValPairs[index + 1]);
			}
			if (className != null) {
				sBuffer.append(ATTR_END);
			}
			assert sBuffer.length() == len;
			return sBuffer.toString();
		}
	}

	public static String toString(Object[] array) {
		String result = null;
		if (array != null) {
			if (array.length == 0) {
				result = ARR_EMPTY;
			} else {
				int len = ARR_BEGIN.length() + ARR_END.length() + (array.length - 1) * ARR_SEPARATOR.length();

				Object val;
				Object[] strArray = new String[array.length];
				int index;
				for (index = 0; index < array.length; index++) {
					val = array[index];
					if (val == null) {
						len += NULL_LENGTH;
					} else {
						if (val instanceof Collection) {
							val = toString((Collection) val);
						} else if (val.getClass().isArray()) {
							val = toString((Object[]) val);
						} else {
							val = toString(val);
						}
						len += ((String) val).length();
						strArray[index] = val;
					}
				}

				StringBuffer sBuffer = new StringBuffer(len);
				synchronized (sBuffer) {
					sBuffer.append(ARR_BEGIN);
					for (index = 0; index < strArray.length; index++) {
						if (index != 0) {
							sBuffer.append(ARR_SEPARATOR);
						}
						sBuffer.append(strArray[index]);
					}
					sBuffer.append(ARR_END);
					assert sBuffer.length() == len : sBuffer;
					result = sBuffer.toString();
				}
			}
		}
		return result;
	}

	public static String toString(Collection collection) {
		String result = null;
		if (collection != null) {
			if (collection.isEmpty()) {
				result = ARR_EMPTY;
			} else {
				int len = ARR_BEGIN.length() + ARR_END.length() + (collection.size() - 1) * ARR_SEPARATOR.length();

				Object val;
				Object[] strArray = new String[collection.size()];
				Iterator iterator = collection.iterator();
				int index;
				for (index = 0; index < strArray.length; index++) {
					val = iterator.next();
					if (val == null) {
						len += NULL_LENGTH;
					} else {
						if (val instanceof Collection) {
							val = toString((Collection) val);
						} else if (val.getClass().isArray()) {
							val = toString((Object[]) val);
						} else {
							val = toString(val);
						}
						len += ((String) val).length();
						strArray[index] = val;
					}
				}

				StringBuffer sBuffer = new StringBuffer(len);
				synchronized (sBuffer) {
					sBuffer.append(ARR_BEGIN);
					for (index = 0; index < strArray.length; index++) {
						if (index != 0) {
							sBuffer.append(ARR_SEPARATOR);
						}
						sBuffer.append(strArray[index]);
					}
					sBuffer.append(ARR_END);
					assert sBuffer.length() == len;
					result = sBuffer.toString();
				}
			}
		}
		return result;
	}

	/**
	 * @return <code>true</code> si y solo si todos los caracteres del String son imprimibles, esto es, no son
	 *         caracteres de control. Técnicamente, verifica que todos los caracteres estén en el rango [32-175] de
	 *         código ASCII. Algunos ejemplos de invocación del método:
	 *         <ul>
	 *         <li> isPrintable("@?$lajk#") = <code>true</code>
	 *         <li> isPrintable("_¡+}´") = <code>true</code>
	 *         <li> isPrintable(" |"()áéíóúñ") = <code>true</code>
	 *         </ul>
	 */
	public static boolean isPrintable(String str) {
		boolean isPrintable = true;
		if (str != null) {
			char charActual;
			boolean estaEnRangoUno;
			boolean estaEnRangoDos;
			for (int i = 0; isPrintable && i < str.length(); i++) {
				charActual = str.charAt(i);
				estaEnRangoUno = (charActual >= 32) && (charActual <= 126);
				estaEnRangoDos = (charActual >= 161) && (charActual <= 255);
				isPrintable = isPrintable && (estaEnRangoUno || estaEnRangoDos);
			}
		}
		return isPrintable;
	}

	public static boolean containsSpecialCharacter(String str) {
		boolean contains = false;
		if (str != null) {
			for (int i = 0; !contains && i < str.length(); i++) {
				contains = contains || isSpecialCharacter(str.charAt(i));
			}
		}
		return contains;
	}

	/**
	 * @return Devuelve verdadero si y solo si el char dado corresponde con alguna de las siguientes teclas:
	 *         <ul>
	 *         <li><code>BACKSPACE</code></li>
	 *         <li><code>DELETE</code></li>
	 *         <li><code>ALT</code></li>
	 *         <li><code>CTRL</code></li>
	 *         <li><code>CR</code></li>
	 *         <li><code>KEYPAD_CR</code></li>
	 *         </ul>
	 */
	public static boolean isSpecialCharacter(char character) {
		return character == SWT.BS || character == SWT.DEL || character == SWT.ALT || character == SWT.CTRL
				|| character == SWT.CR || character == SWT.KEYPAD_CR;
	}

	public static boolean containsSpecialKey(String str) {
		boolean contains = false;
		if (str != null) {
			for (int i = 0; !contains && i < str.length(); i++) {
				contains = contains || isSpecialKey(str.charAt(i));
			}
		}
		return contains;
	}

	/**
	 * @return Devuelve verdadero si y solo si el <b>keyCode</b> dado corresponde a una <code>flecha a izquierda</code>,
	 *         a una <code>flecha a derecha</code>, a la tecla <code>inicio</code> o a la tecla <code>fin</code>
	 */
	public static boolean isSpecialKey(int keyCode) {
		return keyCode == SWT.ARROW_LEFT || keyCode == SWT.ARROW_RIGHT || keyCode == SWT.HOME || keyCode == SWT.END;
	}

	public static boolean isDateSeparatorCharacter(char character) {
		return character == DATE_SEPARATOR;
	}

	public static String toString(Object obj) {
		return String.valueOf(obj);
	}

	private static final String ATTR_BEGIN = ":{";

	private static final String ATTR_SEPARATOR = "; ";

	private static final String ATTR_VALUE_SEPARATOR = "=";

	private static final String ATTR_END = "}";

	private static final String ARR_BEGIN = "[";

	private static final String ARR_SEPARATOR = ", ";

	private static final String ARR_END = "]";

	private static final String ARR_EMPTY = "[]";

	// Determines the amount of space used by StringBuffer.append(null);
	private static final int NULL_LENGTH = new StringBuffer("").append((String) null).length();

	private static final Pattern SQUEEZE_PATTERN = Pattern.compile("\\s+", Pattern.DOTALL);

	private static final Pattern[] SQUEEZE_FULLY_PATTERNS = { Pattern.compile("(\\W) ", Pattern.DOTALL),
			Pattern.compile(" (\\W)", Pattern.DOTALL), };

	private static final char DATE_SEPARATOR = '/';

}