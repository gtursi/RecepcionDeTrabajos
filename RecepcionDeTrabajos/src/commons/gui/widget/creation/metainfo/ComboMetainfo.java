package commons.gui.widget.creation.metainfo;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import commons.gui.util.ComboHelper;
import commons.gui.widget.creation.binding.Binding;

public class ComboMetainfo extends ControlMetainfo {

	private static final ComboMetainfo instance = new ComboMetainfo();

	public static ComboMetainfo create(Composite composite, String labelKey,
			StringValueMetaInfo bindingInfo, ComboValuesMetainfo comboValuesMetainfo,
			boolean readOnly) {
		ControlMetainfo.setValues(instance, composite, labelKey, readOnly);
		instance.valueMetaInfo = bindingInfo;
		instance.comboValuesMI = comboValuesMetainfo;
		return instance;
	}

	public static ComboMetainfo create(Composite composite, String labelKey, Binding bindingInfo,
			ComboValuesMetainfo comboValuesMetainfo, boolean readOnly) {
		ControlMetainfo.setValues(instance, composite, labelKey, readOnly);
		instance.bindingInfo = bindingInfo;
		instance.comboValuesMI = comboValuesMetainfo;
		return instance;
	}

	public ComboMetainfo composite(Composite comp) {
		this.composite = comp;
		return this;
	}

	public ComboMetainfo labelKey(String key) {
		this.labelKey = key;
		return this;
	}

	public ComboMetainfo comboValuesMetainfo(ComboValuesMetainfo valuesMetaInfo) {
		this.comboValuesMI = valuesMetaInfo;
		return this;
	}

	public ComboMetainfo readOnly(boolean readOnlyValue) {
		this.readOnly = readOnlyValue;
		return this;
	}

	public ComboMetainfo binding(Binding binding) {
		this.bindingInfo = binding;
		return this;
	}

	public Control create() {
		return ComboHelper.createComboB(this);
	}

	@Override
	public void restoreDefaults() {
		super.restoreDefaults();
		this.comboValuesMI = null;
		// this.valueMetaInfo = null; tira NullPointerException en los listeners
		this.defaultOption = null;
	}

	public ComboValuesMetainfo comboValuesMI;

	public StringValueMetaInfo valueMetaInfo;

	public Binding bindingInfo;

	public String defaultOption;

}
