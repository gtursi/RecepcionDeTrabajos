package recepciondetrabajos;

import java.lang.reflect.Method;
import java.util.logging.Level;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import recepciondetrabajos.widget.composite.queries.clientes.ClienteQueryComposite;
import recepciondetrabajos.widget.composite.queries.pedidos.GananciaMensualQueryComposite;
import recepciondetrabajos.widget.composite.queries.pedidos.PedidoQueryComposite;

import commons.gui.thread.CustomUncaughtExceptionHandler;
import commons.gui.util.PageHelper;
import commons.gui.widget.composite.QueryComposite;
import commons.logging.AppLogger;

public class MainWindow extends ApplicationWindow {

	public static void main(String[] args) {
		AppLogger.getLogger().log(Level.INFO, "Iniciando aplicación...");
		init();
		getInstance().run();
	}

	private static void init() {
		DatabaseUpdater.updateDatabase();
	}

	public void run() {
		// Don't return from open() until window closes
		setBlockOnOpen(true);
		// Open the main window
		open();
		// Dispose the display
		Display.getCurrent().dispose();
	}

	public static MainWindow getInstance() {
		if (instance == null) {
			instance = new MainWindow();
			Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler());
		}
		return instance;
	}

	public void setDefaultStatusMessage() {
		this.setStatus(Constants.APP_NAME);
	}

	@Override
	protected Control createContents(Composite parent) {
		mainTabFolder = new CTabFolder(parent, SWT.TOP);
		mainTabFolder.setBorderVisible(true);
		mainTabFolder.setVisible(false);
		CTabFolder2Listener listener = new CTabFolder2Adapter() {

			@Override
			public void close(CTabFolderEvent event) {
				if (mainTabFolder.getItemCount() == 1) {
					mainTabFolder.setVisible(false);
				}
			}
		};
		mainTabFolder.addCTabFolder2Listener(listener);
		setStatus(Constants.APP_NAME);
		return parent;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setMaximized(true);
		shell.setText(shellTitleText);
		shell.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_GRAY));
		PageHelper.init(shell);
	}

	@Override
	protected void handleShellCloseEvent() {
		AppLogger.getLogger().log(Level.INFO, "Cerrando aplicación...");
		System.exit(0);
	}

	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager();
		crearMenuConsultas(menuManager);
		return menuManager;
	}

	@Override
	protected boolean canHandleShellCloseEvent() {
		return super.canHandleShellCloseEvent() /* && ExitAction.confirmExit() */;
	}

	protected MainWindow() {
		super(null);
		setShellStyle(SWT.MIN | SWT.MAX | SWT.RESIZE | SWT.CLOSE);
		Image[] images = {
				ImageDescriptor.createFromFile(MainWindow.class, "/images/16x16.png").createImage(),
				ImageDescriptor.createFromFile(MainWindow.class, "/images/32x32.png").createImage(),
				ImageDescriptor.createFromFile(MainWindow.class, "/images/48x48.png").createImage() };
		setDefaultImages(images);
		addMenuBar();
		addStatusLine();
	}

	private void crearMenuConsultas(MenuManager menuManager) {
		MenuManager menu = new MenuManager("&Consultas");
		menuManager.add(menu);

		agregarMenuWithQueryComposite(menu, "C&lientes... @CTRL+L", Constants.CONSULTA_CLIENTES,
				ClienteQueryComposite.class);
		agregarMenuWithQueryComposite(menu, "&Pedidos... @CTRL+P", Constants.CONSULTA_PEDIDOS,
				PedidoQueryComposite.class);
		agregarMenuWithQueryComposite(menu, "&Balance... @CTRL+B",
				Constants.CONSULTA_GANANCIA_MENSUAL, GananciaMensualQueryComposite.class);
	}

	protected void agregarMenuWithQueryComposite(MenuManager menu, String menuText,
			final String tabItemText, final Class<? extends QueryComposite> queryCompositeClass) {
		Action action = new Action(menuText) {

			@Override
			public void run() {
				CTabItem tabItem = getTabItem(tabItemText);
				QueryComposite queryComposite;
				if (tabItem == null) {
					tabItem = new CTabItem(mainTabFolder, SWT.CLOSE);
					queryComposite = getQueryCompositeInstance();
					tabItem.setControl(queryComposite);
					tabItem.setText(tabItemText);
				} else {
					// Limpío los filtros que ya tuviera
					queryComposite = (QueryComposite) tabItem.getControl();
					queryComposite.reset();
				}
				mainTabFolder.setSelection(tabItem);
				mainTabFolder.setVisible(true);
			}

			private QueryComposite getQueryCompositeInstance() {
				QueryComposite queryComposite = null;
				try {
					Method getInstanceMethod = queryCompositeClass.getMethod("getInstance",
							new Class[] { Composite.class });
					queryComposite = (QueryComposite) getInstanceMethod.invoke(null,
							new Object[] { mainTabFolder });
					if (!(queryCompositeClass.isInstance(queryComposite))) {
						AppLogger.getLogger().severe(
								"La clase instanciada no es la esparada "
										+ queryComposite.getClass().getSimpleName());
					}
				} catch (Exception ex) {
					AppLogger.getLogger().log(
							Level.SEVERE,
							"No se ha podido instanciar el siguiente QueryComposite: "
									+ queryCompositeClass.getSimpleName(), ex);
				}
				return queryComposite;
			}
		};
		menu.add(action);
	}

	private CTabItem getTabItem(String tabItemText) {
		CTabItem tabItem = null;
		for (CTabItem item : mainTabFolder.getItems()) {
			if (item.getText().equals(tabItemText)) {
				tabItem = item;
			}
		}
		return tabItem;
	}

	public PreferencePage currentPreferencePage;

	public CTabFolder mainTabFolder;

	private static MainWindow instance;

	private String shellTitleText = "Clientes y Pedidos";

}