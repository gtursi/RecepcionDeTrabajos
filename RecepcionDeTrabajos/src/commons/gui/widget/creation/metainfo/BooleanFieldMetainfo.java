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
 * $Id: BooleanFieldMetainfo.java,v 1.7 2007/06/27 18:30:52 cvschioc Exp $
 */
package commons.gui.widget.creation.metainfo;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import commons.gui.util.TextFieldHelper;
import commons.gui.widget.creation.binding.Binding;

/**
 * Meta-información necesaria para la creación de un campo booleano
 * 
 * @author Gabriel Tursi
 * @version $Revision: 1.7 $ $Date: 2007/06/27 18:30:52 $
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