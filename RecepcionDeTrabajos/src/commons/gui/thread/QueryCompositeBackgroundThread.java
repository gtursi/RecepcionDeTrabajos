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
 * $Id: QueryCompositeBackgroundThread.java,v 1.4 2007/04/16 18:04:34 cvschioc Exp $
 */
package commons.gui.thread;

import java.util.List;

import org.eclipse.swt.widgets.Display;

import recepciondetrabajos.MainWindow;




import commons.gui.widget.composite.QueryComposite;

/**
 *
 * @author Gabriel Tursi
 * @version $Revision: 1.4 $ $Date: 2007/04/16 18:04:34 $
 */
public abstract class QueryCompositeBackgroundThread extends BackgroundThread {

	public QueryCompositeBackgroundThread(Display display, QueryComposite queryComposite) {
		super(display);
		this.queryComposite = queryComposite;
	}

	protected abstract List doQuery();
	
	@Override
	protected void performBackgroudOperation() {
		items = doQuery();
	}

	@Override
	protected void updateUI() {
		queryComposite.getTable().setInput(items);
		queryComposite.getTable().getTable().setEnabled(true);
		MainWindow.getInstance().setDefaultStatusMessage();
	}

	private List items;
	
	private final QueryComposite queryComposite;

}

