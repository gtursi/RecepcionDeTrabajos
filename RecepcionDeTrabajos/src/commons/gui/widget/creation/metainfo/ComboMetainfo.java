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
 * $Id: ComboMetainfo.java,v 1.10 2007/05/29 21:42:14 cvsalons Exp $
 */
package commons.gui.widget.creation.metainfo;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import commons.gui.util.ComboHelper;
import commons.gui.widget.creation.binding.Binding;

/**
 * @author Gabriel Tursi
 * @version $Revision: 1.10 $ $Date: 2007/05/29 21:42:14 $
 */
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

	public static ComboMetainfo create(Composite composite, String labelKey,
			Binding bindingInfo, ComboValuesMetainfo comboValuesMetainfo, boolean readOnly) {
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
