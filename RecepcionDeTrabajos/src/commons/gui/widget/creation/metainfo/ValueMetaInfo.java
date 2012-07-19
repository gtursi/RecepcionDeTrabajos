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
 * $Id: ValueMetaInfo.java,v 1.7 2007/05/14 20:44:29 cvsalons Exp $
 */

package commons.gui.widget.creation.metainfo;

import org.apache.commons.lang.StringUtils;

import commons.util.ClassUtils;

/**
 * Representa la meta informaci�n referente al valor de un Control visual. Encapsula la decisi�n
 * sobre si el valor es obtenido de un objeto por reflection o si el mismo est� expl�cito.
 * @author Jonathan Chiocchio
 * @version $Revision: 1.7 $ $Date: 2007/05/14 20:44:29 $
 */

public class ValueMetaInfo {

	public ValueMetaInfo(String literalValue) {
		reset();
		this.literalValue = literalValue;
	}
	
	/**
	 * El modelo puede ser nulo ya que puede ser enchufado posteriormente.
	 * 
	 */
	public ValueMetaInfo(Object model, String propertyName) {
		reset();
		if(StringUtils.isBlank(propertyName)){
			throw new IllegalArgumentException("El atributo propertyName no puede ser nulo");
		}
		this.propertyName = propertyName;
		this.model = model;
	}
	
	private void reset() {
		this.literalValue = null;
		this.propertyName = null;
		this.model = null;
	}

	public String getValue() {
		String value = "";
		if(model == null && propertyName == null) {
			value = this.literalValue;
		} else if(model != null) {
			value = ClassUtils.getValue(model, propertyName);			
		}
		return value;
	}

	public Object getModel() {
		return this.model;
	}
	
	public String getPropertyName() {
		return this.propertyName;
	}
	
	public boolean isLiteral() {
		return this.literalValue != null;
	}

	private String literalValue;
	
	private Object model;
	
	private String propertyName;
}
