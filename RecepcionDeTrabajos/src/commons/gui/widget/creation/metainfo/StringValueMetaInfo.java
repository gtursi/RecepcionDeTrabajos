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
 * $Id: StringValueMetaInfo.java,v 1.2 2007/05/21 14:02:34 cvschioc Exp $
 */

package commons.gui.widget.creation.metainfo;

import commons.util.ClassUtils;

/**
 * Representa la meta información referente al valor de un Control visual. Encapsula la decisión
 * sobre si el valor es obtenido de un objeto por reflection o si el mismo está explícito.
 * @author Jonathan Chiocchio
 * @version $Revision: 1.2 $ $Date: 2007/05/21 14:02:34 $
 */

public class StringValueMetaInfo extends AbstractValueMetaInfo<String> {

	public StringValueMetaInfo(String literalValue) {
		super();
		this.literalValue = literalValue;
	}
	
	/**
	 * El modelo puede ser nulo ya que puede ser enchufado posteriormente.
	 * 
	 */
	public StringValueMetaInfo(Object model, String propertyName) {
		super(model, propertyName);
	}
	
	@Override
	public String getValue() {
		String value = "";
		if(super.model == null && super.propertyName == null) {
			value = this.literalValue;
		} else if(super.model != null) {
			value = ClassUtils.getValue(super.model, super.propertyName);			
		}
		return value;
	}
	
	public boolean isLiteral() {
		return this.literalValue != null;
	}

	private String literalValue;
	
}
