package recepciondetrabajos.widget.composite.queries.pedidos;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import recepciondetrabajos.Constants;
import recepciondetrabajos.domain.GananciaMensual;
import recepciondetrabajos.service.PedidoService;

import commons.gui.table.GenericTable;
import commons.gui.thread.QueryCompositeBackgroundThread;
import commons.gui.widget.composite.QueryComposite;

public class GananciaMensualQueryComposite extends QueryComposite {

	public static GananciaMensualQueryComposite getInstance(Composite parent) {
		if (instance == null) {
			instance = new GananciaMensualQueryComposite(parent,
					Constants.CONSULTA_GANANCIA_MENSUAL);
		}
		instance.doQuery();
		return instance;
	}

	protected void agregarFiltrosEspecificos(Group grupoFiltros) {
	}

	protected void agregarFiltro(Composite parent) {

	}

	@Override
	protected List<Control> getFilterControls() {
		List<Control> controls = new ArrayList<Control>();
		return controls;
	}

	@Override
	protected SelectionListener getFilterListener() {
		SelectionListener listener = new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				doQuery();
			}
		};
		return listener;
	}

	@Override
	protected SelectionListener getCleanUpListener() {
		return new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
			}
		};
	}

	@Override
	protected void agregarTabla(Composite parent) {
		setTable(new GenericTable(parent, GananciaMensual.class, instanceKey, null, false));
	}

	@Override
	protected QueryCompositeBackgroundThread getBackgroundThread() {
		return new QueryCompositeBackgroundThread(getShell().getDisplay(), this) {

			@Override
			protected List doQuery() {
				return PedidoService.gananciaMensualizada();
			}
		};
	}

	@Override
	public GenericTable getTable() {
		return this.gananciaMensualQueryTable;
	}

	/**
	 * Cada subclase deberá guardar en una variable privada su propia tabla.
	 * 
	 * @param table
	 */
	protected void setTable(GenericTable table) {
		this.gananciaMensualQueryTable = table;
	}

	@Override
	protected void agregarBotones() {

	}

	protected GananciaMensualQueryComposite(Composite parent, String name) {
		super(parent, name);
		init();
	}

	@Override
	protected void setMinSizeWindow(ScrolledComposite sc, int width, int height) {
		sc.setMinSize(WIDTH, HEIGHT);
	}

	private static int WIDTH = 600;

	private static int HEIGHT = 600;

	private static GananciaMensualQueryComposite instance;

	private GenericTable gananciaMensualQueryTable;

}