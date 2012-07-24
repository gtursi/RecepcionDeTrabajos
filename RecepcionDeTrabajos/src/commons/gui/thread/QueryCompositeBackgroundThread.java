package commons.gui.thread;

import java.util.List;

import org.eclipse.swt.widgets.Display;

import recepciondetrabajos.MainWindow;

import commons.gui.widget.composite.QueryComposite;

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
