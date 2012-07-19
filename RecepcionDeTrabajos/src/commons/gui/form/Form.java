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