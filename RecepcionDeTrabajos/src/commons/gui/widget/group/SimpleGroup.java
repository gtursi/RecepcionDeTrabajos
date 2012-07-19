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
 * $Id: SimpleGroup.java,v 1.9 2007/05/10 17:19:33 cvstursi Exp $
 */

package commons.gui.widget.group;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import commons.gui.widget.DefaultLayoutFactory;

/**
 * Grupo base del que heredan todos los grupos.
 * 
 * @author Jonathan Chiocchio
 * @version $Revision: 1.9 $ $Date: 2007/05/10 17:19:33 $
 */
public class SimpleGroup {

	public SimpleGroup(Composite composite, String text, boolean readOnly) {
		this(composite, text, readOnly, DEFAULT_NUM_COLUMNS);
	}

	public SimpleGroup(Composite composite, String text, boolean readOnly, int numColumns) {
		this.swtGroup = new Group(composite, SWT.NONE);
		this.numColumns = numColumns;
		if(StringUtils.isNotBlank(text)) {
			this.swtGroup.setText(text);	
		}		
		applyLayout();
		this.readOnly = readOnly;
	}

	public void setLayoutData(GridData gridData) {
		swtGroup.setLayoutData(gridData);
	}

	/**
	 * Provee un layout por default, sobreescribir este m�todo si se desea otro
	 * layout.
	 */
	protected void applyLayout() {
		DefaultLayoutFactory.setDefaultGridLayout(this.getSwtGroup(), numColumns);
		GridLayout layout = (GridLayout) this.getSwtGroup().getLayout();
		if(!StringUtils.isEmpty(this.getSwtGroup().getText())){
			layout.marginTop = 5;
		}
		layout.marginHeight = 5;
		layout.marginWidth = 10;		
	}

	public Group getSwtGroup() {
		return this.swtGroup;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	private final Group swtGroup;
	
	protected boolean readOnly;

	private int numColumns;

	private static final int DEFAULT_NUM_COLUMNS = 2;

}