package recepciondetrabajos.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import recepciondetrabajos.domain.Cliente;
import recepciondetrabajos.domain.Pedido;
import recepciondetrabajos.domain.PedidoItem;

import commons.util.DateUtils;

public class PedidoService {

	public static void crearPedido(Pedido nuevoPedido) {
		long numeroPedido = jdbcTemplate.queryForLong(NUEVO_NRO_PEDIDO);
		nuevoPedido.setNumero(numeroPedido);
		Object[] argsPedido = { numeroPedido, nuevoPedido.getCliente().getCodigo(),
				nuevoPedido.getFecha() };
		jdbcTemplate.update(INSERT_PEDIDO, argsPedido);
		insertarPedidoItems(nuevoPedido);
	}

	public static void actualizarPedido(Pedido pedido) {
		Long numeroPedido = pedido.getNumero();
		jdbcTemplate.update(DELETE_PEDIDO_ITEM, numeroPedido);
		insertarPedidoItems(pedido);
	}

	@SuppressWarnings("unchecked")
	public static List<Pedido> consultarPedidos(Integer codigoCliente, String denominacionCliente) {
		return jdbcTemplate
				.query("select p.*, c.codigo, c.denominacion from pedido p join cliente c on p.codigo_cliente = c.codigo",
						new ParameterizedRowMapper() {

							public Pedido mapRow(ResultSet rs, int rowNum) throws SQLException {
								Cliente cliente = new Cliente();
								cliente.setCodigo(rs.getLong("codigo"));
								cliente.setDenominacion(rs.getString("denominacion"));
								Pedido pedido = new Pedido(cliente);
								pedido.setNumero(rs.getLong("numero"));
								pedido.setFecha(DateUtils.toCalendar(rs.getDate("fecha")));
								return pedido;
							}
						});

	}

	public static Pedido obtenerPedido(Long numero, Long codigoCliente) {
		Pedido pedido = jdbcTemplate.queryForObject("select * from pedido where numero=?",
				ParameterizedBeanPropertyRowMapper.newInstance(Pedido.class), numero);
		pedido.setCliente(ClienteService.consultarCliente(codigoCliente));
		List<PedidoItem> items = jdbcTemplate.query(
				"select * from pedido_item where pedido_numero = ?",
				ParameterizedBeanPropertyRowMapper.newInstance(PedidoItem.class), numero);
		pedido.setItems(items);
		return pedido;
	}

	public static final SimpleJdbcTemplate jdbcTemplate = ApplicationContext.getInstance().getBean(
			SimpleJdbcTemplate.class);

	private static void insertarPedidoItems(Pedido pedido) {
		for (PedidoItem item : pedido.getItems()) {
			Map<String, Object> argsItem = new HashMap<String, Object>();
			argsItem.put("numero_pedido", pedido.getNumero());
			argsItem.put("orden", pedido.getItems().indexOf(item));
			argsItem.put("cantidad", item.getCantidad());
			argsItem.put("detalle", item.getDetalle());
			argsItem.put("observaciones", item.getObservaciones());
			jdbcTemplate.getNamedParameterJdbcOperations().update(INSERT_PEDIDO_ITEM, argsItem);
		}
	}

	private static final String NUEVO_NRO_PEDIDO = "select nvl((select max(numero) from pedido),0) + 1 from dual";

	private static final String INSERT_PEDIDO = "insert into pedido (numero, codigo_cliente, fecha) values (?,?,?)";

	private static final String INSERT_PEDIDO_ITEM = "insert into pedido_item (pedido_numero, orden, cantidad, detalle, observaciones) values (:numero_pedido, :orden, :cantidad, :detalle, :observaciones)";

	private static final String DELETE_PEDIDO_ITEM = "delete from pedido_item where pedido_numero = ?";

}