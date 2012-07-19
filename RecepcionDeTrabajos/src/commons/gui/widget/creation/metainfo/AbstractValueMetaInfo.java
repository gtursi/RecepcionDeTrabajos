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
 * $Id: AbstractValueMetaInfo.java,v 1.1 2007/05/14 20:44:29 cvsalons Exp $
 */
package commons.gui.widget.creation.metainfo;

import org.apache.commons.lang.StringUtils;


/**
 *
 * @author Gabriel Tursi
 * @version $Revision: 1.1 $ $Date: 2007/05/14 20:44:29 $
 */
public abstract class AbstractValueMetaInfo<T> {
	
	public AbstractValueMetaInfo(Object model, String propertyName) {
		super();
		if(StringUtils.isBlank(propertyName)){
			throw new IllegalArgumentException("El atributo propertyName no puede ser nulo");
		}
		this.model = model;
		this.propertyName = propertyName;
	}

	public AbstractValueMetaInfo() {
		super();
	}

	public abstract T getValue();

	public Object getModel() {
		return this.model;
	}

	public String getPropertyName() {
		return this.propertyName;
	}
	
	protected Object model;

	protected String propertyName;

}

