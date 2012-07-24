package commons.gui.widget.creation.metainfo;

import org.apache.commons.lang.StringUtils;

import commons.util.ClassUtils;

/**
 * Representa la meta información referente al valor de un Control visual. Encapsula la decisión
 * sobre si el valor es obtenido de un objeto por reflection o si el mismo está explícito.
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
		if (StringUtils.isBlank(propertyName)) {
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
		if ((model == null) && (propertyName == null)) {
			value = this.literalValue;
		} else if (model != null) {
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
