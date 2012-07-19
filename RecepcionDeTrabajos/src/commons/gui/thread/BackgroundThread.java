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

