/*
 * Licencia de Caja de Valores S.A., Versi�n 1.0
 *
 * Copyright (c) 2006 Caja de Valores S.A.
 * 25 de Mayo 362, Ciudad Aut�noma de Buenos Aires, Rep�blica Argentina
 * Todos los derechos reservados.
 *
 * Este software es informaci�n confidencial y propietaria de Caja de Valores S.A. ("Informaci�n
 * Confidencial"). Usted no divulgar� tal Informaci�n Confidencial y la usar� solamente de acuerdo a
 * los t�rminos del acuerdo de licencia que posee con Caja de Valores S.A.
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
 * Meta-informaci�n necesaria para la creaci�n de un campo booleano
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