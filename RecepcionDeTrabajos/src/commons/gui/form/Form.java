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
 * $Id: Form.java,v 1.30 2007/12/26 15:41:51 cvsalons Exp $
 */

package commons.gui.form;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import commons.gui.widget.creation.binding.Binding;

/**
 * @author Rodrigo Alonso
 */

public class Form {//implements Subscriber {

	public Form() {
		super();
	}
	
	public Map<String, Binding> getBindings() {
		return Collections.unmodifiableMap(bindings);
	}

	
	public Form(Object model) {
		this.model = model;
	}


	public Object getModel() {
		return this.model;
	}


	public void exportBinding(final String controlName, final Binding binding)
	{
		bindings.put(controlName, binding);
		//bindingInfo.subscribe
	}
	
	public Binding getBinding(final String controlName)
	{
		return bindings.get(controlName);
	}

	private Object model;
	
	private final Map<String, Binding> bindings = new HashMap<String, Binding>();

}