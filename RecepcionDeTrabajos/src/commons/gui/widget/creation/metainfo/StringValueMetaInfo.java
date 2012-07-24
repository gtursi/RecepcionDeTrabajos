package commons.gui.widget.creation.metainfo;

import commons.util.ClassUtils;

/**
 * Representa la meta información referente al valor de un Control visual. Encapsula la decisión
 * sobre si el valor es obtenido de un objeto por reflection o si el mismo está explícito.
 * 
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
		if ((super.model == null) && (super.propertyName == null)) {
			value = this.literalValue;
		} else if (super.model != null) {
			value = ClassUtils.getValue(super.model, super.propertyName);
		}
		return value;
	}

	public boolean isLiteral() {
		return this.literalValue != null;
	}

	private String literalValue;

}
