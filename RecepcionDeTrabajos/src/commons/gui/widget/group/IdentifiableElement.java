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
 *
 * $Id: IdentifiableElement.java,v 1.2 2007/09/14 20:22:35 cvstursi Exp $
 */

package commons.gui.widget.group;

/**
 *
 * @author Luisina Marconi
 *
 * @param <T>
 */
public interface IdentifiableElement<T> {
	T getModel();
	String getDescription();
}