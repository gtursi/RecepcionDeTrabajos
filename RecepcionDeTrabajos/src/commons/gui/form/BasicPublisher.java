/*
 * $Id: BasicPublisher.java,v 1.1 2007/06/07 20:00:59 cvsalons Exp $
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rodrigo Alonso
 * @version $Revision: 1.1 $ $Date: 2007/06/07 20:00:59 $
 */

public class BasicPublisher implements Publisher {
	/* (non-Javadoc)
	 * @see commons.gui.form.Publisher#subscribe(commons.gui.form.Subscriber)
	 */
	public void subscribe(Subscriber subscriber) {
		subscribers.add(subscriber);
	}

	protected final List<Subscriber> subscribers = new ArrayList<Subscriber>();
}
