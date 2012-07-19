import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

@Service
public class FacturacionServiceImpl {

	public void construirDocumentosFacturacion(Calendar periodo) throws IOException, JRException {

		logger.info("BEGIN Facturacion");

		construirDocumentos(periodo, null, IMPRESION_ORIGINAL);
		construirDocumentos(periodo, null, IMPRESION_DUPLICADO);

		logger.info("END Facturacion");

	}

	public void construirDocumentos(Calendar periodo, Moneda moneda, String impresion)
			throws IOException, JRException {

		final String reporteName = BASE_PATH + "facturacion" + EXTENSION;

		InputStream jasperResource = new DefaultResourceLoader().getResource(reporteName)
				.getInputStream();

		// FIXME no haria falta compilar ningun reporte
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperResource);

		Map<String, Object> jasperParams = createParameters(periodo, moneda, impresion);

		Connection con = DataSourceUtils.getConnection(dataSource);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParams, con);

		String destFile = DEST_FILE_PREFIX
				+ printerHelper.formatearNombreArchivo("/facturacion-" + impresion + "-"
						+ moneda.name());

		JasperExportManager.exportReportToPdfFile(jasperPrint, destFile);

		DataSourceUtils.releaseConnection(con, dataSource);

		logger.info("Se creó el archivo " + destFile);
	}

	public void construirDocumentosFactura(Calendar periodo, Moneda moneda) throws IOException,
			JRException {

		final String reporteName = BASE_PATH + "facturacion" + EXTENSION;

		InputStream jasperResource = new DefaultResourceLoader().getResource(reporteName)
				.getInputStream();

		JasperReport jasperReport = JasperCompileManager.compileReport(jasperResource);

		Map<String, Object> jasperParams = createParameters(periodo, moneda, "");

		Connection con = DataSourceUtils.getConnection(dataSource);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParams, con);

		String destFile = DEST_FILE_PREFIX + "/facturacion-" + "0"
				+ (periodo.get(Calendar.MONTH) + 1) + "-" + periodo.get(Calendar.YEAR) + "-"
				+ moneda.name() + ".pdf";

		JasperExportManager.exportReportToPdfFile(jasperPrint, destFile);

		DataSourceUtils.releaseConnection(con, dataSource);

		logger.info("Se creó el archivo " + destFile);

	}

	private Map<String, Object> createParameters(Calendar periodo, Moneda moneda, String impresion) {
		Map<String, Object> jasperParams = new HashMap<String, Object>();
		jasperParams.put("periodo", new Date(periodo.getTimeInMillis()));
		jasperParams.put("SUBREPORT_DIR", "reports/");
		jasperParams.put("IS_PRODUCCION", esProduccion);
		jasperParams.put("IMPRESION", impresion);
		Locale locale = new Locale("es", "AR");
		jasperParams.put(JRParameter.REPORT_LOCALE, locale);

		return jasperParams;
	}

	public void ejecutarProcesoEmisionDocumentos() {

		try {
			ControlBatch control = controlBatchService.findUltimoPeriodo();
			Calendar periodo = control.getPeriodo();

			SecurityUtils.setUserContext(USUARIO_BATCH);

			PuntoDeVenta puntoDeVenta = puntoDeVentaService.findPuntoDeVentaActivoNoElectronico();

			logger.info("BEGIN generar Facturacion");
			long startFacturacion = System.currentTimeMillis();

			logger.info("BEGIN Autorizar documentos Fiscales");
			logger.info("Punto de venta " + puntoDeVenta.getNumero());
			documentoFiscalService.autorizarDocumentosFiscales(periodo, puntoDeVenta);
			logger.info("END Autorizar documentos Fiscales");

			logger.info("BEGIN Emitir documentos Fiscales");
			documentoFiscalService.updateFechaEmision(periodo, puntoDeVenta);
			construirDocumentosFacturacion(periodo);
			logger.info("END Emitir documentos Fiscales");

			long endFacturacion = System.currentTimeMillis();
			logger.info("END generar Facturacion in: " + (endFacturacion - startFacturacion)
					+ " ms");

			logger.info("BEGIN exportacion SAP ");
			long startSap = System.currentTimeMillis();
			exportSapService.exportarFacturacionSap(periodo, puntoDeVenta);
			long endSap = System.currentTimeMillis();
			logger.info("END exportacion SAP in: " + (endSap - startSap) + " ms");

			logger.info("BEGIN generarListados");
			String REPORT_NAME;
			long startListados = System.currentTimeMillis();
			REPORT_NAME = "LB54 -Consultar Liquidacion de Instrucciones deOtras Depositarias";
			reportesService.generarReporte(REPORT_NAME, periodo);
			REPORT_NAME = "LB20 -Consultar Instrucciones CBL por Cliente";
			reportesService.generarReporte(REPORT_NAME, periodo);
			REPORT_NAME = "LB18 -Consultar Cobro de Cheque Custodia por Cliente";
			reportesService.generarReporte(REPORT_NAME, periodo);
			REPORT_NAME = "LB16 -Consultar Cheques de Recupero de Gastos de Cobro por Cliente";
			reportesService.generarReporte(REPORT_NAME, periodo);

			REPORT_NAME = "ETIQUETAS";
			reportesService.generarReporte(REPORT_NAME, periodo);

			lb45ReporteService.generarReporte(periodo);

			long endListados = System.currentTimeMillis();
			logger.info("END generarListados in: " + (endListados - startListados) + " ms");

			logger.info("BEGIN Transferencia de Archivos ");
			long startTransferenciaArchivos = System.currentTimeMillis();

			try {
				transferenciaArchivosService.transferirArchivosFtp();
			} catch (Exception exc) {
				logger.error("Error al transferir archivos ");
				logger.error(exc.getMessage());
			}

			long endTransferenciaArchivos = System.currentTimeMillis();
			logger.info("END Transferencia de Archivos en: "
					+ (endTransferenciaArchivos - startTransferenciaArchivos) + " ms");

		} catch (Exception e) {

			throw new ServiceException(e.getMessage());

		}
	}

	@Autowired
	@Qualifier("dataSourceSFAC")
	private DataSource dataSource;

	private static final String BASE_PATH = "classpath:reports/";

	private static final String EXTENSION = ".jrxml";

	private static final String IMPRESION_ORIGINAL = "ORIGINAL";

	private static final String IMPRESION_DUPLICADO = "DUPLICADO";

	@Autowired
	@Qualifier("pathPreFacExport")
	private String DEST_FILE_PREFIX;

	@Autowired
	@Qualifier("marcaAguaProduccion")
	private Boolean esProduccion;

	@Autowired
	private PrinterHelper printerHelper;

	@Autowired
	private DocumentoFiscalService documentoFiscalService;

	@Autowired
	private PuntoDeVentaService puntoDeVentaService;

	@Autowired
	private ExportSapService exportSapService;

	@Autowired
	private ReportesService reportesService;

	@Autowired
	private TransferenciaArchivosService transferenciaArchivosService;

	@Autowired
	private ControlBatchService controlBatchService;

	@Autowired
	private LB45ReporteService lb45ReporteService;

	private static final String USUARIO_BATCH = "BATCH";

	private static final Log logger = LogFactory.getLog(ClassUtils.getCurrentClass());

}