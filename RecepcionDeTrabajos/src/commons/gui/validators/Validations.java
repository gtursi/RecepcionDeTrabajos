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
				result.add("El texto ingresado no est� entre " + infimo + " y " + supremo);
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