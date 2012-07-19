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
 * $Id: Validations.java,v 1.11 2008/05/19 18:33:50 cvstursi Exp $
 */
package commons.gui.validators;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import commons.util.DateUtils;

/**
 * 
 * @author Gabriel Tursi
 * @version $Revision: 1.11 $ $Date: 2008/05/19 18:33:50 $
 */
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