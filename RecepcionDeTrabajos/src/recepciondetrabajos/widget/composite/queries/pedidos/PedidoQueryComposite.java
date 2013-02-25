package recepciondetrabajos.widget.composite.queries.pedidos;

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
import recepciondetrabajos.domain.Pedido;
import recepciondetrabajos.service.PedidoService;
import recepciondetrabajos.widget.dialog.pedido.NuevoPedidoDialog;

import commons.gui.table.GenericTable;
import commons.gui.thread.QueryCompositeBackgroundThread;
import commons.gui.util.TextFieldHelper;
import commons.gui.widget.composite.QueryComposite;
import commons.gui.widget.composite.SimpleComposite;
import commons.gui.widget.creation.metainfo.StringValueMetaInfo;
import commons.gui.widget.creation.metainfo.TextFieldMetainfo;

public class PedidoQueryComposite extends QueryComposite {

	public static PedidoQueryComposite getInstance(Composite parent) {
		if (instance == null) {
			instance = new PedidoQueryComposite(parent, Constants.CONSULTA_PEDIDOS);
		}
		instance.doQuery();
		return instance;
	}

	@Override
	protected void agregarFiltrosEspecificos(Group grupoFiltros) {
		agregarFiltroDenominacionCliente(grupoFiltros);
		agregarFiltroCodigoCliente(grupoFiltros);
	}

	@Override
	protected List<Control> getFilterControls() {
		List<Control> controls = new ArrayList<Control>();
		controls.add(this.denominacionClienteText);
		return controls;
	}

	protected void agregarFiltroDenominacionCliente(Composite composite) {
		TextFieldMetainfo metainfo = TextFieldMetainfo.create(composite, "cliente",
				new StringValueMetaInfo(""), false, null, false);
		this.denominacionClienteText = (Text) TextFieldHelper.createTextField(metainfo);
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, false, false);
		this.denominacionClienteText.setLayoutData(gridData);
	}

	protected void agregarFiltroCodigoCliente(Composite composite) {
		// TODO agregar filtro codigo de cliente en Pedidos
	}

	@Override
	protected SelectionListener getFilterListener() {
		SelectionListener listener = new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				denominacionCliente = "";
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
		setTable(new GenericTable(parent, Pedido.class, instanceKey, null, true));
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
				return PedidoService.consultarPedidos(null, denominacionCliente);
			}
		};
	}

	@Override
	public GenericTable getTable() {
		return this.pedidoQueryTable;
	}

	/**
	 * Cada subclase deberá guardar en una variable privada su propia tabla.
	 * 
	 * @param table
	 */
	protected void setTable(GenericTable table) {
		this.pedidoQueryTable = table;
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
				botonEditar.notifyListeners(SWT.Selection, null);
			}
		};
	}

	protected ISelectionChangedListener getTableSelectionChangedListener() {
		return new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				boolean hayAlgoSeleccionado = getTable().getSelectedElements().size() != 0;
				botonEditar.setEnabled(hayAlgoSeleccionado);
				botonEliminarPedido.setEnabled(hayAlgoSeleccionado);
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

		this.botonEditar = createButton(panelBotones, getEditarButtonSelectionListener(),
				Constants.CONSULTAS_EDITAR_BUTTON_TEXT);
		this.botonEliminarPedido = createButton(panelBotones,
				getEliminarPedidoButtonSelectionListener(), Constants.ELIMINAR_PEDIDO_BUTTON_TEXT);

	}

	private Button createButton(Composite panelBotones, SelectionListener buttonSelectionListener,
			String buttonText) {
		Button button = new Button(panelBotones, SWT.CENTER);
		button.setText(buttonText);
		button.addSelectionListener(buttonSelectionListener);
		button.setEnabled(false);
		return button;
	}

	private SelectionListener getEditarButtonSelectionListener() {
		return new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				Pedido pedido = (Pedido) getTable().getSelectedElement();
				if (pedido != null) {
					pedido = PedidoService.obtenerPedido(pedido.getNumero(), pedido.getCliente()
							.getCodigo());
					NuevoPedidoDialog dialog = new NuevoPedidoDialog(pedido);
					dialog.open();
				}
			}
		};
	}

	private SelectionListener getEliminarPedidoButtonSelectionListener() {
		return new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				Pedido pedido = (Pedido) getTable().getSelectedElement();
				PedidoService.eliminar(pedido);
				reset();
			}
		};
	}

	protected PedidoQueryComposite(Composite parent, String name) {
		super(parent, name);
		init();
	}

	@Override
	protected void setMinSizeWindow(ScrolledComposite sc, int width, int height) {
		sc.setMinSize(WIDTH, HEIGHT);
	}

	private static int WIDTH = 1000;

	private static int HEIGHT = 600;

	private static PedidoQueryComposite instance;

	protected Text denominacionClienteText;

	private GenericTable pedidoQueryTable;

	protected Button botonEditar;

	protected Button botonEliminarPedido;

	protected String denominacionCliente = "";

}