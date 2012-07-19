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
 * $Id: BackgroundThread.java,v 1.4 2007/06/28 18:33:37 cvstursi Exp $
 */
package commons.gui.thread;


import org.eclipse.swt.widgets.Display;

/**
 *
 * @author Gabriel Tursi
 * @version $Revision: 1.4 $ $Date: 2007/06/28 18:33:37 $
 */
public abstract class BackgroundThread extends Thread {

	public BackgroundThread(Display display){
		super();
		this.display = display;
		this.setUncaughtExceptionHandler(new CustomUncaughtExceptionHandler());
	}

	@Override
	public final void run(){
		BackgroundThread.instance = this;
		performBackgroudOperation();
		display.asyncExec(runnable);
	}
	
	protected abstract void performBackgroudOperation();

	protected abstract void updateUI();
	
	private final static Runnable runnable = new Runnable() {

		public void run() {
			BackgroundThread.instance.updateUI();
		}
	};
	
	private final Display display;

	private static BackgroundThread instance;
}

