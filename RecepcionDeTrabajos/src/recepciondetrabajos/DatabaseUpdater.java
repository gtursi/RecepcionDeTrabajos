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
 * $Id$
 */
package recepciondetrabajos;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import recepciondetrabajos.service.ApplicationContext;

/**
 * 
 * @author Gabriel Tursi
 * @version $Revision$ $Date$
 */
public class DatabaseUpdater {

	private static final Map<Long, String[]> versions = new LinkedHashMap<Long, String[]>();

	static void updateDatabase() {
		SimpleJdbcTemplate jdbcTemplate = ApplicationContext.getInstance().getBean(
				SimpleJdbcTemplate.class);
		long dbVersion = getAppCurrentVersion(jdbcTemplate);
		for (Map.Entry<Long, String[]> version : versions.entrySet()) {
			if (dbVersion < version.getKey()) {
				String[] updates = version.getValue();
				for (String update : updates) {
					jdbcTemplate.getJdbcOperations().execute(update);
				}
				jdbcTemplate.update("update property set value=? where name='db_version'",
						version.getKey());
			}
		}
	}

	public static long getAppCurrentVersion(SimpleJdbcTemplate jdbcTemplate) {
		long dbVersion;
		try {
			dbVersion = jdbcTemplate.queryForLong(SELECT_DB_VERSION_SQL);
		} catch (BadSqlGrammarException exc) {
			dbVersion = createDBVersion(jdbcTemplate);
		}
		if (dbVersion == 20130319L) {
			dbVersion = 2L;
		}
		return dbVersion;
	}

	public static long createDBVersion(SimpleJdbcTemplate jdbcTemplate) {
		long dbVersion;
		jdbcTemplate.getJdbcOperations().execute(
				"create table property (name varchar2(50) PRIMARY KEY, value varchar2(100))");
		jdbcTemplate.getJdbcOperations().update(
				"insert into property (name,value) values ('db_version','1')");
		dbVersion = jdbcTemplate.queryForLong(SELECT_DB_VERSION_SQL);
		return dbVersion;
	}

	static {
		versions.put(2L, new String[] { "alter table pedido_item add costo number(18,2)",
				"alter table pedido_item add precio number(18,2)" });
		versions.put(3L, new String[] { "alter table pedido_item add comentarios varchar2(255)",
				"alter table pedido_item add entregado boolean DEFAULT false not null",
				"update pedido_item set entregado = true" });
		versions.put(
				4L,
				new String[] {
						"alter table pedido add entregado boolean DEFAULT false not null",
						"update pedido set entregado = false",
						"update pedido set entregado = true where not exists (select 1 from pedido_item pi where pedido_numero = numero and pi.entregado = false)",
						"alter table pedido add primer_item varchar2(250)",
						"update pedido set primer_item = (select detalle from pedido_item where orden = 0 and pedido_numero = numero) where primer_item is null" });
	}

	private static final String SELECT_DB_VERSION_SQL = "select value from property where name = 'db_version'";

}
