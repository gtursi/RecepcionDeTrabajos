package commons.gui.widget.creation.metainfo;

import org.eclipse.swt.widgets.Composite;

/**
 * Modela la meta información inherente a un Control visual.
 * 
 */
public class ControlMetainfo {

	public ControlMetainfo(Composite composite, String labelKey, boolean readOnly) {
		super();
		this.composite = composite;
		this.labelKey = labelKey;
		this.readOnly = readOnly;
	}

	public Composite composite;

	public String labelKey;

	public boolean readOnly = false;

	public void restoreDefaults() {
		this.labelKey = null;
	}

	protected ControlMetainfo() {
		super();
	}

	public static void setValues(ControlMetainfo instance, Composite composite, String labelKey,
			boolean readOnly) {
		instance.composite = composite;
		instance.labelKey = labelKey;
		instance.readOnly = readOnly;
	}

}
