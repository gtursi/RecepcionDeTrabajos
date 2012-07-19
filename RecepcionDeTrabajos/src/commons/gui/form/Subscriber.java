/*
 * $Id: Subscriber.java,v 1.8 2007/05/28 13:34:08 cvschioc Exp $
 *
 * Copyright (c) 2003 Caja de Valores S.A.
 * 25 de Mayo 362, Buenos Aires, Rep�blica Argentina.
 * Todos los derechos reservados.
 *
 * Este software es informaci�n confidencial y propietaria de Caja de Valores
 * S.A. ("Informaci�n Confidencial"). Usted no divulgar� tal Informaci�n
 * Confidencial y solamente la usar� conforme a los terminos del Acuerdo que Ud.
 * posee con Caja de Valores S.A.
 */

package commons.gui.form;

/**
 * @author Rodrigo Alonso
 * @version $Revision: 1.8 $ $Date: 2007/05/28 13:34:08 $
 */

public interface Subscriber {
	void change(String key, Object value);

	String getId();
}