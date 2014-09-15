package recepciondetrabajos.service;

import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import recepciondetrabajos.domain.Cliente;

public abstract class ClienteService {

	public static Cliente consultarCliente(Long codigo) {
		Object[] args = new Object[] { codigo };
		return simpleJdbcTemplate.queryForObject("select * from cliente where codigo=?",
				ParameterizedBeanPropertyRowMapper.newInstance(Cliente.class), args);
	}

	@SuppressWarnings("unchecked")
	public static List<Cliente> consultarClientes(String denominacion, String direccion) {
		return simpleJdbcTemplate
				.query("select * from cliente where lower(denominacion) like ? and lower(direccion) like ?",
						ParameterizedBeanPropertyRowMapper.newInstance(Cliente.class),
						stringToSearch(denominacion), stringToSearch(direccion));
	}

	public static void persistir(Cliente cliente) {
		if (cliente.isNuevo()) {
			insertar(cliente);
		} else {
			actualizar(cliente);
		}
	}

	private static void insertar(Cliente cliente) {
		Object[] args = { cliente.getDenominacion(), cliente.getDireccion(), cliente.getTelefono(),
				cliente.getEmail() };
		simpleJdbcTemplate
				.update("insert into cliente (codigo, denominacion, direccion, telefono, email) values ((select nvl((select max(codigo) from cliente),0) + 1 from dual),?,?,?,?)",
						args);
	}

	private static void actualizar(Cliente cliente) {
		Object[] args = { cliente.getDenominacion(), cliente.getDireccion(), cliente.getTelefono(),
				cliente.getEmail(), cliente.getCodigo() };
		simpleJdbcTemplate
				.update("update cliente set denominacion = ?, direccion = ?, telefono = ?, email = ? where codigo = ?",
						args);
	}

	public static void eliminar(Cliente cliente) {
		Object[] args = { cliente.getCodigo() };
		simpleJdbcTemplate.update("delete cliente where codigo = ?", args);
	}

	private static String stringToSearch(String string) {
		return "%" + string.trim().toLowerCase() + "%";
	}

	private static final SimpleJdbcTemplate simpleJdbcTemplate = ApplicationContext.getInstance()
			.getBean(SimpleJdbcTemplate.class);

}