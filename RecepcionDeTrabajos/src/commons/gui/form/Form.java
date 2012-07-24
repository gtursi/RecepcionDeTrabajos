package commons.gui.form;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import commons.gui.widget.creation.binding.Binding;

public class Form {

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

	public void exportBinding(final String controlName, final Binding binding) {
		bindings.put(controlName, binding);
	}

	public Binding getBinding(final String controlName) {
		return bindings.get(controlName);
	}

	private Object model;

	private final Map<String, Binding> bindings = new HashMap<String, Binding>();

}