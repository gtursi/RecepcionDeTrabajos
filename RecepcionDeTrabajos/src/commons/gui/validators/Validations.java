package commons.gui.validators;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import commons.util.DateUtils;

public abstract class Validations {

	public static List<String> email(String email) {
		List<String> result = new ArrayList<String>();
		return result;
	}

	public static List<String> rango(String texto, int infimo, int supremo) {
		List<String> result = new ArrayList<String>();
		if (!StringUtils.isEmpty(texto)) {
			int textoIngresado = Integer.parseInt(texto);
			if ((textoIngresado < infimo) || (textoIngresado > supremo)) {
				result.add("El texto ingresado no está entre " + infimo + " y " + supremo);
			}
		}
		return result;
	}

	public static List<String> mayor(String texto, int infimo) {
		List<String> result = new ArrayList<String>();
		if (!StringUtils.isEmpty(texto)) {
			int textoIngresado = Integer.parseInt(texto);
			if (textoIngresado < infimo) {
				result.add("El texto ingresado no es mayor que " + infimo);
			}
		}
		return result;
	}

	public static String validateDate(String text) {
		String result = null;
		if (!StringUtils.isEmpty(text)) {
			if (!DateUtils.isValidDate(text)) {
				result = "El texto ingresado no posee un formato de fecha correcto ("
						+ DateUtils.DATE_FORMAT.toLowerCase() + ")";
			}
		}
		return result;
	}

}