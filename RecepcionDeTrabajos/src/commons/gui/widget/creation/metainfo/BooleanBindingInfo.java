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
 * $Id: BooleanBindingInfo.java,v 1.2 2007/05/14 20:44:29 cvsalons Exp $
 */
package commons.gui.widget.creation.metainfo;

import commons.util.ClassUtils;

/**
 * 
 * @author Gabriel Tursi
 * @version $Revision: 1.2 $ $Date: 2007/05/14 20:44:29 $
 */
public class BooleanBindingInfo extends AbstractValueMetaInfo<Boolean> {

	public BooleanBindingInfo(Boolean fixedValue) {
		super();
		this.fixedValue = fixedValue;
	}
	
	public BooleanBindingInfo(Object model, String propertyName) {
		super(model, propertyName);
		this.fixedValue = null;
	}

	
	@Override
	public Boolean getValue() {
		Boolean value = Boolean.FALSE;
		if (super.model == null && super.propertyName == null) {
			value = this.fixedValue;
		} else if (super.model != null) {
			String valueAsString = ClassUtils.getValue(super.model, super.propertyName);
			value = Boolean.parseBoolean(valueAsString);
		}
		return value;
	}

	private final Boolean fixedValue;

}
