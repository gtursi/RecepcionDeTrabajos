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