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
 * $Id: QueryComposite.java,v 1.12 2010/12/23 14:23:59 cvsmvera Exp $
 */
package commons.gui.widget.composite;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import recepciondetrabajos.MainWindow;




import commons.gui.table.GenericTable;
import commons.gui.thread.QueryCompositeBackgroundThread;
import commons.gui.widget.group.SimpleGroup;
import commons.gui.widget.group.query.FilterButtonsGroup;

/**
 * Composite base para todas las consultas que tengan un filtro sobre lo consultado y acciones a
 * realizar sobre los items seleccionados;
 * 
 * @author Gabriel Tursi
 * @version $Revision: 1.12 $ $Date: 2010/12/23 14:23:59 $
 */
public abstract class QueryComposite extends Composite {

	/**
	 * @param parent
	 * @param name
	 */
	public QueryComposite(Composite parent, String name) {
		super(parent, SWT.NONE);
		this.instanceKey = name;
	}

	protected final QueryComposite init() {
		createCompositeMainQuery();
		return this;
	}

	/*
	 * Crea un composite con un ScrollBar, segun la resolucion del cliente son visibles o no. Dentro
	 * de este composite se crearan las diferentes vistas abiertas por el usuario del estilo Query
	 */
	private void createCompositeMainQuery() {
		this.setLayout(new FillLayout());

		ScrolledComposite sc = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);

		final Composite composite = new Composite(sc, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite compositeTop = new Composite(composite, SWT.NONE);
		compositeTop.setLayout(new GridLayout(2, false));
		agregarFiltro(compositeTop);

		agregarTabla(composite);
		agregarBotones();
		this.pack();

		sc.setContent(composite);
		setMinSizeWindow(sc, 1000, 600);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
	}

	/**
	 * Setea el ancho y alto minimo para que aparezca el scroll
	 * 
	 * @param sc
	 * @param width
	 * @param height
	 */
	protected abstract void setMinSizeWindow(ScrolledComposite sc, int width, int height);

	/**
	 * Crea el composite de filtrado respectivo a la consulta a implementar.
	 * 
	 * @param parent
	 */
	protected void agregarFiltro(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		agregarFiltrosEspecificos((new SimpleGroup(composite, "Filtros", false)).getSwtGroup());
		filterButtonsGroup = new FilterButtonsGroup(composite, getFilterListener(),
				getCleanUpListener());
		agregarSelectionListener(getFilterControls());
	}

	/**
	 * Agrega a todos los controles el listener para que al presionar <code>ENTER</code> sobre ellos
	 * se dispare la consulta.
	 * 
	 * @param controls
	 *            Controles a los que se le agregará el listener.
	 */
	private void agregarSelectionListener(List<Control> controls) {
		for (Control control : controls) {
			KeyListener filterButtonListener = new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent event) {
					if ((event.keyCode == SWT.Selection) || (event.keyCode == SWT.KEYPAD_CR)) {
						filterButtonsGroup.getFilterButton().notifyListeners(SWT.Selection, null);
					}
				}
			};
			control.addKeyListener(filterButtonListener);
		}
	}

	/**
	 * Realiza la consulta definida en <code>getBackgroundThread</code> y actualiza la tabla.
	 * 
	 */
	protected final void doQuery() {
		MainWindow.getInstance().setStatus("Realizando consulta...");
		this.getTable().getTable().setEnabled(false);
		getBackgroundThread().start();
	}

	/**
	 * Agrega los filtros específicos.
	 * 
	 * @param grupoFiltros
	 *            el grupo sobre el cual se agregarán dichos filtros.
	 */
	protected abstract void agregarFiltrosEspecificos(Group grupoFiltros);

	/**
	 * Método abstracto que generaliza la obtención del listener específico de cada subclase.
	 * 
	 * @return un listener que se activa cuando se efectúa una selección en el botón de Filtro.
	 */
	protected abstract SelectionListener getFilterListener();

	/**
	 * @return todos los controles de filtro (Texts, combos, etc)
	 */
	protected abstract List<Control> getFilterControls();

	/**
	 * Método abstracto que generaliza la obtención del listener específico de cada subclase.
	 * 
	 * @return un listener que se activa cuando se efectúa una selección en el botón de Clean Up.
	 */
	protected abstract SelectionListener getCleanUpListener();

	/**
	 * En este método se crea el objeto GenericTable con todos sus datos correspondientes
	 * 
	 * @param parent
	 *            composite sobre el cuál se creará la tabla.
	 */
	protected abstract void agregarTabla(Composite parent);

	/**
	 * Retorna el <code>QueryCompositeBackgroundThread</code> encargado de realizar la consulta.
	 */
	protected abstract QueryCompositeBackgroundThread getBackgroundThread();

	/**
	 * Este método agrega los botones que agregan funcionalidad a la consulta, como ser la edición,
	 * o el borrado de un item de la consulta.
	 * 
	 * @param parent
	 */
	protected abstract void agregarBotones();

	/**
	 * 
	 */
	// protected abstract void configurarFiltros();

	/**
	 * Provee acceso a la tabla específica del QueryComposite. Deberá ser sobreescrito por las
	 * subclases.
	 */
	public abstract GenericTable getTable();

	/**
	 * Su utilidad es limpiar los filtros y actualizar la consulta del QueryComposite.
	 */
	public void reset() {
		// Simulo el cleanUp
		SelectionListener cleanUpListener = this.getCleanUpListener();
		if (cleanUpListener != null) {
			cleanUpListener.widgetSelected(null);
		}
		// Simulo el filtrado
		SelectionListener filterListener = this.getFilterListener();
		if (filterListener != null) {
			filterListener.widgetSelected(null);
		}
	}

	protected String instanceKey;

	protected FilterButtonsGroup filterButtonsGroup;
}
