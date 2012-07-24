package recepciondetrabajos.widget.composite.queries.clientes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import recepciondetrabajos.Constants;
import recepciondetrabajos.domain.Cliente;
import recepciondetrabajos.domain.Pedido;
import recepciondetrabajos.service.ClienteService;
import recepciondetrabajos.widget.dialog.cliente.ClienteDetailUpdateDialog;
import recepciondetrabajos.widget.dialog.pedido.NuevoPedidoDialog;

import commons.gui.table.GenericTable;
import commons.gui.thread.QueryCompositeBackgroundThread;
import commons.gui.util.TextFieldHelper;
import commons.gui.widget.composite.QueryComposite;
import commons.gui.widget.composite.SimpleComposite;
import commons.gui.widget.creation.metainfo.StringValueMetaInfo;
import commons.gui.widget.creation.metainfo.TextFieldMetainfo;

public class ClienteQueryComposite extends QueryComposite {

	public static ClienteQueryComposite getInstance(Composite parent) {
		if (instance == null) {
			instance = new ClienteQueryComposite(parent, Constants.CONSULTA_CLIENTES);
		}
		instance.doQuery();
		return instance;
	}

	@Override
	protected void agregarFiltrosEspecificos(Group grupoFiltros) {
		agregarFiltroDenominacionCliente(grupoFiltros);
		configurarFiltros();
	}

	@Override
	protected List<Control> getFilterControls() {
		List<Control> controls = new ArrayList<Control>();
		controls.add(this.denominacionClienteText);
		return controls;
	}

