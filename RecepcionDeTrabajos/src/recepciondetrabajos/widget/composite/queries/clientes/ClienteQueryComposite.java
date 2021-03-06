package recepciondetrabajos.widget.composite.queries.clientes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import recepciondetrabajos.Constants;
import recepciondetrabajos.MainWindow;
import recepciondetrabajos.domain.Cliente;
import recepciondetrabajos.domain.Pedido;
import recepciondetrabajos.service.ClienteService;
import recepciondetrabajos.widget.composite.queries.pedidos.PedidoQueryComposite;
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
		denominacionClienteText = agregarFiltroString(grupoFiltros, "denominacion");
		direccionClienteText = agregarFiltroString(grupoFiltros, "direccion");
		configurarFiltros();
	}

	@Override
	protected List<Control> getFilterControls() {
		List<Control> controls = new ArrayList<Control>();
		controls.add(this.denominacionClienteText);
		controls.add(this.direccionClienteText);
		return controls;
	}

	protected Text agregarFiltroString(Composite composite, String labelKey) {
		TextFieldMetainfo metainfo = TextFieldMetainfo.create(composite, labelKey,
				new StringValueMetaInfo(""), false, null, false);
		Text text = (Text) TextFieldHelper.createTextField(metainfo);
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, false, false);
		text.setLayoutData(gridData);
		return text;
	}

	/**
	 * Este m�todo est� pensado para ser sobreescrito por las subclases.
	 */
	protected void configurarFiltros() {
		// do nothing
	}

	@Override
	protected SelectionListener getFilterListener() {
		SelectionListener listener = new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				denominacionCliente = textToSearch(denominacionClienteText);
				direccionCliente = textToSearch(direccionClienteText);
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
				direccionClienteText.setText("");
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
		agregarMenuContextual();

	}

	private void agregarMenuContextual() {
		final MenuManager popManager = new MenuManager();
		IAction menuAction = new Action("Ver pedidos...") {

			public void run() {
				Cliente cliente = (Cliente) getTable().getSelectedElement();
				PedidoQueryComposite.getInstance(getParent()).setFiltroCodigoCliente(
						cliente.getCodigo());
				MainWindow.getInstance().showQueryPedidos(popManager);
			}
		};
		popManager.add(menuAction);
		Table table = getTable().getTable();
		Menu menu = popManager.createContextMenu(table);
		table.setMenu(menu);
	}

	@Override
	protected QueryCompositeBackgroundThread getBackgroundThread() {
		return new QueryCompositeBackgroundThread(getShell().getDisplay(), this) {

			@Override
			protected List doQuery() {
				return ClienteService.consultarClientes(denominacionCliente, direccionCliente);
			}
		};
	}

	@Override
	public GenericTable getTable() {
		return this.clienteQueryTable;
	}

	/**
	 * Cada subclase deber� guardar en una variable privada su propia tabla.
	 * 
	 * @param table
	 */
	protected void setTable(GenericTable table) {
		this.clienteQueryTable = table;
	}

	/**
	 * Define la acci�n desencadenada por un doble click en un �tem de la lista de Clientes. Por
	 * defecto, esta implementaci�n no realiza nada, las subclases deber�n implementarlo si as� lo
	 * necesitan.
	 * 
	 * @param clienteVDWrapper
	 *            los datos del �tem Cliente seleccionado.
	 * @return un IDoubleClickListener con el comportamiento deseado.
	 */
	protected IDoubleClickListener getTableDoubleClickListener() {
		return new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				botonAgregarPedido.notifyListeners(SWT.Selection, null);
			}
		};
	}

	protected IDoubleClickListener getTableRightClickListener() {
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
	 * Con este m�todo se indica si se permite el bot�n "Editar". El valor por defecto es
	 * <code>true</code>. Si se desea otro comportamiento, este m�todo deber� ser sobreescrito por
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
				if (cliente != null) {
					NuevoPedidoDialog dialog = new NuevoPedidoDialog(new Pedido(cliente));
					dialog.open();
				}
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

	private String textToSearch(Text text) {
		if (StringUtils.isNotBlank(text.getText().trim())) {
			return text.getText().trim();
		}
		return "";
	}

	private static int WIDTH = 1000;

	private static int HEIGHT = 600;

	private static ClienteQueryComposite instance;

	protected Text denominacionClienteText;

	protected Text direccionClienteText;

	private GenericTable clienteQueryTable;

	protected Button botonNuevo;

	protected Button botonEditarCliente;

	protected Button botonEliminarCliente;

	protected Button botonAgregarPedido;

	protected String denominacionCliente = "";

	protected String direccionCliente = "";

}