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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import recepciondetrabajos.domain.Pedido;

import commons.gui.util.FileHelper;

@Service
public class FormularioServiceImpl {

	public void imprimirFormulario(Pedido pedido) throws IOException, JRException {

		final String reporteName = BASE_PATH + "formulario" + EXTENSION;

		InputStream jasperResource = new DefaultResourceLoader().getResource(reporteName)
				.getInputStream();

		// FIXME no haria falta compilar ningun reporte
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperResource);

		Map<String, Object> jasperParams = createParameters(pedido);

		Connection con = DataSourceUtils.getConnection(dataSource);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParams, con);

		String destFile = DEST_FILE_PREFIX + "/pedido-" + pedido.getNumero();

		JasperExportManager.exportReportToPdfFile(jasperPrint, destFile);

		DataSourceUtils.releaseConnection(con, dataSource);

	}

	private Map<String, Object> createParameters(Pedido pedido) {
		Map<String, Object> jasperParams = new HashMap<String, Object>();
		jasperParams.put("pedido", pedido);
		jasperParams.put("SUBREPORT_DIR", "reports/");
		Locale locale = new Locale("es", "AR");
		jasperParams.put(JRParameter.REPORT_LOCALE, locale);
		return jasperParams;
	}

	@Autowired
	private DataSource dataSource;

	private static final String BASE_PATH = "classpath:reports/";

	private static final String EXTENSION = ".jrxml";

	private String DEST_FILE_PREFIX = FileHelper.OUTPUT_DIR;

}