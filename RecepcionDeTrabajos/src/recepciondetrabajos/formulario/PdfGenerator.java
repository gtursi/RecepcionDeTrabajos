package recepciondetrabajos.formulario;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

import recepciondetrabajos.MainWindow;
import recepciondetrabajos.domain.Pedido;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import commons.gui.util.FileHelper;

public class PdfGenerator implements IRunnableWithProgress {

	public PdfGenerator(Pedido pedido) {
		this.pedido = pedido;
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		try {
			monitor.beginTask("Generando pedido", 1);

			Document doc = new Document();
			String title = DOCUMENT_TITLE + pedido.getNumero();
			String fileName = String.format(FILE_NAME, pedido.getNumero());
			new PedidoDocument().createPdf(fileName, pedido);

			doc.addTitle(title);
			doc.addCreator("Creator");

			doc.open();
			monitor.worked(1);
			doc.close();
			if (!monitor.isCanceled()) {
				monitor.worked(1);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			throw new InvocationTargetException(exc, exc.toString());
		} finally {
			monitor.done();
			if (monitor.isCanceled()) {
				throw new InterruptedException("Se ha cancelado la generación de los documentos.");
			}
		}
	}

	public static void generate(Pedido pedido) {
		Shell shell = MainWindow.getInstance().getShell();
		try {
			new ProgressMonitorDialog(shell).run(true, true, new PdfGenerator(pedido));
		} catch (InvocationTargetException exc) {
			MessageDialog.openError(shell, "Error", exc.getMessage());
		} catch (InterruptedException exc) {
			MessageDialog.openInformation(shell, "Cancelado", exc.getMessage());
		}
	}

	private Pedido pedido;

	private static final String DOCUMENT_TITLE = "Pedido";

	private static final String FILE_NAME = FileHelper.OUTPUT_DIR + "/pedido-%s.pdf";

}
