package commons.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

import commons.gui.util.ComboHelper;
import commons.gui.widget.dialog.SWTCalendarDialog;
import commons.logging.AppLogger;

public abstract class ClassUtils {

	public static Field[] getFields(Class clazz) {
		Field[] fields = sm_fields.get(clazz);
		if (fields == null) {
			List<Field> fieldsList = doGetFields(clazz);
			int size = fieldsList.size();
			fields = new Field[size];
			fieldsList.toArray(fields);
			sm_fields.put(clazz, fields);
		}
		return fields;
	}

	public static Field getField(Class clazz, String fieldName) {
		Field result = null;
		for (Field field : getFields(clazz)) {
			if (fieldName.equals(field.getName())) {
				result = field;
				break;
			}
		}
		return result;
	}

	public static <T> Object invokeMethod(T instance, String methodName, Object[] args) {
		Object result;
		try {
			Method method = getMethod(instance.getClass(), methodName);
			method.setAccessible(true);
			result = method.invoke(instance, args);
		} catch (Exception e) {
			AppLogger.getLogger().log(Level.SEVERE, "No se pudo invocar el metodo solicitado");
			AppLogger.getLogger().log(Level.SEVERE, e.getMessage());
			result = null;
		}

		return result;
	}

	/**
	 * Retorna el método indicado por los parámetros.
	 * 
	 * @param clazz
	 *            la clase a la cual pedirle el método.
	 * @param methodName
	 *            el nombre del método a obtener.
	 * @return una instancia de la clase <code>Method</code> que representa al método que se quiere
	 *         obtener, ó <code>null</code> en caso que el método no exista o no pueda ser accedido.
	 */
	public static Method getMethod(Class clazz, String methodName) {
		Method result = null;
		result = getPublicMethod(clazz, methodName);
		if (result == null) {
			result = getPrivateMethod(clazz, methodName);
		}

		if (result == null) {
			AppLogger.getLogger().log(Level.SEVERE, "No se pudo hallar el metodo solicitado");
		}
		return result;
	}

	private static Method getPublicMethod(Class<?> clazz, String methodName) {
		Method result = null;
		try {
			result = clazz.getDeclaredMethod(methodName);
		} catch (Exception e) {
			AppLogger.getLogger().log(Level.FINE,
					"getPublicMethod(" + clazz.getName() + ", " + methodName + ") falló");
		}
		return result;
	}

	private static Method getPrivateMethod(Class clazz, String methodName) {
		Method result = null;
		try {
			Method[] methods = clazz.getDeclaredMethods();
			boolean found = false;
			for (int i = 0; !found && (i < methods.length); i++) {
				// toma la primera aparición del método
				found = methodName.equals(methods[i].getName());
				if (found) {
					result = methods[i];
				}
			}
		} catch (Exception e) {
			AppLogger.getLogger().log(Level.FINE,
					"getPrivateMethod(" + clazz.getName() + ", " + methodName + ") falló");
		}
		return result;
	}

	public static Class getTypeParameterClass(Class clazz) {
		return getTypeParameterClass(clazz, (byte) 0);
	}

	public static Class getTypeParameterClass(Class clazz, byte index) {
		return clazz.getTypeParameters()[index].getClass();
	}

