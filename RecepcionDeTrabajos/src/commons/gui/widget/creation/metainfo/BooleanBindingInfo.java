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
