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

