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
 * $Id: StringValueMetaInfo.java,v 1.2 2007/05/21 14:02:34 cvschioc Exp $
 */

package commons.gui.widget.creation.metainfo;

import commons.util.ClassUtils;

/**
 * Representa la meta informaci�n referente al valor de un Control visual. Encapsula la decisi�n
 * sobre si el valor es obtenido de un objeto por reflection o si el mismo est� expl�cito.
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
