package commons.gui.widget.creation.metainfo;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import commons.gui.util.TextFieldHelper;
import commons.gui.widget.creation.binding.Binding;

/**
 * Meta-información necesaria para la creación de un campo booleano
 * 
 */
public class BooleanFieldMetainfo extends ControlMetainfo {

	private static BooleanFieldMetainfo instance = new BooleanFieldMetainfo();

	public static BooleanFieldMetainfo create(Composite composite, String labelKey,
			BooleanBindingInfo bindingMetainfo, boolean readOnly) {
		ControlMetainfo.setValues(instance, composite, labelKey, readOnly);
		instance.bindingInfo = bindingMetainfo;
		return instance;
	}

	@Override
	public void restoreDefaults() {
		super.restoreDefaults();
		applyDefaults();
	}

	public BooleanFieldMetainfo() {
		super();
		this.applyDefaults();
	}

	private void applyDefaults() {
		this.bindingInfo = null;
	}

	public BooleanFieldMetainfo composite(Composite comp) {
		this.composite = comp;
		return this;
	}

	public BooleanFieldMetainfo labelKey(String key) {
		this.labelKey = key;
		return this;
	}

	public BooleanFieldMetainfo label(String aLabel) {
		this.label = aLabel;
		return this;
	}

	public BooleanFieldMetainfo readOnly(boolean isReadOnly) {
		this.readOnly = isReadOnly;
		return this;
	}

	public Button create() {
		return TextFieldHelper.createBooleanFieldB(this);
	}

	public BooleanFieldMetainfo binding(Binding bindInfo) {
		this.binding = bindInfo;
		return this;
	}

	public String label;

	public BooleanBindingInfo bindingInfo;

	public Binding binding;
}