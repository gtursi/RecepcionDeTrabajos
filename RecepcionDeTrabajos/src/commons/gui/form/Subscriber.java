/*
 * $Id: Subscriber.java,v 1.8 2007/05/28 13:34:08 cvschioc Exp $
 *
 * Copyright (c) 2003 Caja de Valores S.A.
 * 25 de Mayo 362, Buenos Aires, República Argentina.
 * Todos los derechos reservados.
 *
 * Este software es información confidencial y propietaria de Caja de Valores
 * S.A. ("Información Confidencial"). Usted no divulgará tal Información
 * Confidencial y solamente la usará conforme a los terminos del Acuerdo que Ud.
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