	protected void agregarFiltroDenominacionCliente(Composite composite) {
		TextFieldMetainfo metainfo = TextFieldMetainfo.create(composite, "denominacion",
				new StringValueMetaInfo(""), false, null, false);
		this.denominacionClienteText = (Text) TextFieldHelper.createTextField(metainfo);
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, false, false);
		this.denominacionClienteText.setLayoutData(gridData);
	}

	/**
	 * Este método está pensado para ser sobreescrito por las subclases.
	 */
	protected void configurarFiltros() {
		// do nothing
	}

	@Override
	protected SelectionListener getFilterListener() {
		SelectionListener listener = new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				denominacionCliente = null;
				if (StringUtils.isNotBlank(denominacionClienteText.getText().trim())) {
					denominacionCliente = denominacionClienteText.getText().trim();
				}
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
				denominacionClienteText.setText("");
			}
		};
	}

	@Override
	protected void agregarTabla(Composite parent) {
		setTable(new GenericTable(parent, Cliente.class, instanceKey, null, true));
		IDoubleClickListener tableDoubleClickListener = this.getTableDoubleClickListener();
		if (tableDoubleClickListener != null) {
			getTable().addDoubleClickListener(tableDoubleClickListener);
		}
		ISelectionChangedListener selectionChangedListener = this
				.getTableSelectionChangedListener();
		if (selectionChangedListener != null) {
			getTable().addPostSelectionChangedListener(selectionChangedListener);
		}
	}

	@Override
	protected QueryCompositeBackgroundThread getBackgroundThread() {
		return new QueryCompositeBackgroundThread(getShell().getDisplay(), this) {

			@Override
			protected List doQuery() {
				return ClienteService.consultarClientesPorDenominacion(denominacionCliente);
			}
		};
	}

	@Override
	public GenericTable getTable() {
		return this.clienteQueryTable;
	}

	/**
	 * Cada subclase deberá guardar en una variable privada su propia tabla.
	 * 
	 * @param table
	 */
	protected void setTable(GenericTable table) {
		this.clienteQueryTable = table;
	}

	/**
	 * Define la acción desencadenada por un doble click en un ítem de la lista de Clientes. Por
	 * defecto, esta implementación no realiza nada, las subclases deberán implementarlo si así lo
	 * necesitan.
	 * 
	 * @param clienteVDWrapper
	 *            los datos del ítem Cliente seleccionado.
	 * @return un IDoubleClickListener con el comportamiento deseado.
	 */
	protected IDoubleClickListener getTableDoubleClickListener() {
		return new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				botonAgregarPedido.notifyListeners(SWT.Selection, null);
			}
		};
	}

	protected ISelectionChangedListener getTableSelectionChangedListener() {
		return new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				boolean hayAlgoSeleccionado = getTable().getSelectedElements().size() != 0;
				botonEditarCliente.setEnabled(hayAlgoSeleccionado);
				botonEliminarCliente.setEnabled(hayAlgoSeleccionado);
				botonAgregarPedido.setEnabled(hayAlgoSeleccionado);
			}
		};
	}

	@Override
	protected void agregarBotones() {
		SimpleComposite parent = new SimpleComposite(this.getTable().getControl().getParent(),
				false, 1);
		parent.setLayout(new GridLayout(1, false));
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.CENTER;
		parent.setLayoutData(gridData);

		SimpleComposite panelBotones = new SimpleComposite(parent, false, 1);
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		layout.spacing = 5;
		panelBotones.setLayout(layout);

		this.botonNuevo = createButton(panelBotones, getNuevoClienteButtonSelectionListener(),
				"Nuevo");
		botonNuevo.setEnabled(true);
		this.botonEditarCliente = createButton(panelBotones,
				getEditarClienteButtonSelectionListener(), Constants.CONSULTAS_EDITAR_BUTTON_TEXT);
		this.botonEliminarCliente = createButton(panelBotones,
				getEliminarClienteButtonSelectionListener(), Constants.ELIMINAR_CLIENTE_BUTTON_TEXT);
		this.botonAgregarPedido = createButton(panelBotones,
				getAgregarPedidoButtonSelectionListener(), Constants.AGREGAR_PEDIDO_BUTTON_TEXT);
	}

	/**
	 * Con este método se indica si se permite el botón "Editar". El valor por defecto es
	 * <code>true</code>. Si se desea otro comportamiento, este método deberá ser sobreescrito por
	 * las subclases.
	 * 
	 * @return <code>true</code>, valor por defecto.
	 */
	protected boolean usaBotonEditar() {
		return true;
	}

	private Button createButton(Composite panelBotones, SelectionListener buttonSelectionListener,
			String buttonText) {
		Button button = new Button(panelBotones, SWT.CENTER);
		button.setText(buttonText);
		button.addSelectionListener(buttonSelectionListener);
		button.setEnabled(false);
		return button;
	}

	private SelectionListener getNuevoClienteButtonSelectionListener() {
		return new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				Cliente cliente = new Cliente();
				abrirClienteDetailUpdateDialog(cliente);
				reset();
			}

		};
	}

	private SelectionListener getEditarClienteButtonSelectionListener() {
		return new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				Cliente cliente = (Cliente) getTable().getSelectedElement();
				abrirClienteDetailUpdateDialog(cliente);
				reset();
			}
		};
	}

	private SelectionListener getEliminarClienteButtonSelectionListener() {
		return new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				Cliente cliente = (Cliente) getTable().getSelectedElement();
				ClienteService.eliminar(cliente);
				reset();
			}
		};
	}

	private void abrirClienteDetailUpdateDialog(Cliente cliente) {
		ClienteDetailUpdateDialog dialog = new ClienteDetailUpdateDialog(cliente, false);
		dialog.open();

	}

	private SelectionListener getAgregarPedidoButtonSelectionListener() {
		return new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				Cliente cliente = (Cliente) getTable().getSelectedElement();
				NuevoPedidoDialog dialog = new NuevoPedidoDialog(new Pedido(cliente));
				dialog.open();
			}
		};
	}

	protected ClienteQueryComposite(Composite parent, String name) {
		super(parent, name);
		init();
	}

	@Override
	protected void setMinSizeWindow(ScrolledComposite sc, int width, int height) {
		sc.setMinSize(WIDTH, HEIGHT);
	}

	private static int WIDTH = 1000;

	private static int HEIGHT = 600;

	private static ClienteQueryComposite instance;

	protected Text denominacionClienteText;

	private GenericTable clienteQueryTable;

	protected Button botonNuevo;

	protected Button botonEditarCliente;

	protected Button botonEliminarCliente;

	protected Button botonAgregarPedido;

	protected String denominacionCliente = null;

}