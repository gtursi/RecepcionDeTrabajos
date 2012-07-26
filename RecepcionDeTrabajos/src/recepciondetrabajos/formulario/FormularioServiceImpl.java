package recepciondetrabajos.formulario;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import recepciondetrabajos.domain.Pedido;
import recepciondetrabajos.service.ApplicationContext;

import commons.gui.util.FileHelper;

@Service
public abstract class FormularioServiceImpl {

	public static void imprimirFormulario(Pedido pedido) {
		Connection con = DataSourceUtils.getConnection(dataSource);
		String destFile = DEST_FILE_PREFIX + "/pedido-" + pedido.getNumero() + ".pdf";
		Map<String, Object> jasperParams = createParameters(pedido);
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParams, con);
			JasperExportManager.exportReportToPdfFile(jasperPrint, destFile);
			System.out.println("Se ha impreso " + destFile);
		} catch (JRException exc) {
			throw new RuntimeException("Error al generar el formulario " + destFile, exc);
		}
		DataSourceUtils.releaseConnection(con, dataSource);
	}

	private static Map<String, Object> createParameters(Pedido pedido) {
		Map<String, Object> jasperParams = new HashMap<String, Object>();
		jasperParams.put("NUMERO_PEDIDO", pedido.getNumero());
		jasperParams.put("SUBREPORT_DIR", "reports/");
		Locale locale = new Locale("es", "AR");
		jasperParams.put(JRParameter.REPORT_LOCALE, locale);
		return jasperParams;
	}

	private static final DataSource dataSource = ApplicationContext.getInstance().getBean(
			DataSource.class);

	private static final String BASE_PATH = "classpath:reports/";

	private static final String EXTENSION = ".jrxml";

	private static final String DEST_FILE_PREFIX = FileHelper.OUTPUT_DIR;

	private static JasperReport jasperReport;

	public static void init() {
		InputStream jasperResource;
		String reportName = BASE_PATH + "formulario" + EXTENSION;
		try {
			jasperResource = new DefaultResourceLoader().getResource(reportName).getInputStream();
		} catch (IOException exc) {
			throw new RuntimeException("Error al intentar abrir " + reportName, exc);
		}
		try {
			jasperReport = JasperCompileManager.compileReport(jasperResource);
		} catch (JRException exc) {
			throw new RuntimeException("Error al compilar el formulario " + reportName, exc);
		}
	}
}