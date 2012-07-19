/*
 * Licencia de Caja de Valores S.A., Versi�n 1.0
 *
 * Copyright (c) 2006 Caja de Valores S.A.
 * 25 de Mayo 362, Ciudad Aut�noma de Buenos Aires, Rep�blica Argentina
 * Todos los derechos reservados.
 *
 * Este software es informaci�n confidencial y propietaria de Caja de Valores S.A. ("Informaci�n
 * Confidencial"). Usted no divulgar� tal Informaci�n Confidencial y la usar� solamente de acuerdo a
 * los t�rminos del acuerdo de licencia que posee con Caja de Valores S.A.
 */

/*
 * $Id: ClientesServiceHelper.java,v 1.35 2010/12/23 14:21:38 cvsmvera Exp $
 */
package recepciondetrabajos.service;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import recepciondetrabajos.domain.Cliente;

/**
 * 
 * @author Gabriel Tursi
 * @version $Revision: 1.35 $ $Date: 2010/12/23 14:21:38 $
 */
public abstract class ClienteService {

	public static Cliente consultarCliente(Long codigo) {
		Object[] args = new Object[] { codigo };
		return (Cliente) jdbcTemplate.queryForObject("select * from cliente where codigo=?", args,
				new BeanPropertyRowMapper());
	}

	@SuppressWarnings("unchecked")
	public static List<Cliente> consultarClientesPorDenominacion(String denominacion) {
		return jdbcTemplate
				.query("select * from cliente", new BeanPropertyRowMapper(Cliente.class));
	}

	public static void persistir(Cliente cliente) {
		if (cliente.isNuevo()) {
			insertar(cliente);
		} else {
			actualizar(cliente);
		}
	}

	private static void insertar(Cliente cliente) {
		Object[] args = { cliente.getDenominacion(), cliente.getDireccion(), cliente.getEmail() };
		jdbcTemplate
				.update("insert into cliente (codigo, denominacion, direccion, email) values ((select nvl((select max(codigo) from cliente),0) + 1 from dual),?,?,?)",
						args);
	}

	private static void actualizar(Cliente cliente) {
		Object[] args = { cliente.getDenominacion(), cliente.getDireccion(), cliente.getEmail(),
				cliente.getCodigo() };
		jdbcTemplate.update(
				"update cliente set denominacion = ?, direccion = ?, email = ? where codigo = ?",
				args);
	}

	public static void eliminar(Cliente cliente) {
		Object[] args = { cliente.getCodigo() };
		jdbcTemplate.update("delete cliente where codigo = ?", args);
	}

	private static final JdbcTemplate jdbcTemplate = ApplicationContext.getInstance().getBean(
			JdbcTemplate.class);

}