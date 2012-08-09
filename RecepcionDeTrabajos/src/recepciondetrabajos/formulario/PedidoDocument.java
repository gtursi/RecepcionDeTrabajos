package recepciondetrabajos.formulario;

import static com.itextpdf.text.Rectangle.BOTTOM;
import static com.itextpdf.text.Rectangle.LEFT;
import static com.itextpdf.text.Rectangle.RIGHT;
import static com.itextpdf.text.Rectangle.TOP;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import recepciondetrabajos.domain.Cliente;
import recepciondetrabajos.domain.Pedido;
import recepciondetrabajos.domain.PedidoItem;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PedidoDocument {

	private static final int MAX_ROWS = 3;

	/** The resulting PDF file. */
	public static final String RESULT = "pedido-prueba.pdf";

	public static void main(String[] args) throws IOException, DocumentException,
			InterruptedException {
		Pedido pedido2 = pedidoDummy();
		new PedidoDocument().createPdf(RESULT, pedido2);
	}

	private static Pedido pedidoDummy() {
		Pedido pedido2 = new Pedido();
		Cliente cliente = new Cliente();
		cliente.setDenominacion("Denominacion del Cliente para prueba");
		cliente.setTelefono("156-555-4575 / 4555-3121");
		cliente.setDireccion("Aristobulo del valle 3333 piso 4 oficina 2");
		cliente.setEmail("emaildelcliente@empresadelcliente.com.ar");
		pedido2.setCliente(cliente);
		PedidoItem item = new PedidoItem();
		item.setCantidad(9999);
		item.setOrden(1);
		item.setDetalle("reparacion de PC cambio de disco, ampliacion de memoria");
		item.setObservaciones("tener en cuenta antiguedad de la maquina, reemplazar fuente de ser necesario");
		pedido2.getItems().add(item);
		return pedido2;
	}

	private Pedido pedido;

	public void createPdf(String filename, Pedido pedido) throws IOException, DocumentException,
			InterruptedException {
		closeFile();
		this.pedido = pedido;
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(filename));
		document.open();
		PdfPTable table = mainTable();
		Element footer = footer();
		document.add(table);
		document.add(footer);
		document.add(table);
		document.add(footer);
		document.close();
		showFile(filename);
	}

	public PdfPTable mainTable() {
		float[] columnWidths = { 1f, 3f, 4f };
		PdfPTable table = new PdfPTable(columnWidths);
		table.getDefaultCell().setIndent(10f);
		table.setWidthPercentage(90);
		table.setSpacingAfter(3f);
		addNombreEmpresa(table);
		addFecha(table);
		addDatosEmpresa(table);
		addNroPedido(table);
		addLeyendaNoFactura(table);
		addDatosCliente(table);
		addColumnHeader(table);
		addDetail(table);
		return table;
	}

	private void addDatosCliente(PdfPTable table) {
		addDenominacionCliente(table);
		addTelefonoCliente(table);
		addDireccionCliente(table);
		addEmailCliente(table);
	}

	private void addDenominacionCliente(PdfPTable table) {
		PdfPCell cell = createCell("Sr.: " + pedido.getCliente().getDenominacion(), 2, 1, LEFT);
		cell.setPaddingTop(3f);
		table.addCell(cell);
	}

	private void addTelefonoCliente(PdfPTable table) {
		PdfPCell cell = createCell("Teléfono: " + pedido.getCliente().getTelefono(), 2, 1, RIGHT
				| TOP);
		cell.setPaddingTop(3f);
		table.addCell(cell);
	}

	private void addDireccionCliente(PdfPTable table) {
		PdfPCell cell = createCell("Dirección: " + pedido.getCliente().getDireccion(), 2, 1, LEFT
				| BOTTOM);
		cell.setPaddingBottom(5f);
		cell.setRightIndent(3f);
		table.addCell(cell);
	}

	private void addEmailCliente(PdfPTable table) {
		PdfPCell cell = createCell("E-mail: " + pedido.getCliente().getEmail(), 2, 1, RIGHT
				| BOTTOM);
		cell.setPaddingBottom(5f);
		table.addCell(cell);
	}

	private void addDatosEmpresa(PdfPTable table) {
		PdfPCell cell;
		cell = new PdfPCell(
				new Phrase(
						"La Pampa 5000\nCiudad Autonoma de Bs.As.\nTel: 4444-4444\nEmail: meljaz@hotmail.com",
						pequeña));
		cell.setColspan(2);
		cell.setRowspan(2);
		cell.setBorder(LEFT | RIGHT | BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setFixedHeight(42f);
		table.addCell(cell);
	}

	private void addNombreEmpresa(PdfPTable table) {
		PdfPCell cell = new PdfPCell(new Phrase("Meljaz", titulo1));
		cell.setColspan(2);
		cell.setRowspan(1);
		cell.setBorder(LEFT | TOP | RIGHT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setFixedHeight(20f);
		table.addCell(cell);
	}

	private void addFecha(PdfPTable table) {
		PdfPCell cell = new PdfPCell(new Phrase("Fecha de recepción: "
				+ DatetimeHelper.getDateAsString(), normal));
		cell.setBorder(RIGHT | TOP);
		cell.setPadding(9f);
		table.addCell(cell);
	}

	private void addNroPedido(PdfPTable table) {
		PdfPCell cell = new PdfPCell(new Phrase("Nro. Pedido: " + getNroPedidoFormatted(), normal));
		cell.setBorder(RIGHT);
		cell.setPadding(5f);
		cell.setPaddingLeft(10f);
		cell.setPaddingTop(5f);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(cell);
	}

	private String getNroPedidoFormatted() {
		return String.format("%05d", pedido.getNumero());
	}

	private void addLeyendaNoFactura(PdfPTable table) {
		PdfPCell cell = new PdfPCell(new Phrase("Documento no válido como factura", pequeña));
		cell.setBorder(RIGHT);
		cell.setPadding(5f);
		cell.setPaddingLeft(10f);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(cell);
	}

	private Element footer() {
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(90);
		table.setSpacingAfter(25f);

		Paragraph p = new Paragraph(
				"Recibimos los productos detallados en este formulario para su posterior reparación.\r\n"
						+ "Todas las reparaciones están condicionadas a la disponibilidad de repuestos.\r\n"
						+ "Pasados 60 días de no retirado el producto, el mismo pasará a disponibilidad de la empresa.\r\n"
						+ "La empresa no se responsabiliza por pérdidas de información.\r\n"
						+ "La empresa no se responsabiliza por la procedencia de la mercadería ingresada.\r\n"
						+ "La empresa no se responsabiliza por robo, daño o pérdida de los artículos recibidos.",
				pequeña);
		table.addCell(p);
		return table;
	}

	private void addDetail(PdfPTable table) {
		table.getDefaultCell().setFixedHeight(25f);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_MIDDLE);
		table.getDefaultCell().setPaddingLeft(0f);
		for (PedidoItem item : pedido.getItems()) {
			addItem(table, item);
		}
		for (int cantFilas = pedido.getItems().size(); cantFilas < MAX_ROWS; cantFilas++) {
			table.addCell("");
			table.addCell("");
			table.addCell("");
		}
	}

	private void addItem(PdfPTable table, PedidoItem item) {
		PdfPCell cell = new PdfPCell(new Phrase(item.getCantidad().toString(), normal));
		table.addCell(cell);
		table.addCell(new PdfPCell(new Phrase(item.getDetalle(), normal)));
		table.addCell(new PdfPCell(new Phrase(item.getObservaciones(), normal)));
	}

	private void addColumnHeader(PdfPTable table) {
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell("Cant.");
		table.addCell("Detalle");
		table.addCell("Observaciones");
	}

	private static PdfPCell createCell(String text, int colspan, int rowspan, Integer border) {
		return createCell(normal, text, colspan, rowspan, border);
	}

	private static PdfPCell createCell(Font font, String text, int colspan, int rowspan,
			Integer border) {
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setIndent(5f);
		cell.setColspan(colspan);
		cell.setRowspan(rowspan);
		if (border != null) {
			cell.setBorder(border);
		}
		return cell;
	}

	private void showFile(String fileName) throws IOException, InterruptedException {
		fileName = new File(fileName).getCanonicalPath();
		fileName = "\"" + fileName + "\"";
		String[] args = { "cmd.exe", "/c", "start", "\"\"", fileName };
		Runtime.getRuntime().exec(args).waitFor();
	}

	private void closeFile() throws InterruptedException, IOException {
		if (System.getProperty("os.name").contains("Windows")) {
			Runtime.getRuntime().exec("taskkill /F /IM AcroRd32.exe").waitFor();
		}
	}

	private static Font titulo1 = new Font(Font.FontFamily.HELVETICA, 25, Font.BOLD);

	private static Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12);

	private static Font pequeña = new Font(Font.FontFamily.HELVETICA, 8);
}
