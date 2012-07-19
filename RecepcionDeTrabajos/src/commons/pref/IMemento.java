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
 * $Id: IMemento.java,v 1.3 2007/04/16 18:04:31 cvschioc Exp $
 */
package commons.pref;

import java.io.Writer;

/**
 * @author Margarita Buriano
 * @version $Revision: 1.3 $ $Date: 2007/04/16 18:04:31 $
 */

public interface IMemento {

	IMemento createChild(String string);

	IMemento createChild(String string1, String string2);

	IMemento getChild(String string);

	IMemento[] getChildren(String string);

	Float getFloat(String string);

	String getID();

	Integer getInteger(String string);

	String getString(String string);

	String getTextData();

	void putFloat(String string, float floatValue);

	void putInteger(String string, int intValue);

	void putMemento(IMemento imemento);

	void putString(String string1, String string2);

	void putTextData(String string);
	
	void save(Writer writer);
}