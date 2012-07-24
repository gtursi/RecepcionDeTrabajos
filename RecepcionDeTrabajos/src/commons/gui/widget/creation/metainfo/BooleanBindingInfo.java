package commons.gui.widget.creation.metainfo;

import commons.util.ClassUtils;

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
		if ((super.model == null) && (super.propertyName == null)) {
			value = this.fixedValue;
		} else if (super.model != null) {
			String valueAsString = ClassUtils.getValue(super.model, super.propertyName);
			value = Boolean.parseBoolean(valueAsString);
		}
		return value;
	}

	private final Boolean fixedValue;

}
