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
 * $Id: ControlMetainfo.java,v 1.3 2007/04/12 12:53:03 cvstursi Exp $
 */

package commons.gui.widget.creation.metainfo;

import org.eclipse.swt.widgets.Composite;

/**
 * Modela la meta información inherente a un Control visual.
 * 
 * @author Jonathan Chiocchio
 * @version $Revision: 1.3 $ $Date: 2007/04/12 12:53:03 $
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
