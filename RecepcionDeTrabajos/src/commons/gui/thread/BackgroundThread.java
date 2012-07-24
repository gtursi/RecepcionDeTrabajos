package commons.gui.thread;

import org.eclipse.swt.widgets.Display;

public abstract class BackgroundThread extends Thread {

	public BackgroundThread(Display display) {
		super();
		this.display = display;
		this.setUncaughtExceptionHandler(new CustomUncaughtExceptionHandler());
	}

	@Override
	public final void run() {
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
