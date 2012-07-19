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