	public static Object newTypeParameterInstance(Field field) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		String className = field.getGenericType().toString();
		int index1 = className.indexOf('<');
		int index2 = className.indexOf('>');
		className = className.substring(index1 + 1, index2);
		return Class.forName(className).newInstance();
	}

	private static List<Field> doGetFields(Class clazz) {
		List<Field> fields = new ArrayList<Field>();
		Field[] declaredFields;
		Field declaredField;
		int mods;
		do {
			declaredFields = clazz.getDeclaredFields();
			for (Field element : declaredFields) {
				declaredField = element;
				mods = declaredField.getModifiers();
				if (((mods & Modifier.STATIC) == 0) && ((mods & Modifier.TRANSIENT) == 0)
						&& !declaredField.isSynthetic()
						&& !declaredField.getType().getSimpleName().equals("PK")) {
					fields.add(declaredField);
					declaredField.setAccessible(true);
				}
			}
			clazz = clazz.getSuperclass();
		} while (clazz != Object.class);
		return fields;
	}

	public static String getValue(Object object, String chainOfProperties) {
		String value = "";
		Object currentObject = getObject(object, chainOfProperties);
		if (currentObject instanceof Calendar) {
			value = DateUtils.formatCalendar(((Calendar) currentObject));
		} else if (currentObject instanceof Date) {
			value = DateUtils.formatDate(((Date) currentObject));
		} else if (currentObject != null) {
			value = currentObject.toString();
		}
		return value;
	}

	/**
	 * Provee el objecto producto de haber navegado por la cadena de propiedades especificada por
	 * parámetro.
	 * 
	 * @param element
	 *            objeto a partir del cual comenzar la navegación de propiedades.
	 * @param chainOfProperties
	 *            string con las propiedades separadas por puntos.
	 * @return el valor producto de haber navegado por la cadena de propiedades especificada por
	 *         parámetro.
	 */
	public static Object getObject(Object element, String chainOfProperties) {
		String[] properties = chainOfProperties.split(Pattern.quote("."));
		Object currentObject = element;
		for (String element2 : properties) {
			try {
				if (element2.endsWith("()")) {
					// es un metodo
					String methodName = element2.substring(0, element2.length() - 2);
					Method method = ClassUtils
							.getPublicMethod(currentObject.getClass(), methodName);
					currentObject = method.invoke(currentObject, new Object[] {});
				} else {
					// es un atributo
					Field field = ClassUtils.getField(currentObject.getClass(), element2);
					currentObject = field.get(currentObject);
				}
			} catch (Exception e) {
				AppLogger.getLogger().log(
						Level.SEVERE,
						"Error al recuperar " + chainOfProperties + " de la clase "
								+ element.getClass().getName());
			}
		}
		return currentObject;
	}

	/**
	 * Setea un valor dado en un atributo de una instancia
	 * 
	 * @param model
	 *            Instancia a la cual se le seteará el valor
	 * @param labelKey
	 *            Nombre del atributo de la instancia que será seteado
	 * @param value
	 *            Valor a setear
	 * 
	 * @return true si y solo si el valor ha sido seteado correctamente
	 */
	public static boolean setValueByReflection(Object instance, String labelKey, Object value) {
		Boolean result = false;
		if (instance != null) {
			result = true;
			Field field = null;
			try {
				String[] fields = labelKey.split(Pattern.quote("."));
				for (int i = 0; i < fields.length; i++) {
					field = getField(instance.getClass(), fields[i]);
					if (i != (fields.length - 1)) {
						// si es el ultimo atributo ya es el atributo a setear
						instance = field.get(instance);
					}
				}
				if (field != null) {
					if (value == null) {
						if ((String.class).equals(field.getType())) {
							field.set(instance, "");
						} else {
							field.set(instance, null);
						}

					} else if ((String.class).equals(field.getType())) {
						field.set(instance, value.toString().trim());

					} else if (field.getType() == Integer.class) {
						String text = value.toString().trim();
						Integer value2Set = (text.length() == 0) ? null : Integer.valueOf(text);
						field.set(instance, value2Set);

					} else if (field.getType() == Long.class) {
						String text = value.toString().trim();
						Long value2Set = (text.length() == 0) ? null : Long.valueOf(text);
						field.set(instance, value2Set);

					} else if (field.getType() == Boolean.TYPE) {
						field.set(instance, value);

					} else if (field.getType().equals(BigDecimal.class)) {
						String str = value.toString().trim().replace(',', '.');
						BigDecimal number;
						try {
							number = new BigDecimal(str);
						} catch (NumberFormatException exc) {
							number = BigDecimal.ZERO;
						}
						field.set(instance, number);

					} else if (field.getType().isEnum()) {
						field.setAccessible(true);
						if (value.equals(ComboHelper.EMPTY_ITEM)) {
							value = null;
						}
						field.setAccessible(true);
						field.set(instance, value);
					} else {
						if (value.equals(ComboHelper.EMPTY_ITEM)) {
							// cubre el caso en que el campo sea una interface y el valor un enum
							value = null;
						}
						field.setAccessible(true);
						field.set(instance, value);
					}
				}
			} catch (NumberFormatException numFormatExcept) {
				result = false;
			} catch (Exception exc) {
				result = false;
				AppLogger.getLogger().log(Level.SEVERE,
						instance.getClass().getName() + "." + labelKey, exc);
			}
		}
		return result;
	}

	/**
	 * Establece como valor del combo el establecido en atributo demarcado por labelKey de la
	 * instancia (valor atributo de instancia --> combo).
	 * 
	 * @param model
	 * @param labelKey
	 * @param textBox
	 */
	public static <T> void obtenerValorCombo(T instance, String labelKey, Combo combo) {
		if (instance != null) {
			try {
				Field field = getField(instance.getClass(), labelKey);
				if (field == null) {
					return;
				}

				Object value = field.get(instance);
				if (value != null) {
					if (value.getClass().isEnum()) {
						Enum enumerate = (Enum) value;
						combo.setText(enumerate.toString());
					} else {
						String text = (String) combo.getData(value.toString());
						if (text == null) {
							combo.setText("");
						} else {
							combo.setText(text);
						}
					}
				}
			} catch (Exception exc) {
				AppLogger.getLogger().log(Level.SEVERE, null, exc);
			}
		}
	}

	/**
	 * Retorna el valor establecido en el atributo demarcado por labelKey de la instancia (valor
	 * atributo de instancia --> textbox).
	 * 
	 * @param model
	 * @param propertyName
	 *            puede ser una cadena de propiedades separada por puntos.
	 */

	public static String obtenerValor(Object instance, String propertyName) {
		String result = "";
		if (instance != null) {
			try {
				Field field = null;
				String[] fields = propertyName.split(Pattern.quote("."));
				for (int i = 0; i < fields.length; i++) {
					field = getField(instance.getClass(), fields[i]);
					if (i != (fields.length - 1)) {
						// si es el ultimo atributo ya es el atributo a setear
						instance = field.get(instance);
					}
				}

				if ((field != null) && (field.get(instance) != null)) {
					if (field.getType() == BigDecimal.class) {
						result = ((BigDecimal) field.get(instance)).toPlainString();
					} else if (field.getType() == Integer.class) {
						result = ((Integer) field.get(instance)).toString();
					} else if (field.getType() == Long.class) {
						result = ((Long) field.get(instance)).toString();
					} else if (field.getType() == Date.class) {
						Date date = (Date) field.get(instance);
						result = DateUtils.formatDate(date);
					} else if (field.getType() == Calendar.class) {
						Calendar calendar = (Calendar) field.get(instance);
						result = DateUtils.formatCalendar(calendar);
					} else {
						result = field.get(instance).toString();
					}
				}
			} catch (Exception exc) {
				AppLogger.getLogger().log(Level.SEVERE, null, exc);
			}
		}
		return result;
	}

	/**
	 * Establece como el estado del button(checked) en base al atributo demarcado por labelKey de la
	 * instancia (valor atributo de instancia --> button).
	 * 
	 * @param model
	 * @param propertyName
	 * @param button
	 */
	public static void obtenerValor(Object instance, final String propertyName, Button button) {
		Field field = getField(instance.getClass(), propertyName);
		try {
			button.setSelection(field.getBoolean(instance));
		} catch (Exception exc) {
			AppLogger.getLogger().log(Level.SEVERE, null, exc);
		}
	}

	public static <T> T newInstance(Class<T> aClass) {
		if (aClass == null) {
			throw new IllegalArgumentException();
		}
		T result = null;
		Class[] empty = {};
		Constructor<T> constructor;
		try {
			constructor = aClass.getDeclaredConstructor(empty);
			constructor.setAccessible(true);
			result = constructor.newInstance((Object[]) null);
		} catch (Exception e) {
			AppLogger.getLogger().severe(
					"No se pudo instanciar la clase " + aClass.getCanonicalName());
		}
		return result;
	}

	public static void setDateByReflection(Object instance, String nombreAtributo,
			String dateAsString) {
		Field field = getField(instance.getClass(), nombreAtributo);
		if (field.getType().equals(Calendar.class)) {
			try {
				field.set(instance, DateUtils.parseDMY(dateAsString));
			} catch (Exception ex) {
				AppLogger.getLogger().log(Level.SEVERE, null, ex);
			}
		} else if (field.getType().equals(Date.class)) {
			try {
				field.set(instance, DateUtils.parseDMY(dateAsString).getTime());
			} catch (Exception ex) {
				AppLogger.getLogger().log(Level.SEVERE, null, ex);
			}
		} else {
			AppLogger.getLogger().log(Level.SEVERE,
					"Tipo de dato no soportado: " + field.getType().toString());
		}
	}

	public static void setDateByReflection(Object instance, String nombreAtributo,
			SWTCalendarDialog calendarDialog) {
		Field field = getField(instance.getClass(), nombreAtributo);
		if (field.getType().equals(Calendar.class)) {
			try {
				field.set(instance, calendarDialog.getSelectedCalendar());
			} catch (Exception ex) {
				AppLogger.getLogger().log(Level.SEVERE, null, ex);
			}
		} else if (field.getType().equals(Date.class)) {
			try {
				field.set(instance, calendarDialog.getSelectedDate());
			} catch (Exception ex) {
				AppLogger.getLogger().log(Level.SEVERE, null, ex);
			}
		} else {
			AppLogger.getLogger().log(Level.SEVERE,
					"Tipo de dato no soportado: " + field.getType().toString());
		}
	}

	private static final Map<Class, Field[]> sm_fields = new HashMap<Class, Field[]>();

}