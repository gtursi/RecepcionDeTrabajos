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
import recepciondetrabajos.domain.GananciaMensual;
import recepciondetrabajos.domain.Pedido;
import recepciondetrabajos.domain.PedidoItem;

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
	public static List<Pedido> consultarPedidos(String denominacionCliente) {
		return jdbcTemplate
				.query("select p.*, c.codigo, c.denominacion from pedido p join cliente c on p.codigo_cliente = c.codigo where lower(c.denominacion) like ?",
						new ParameterizedRowMapper() {

							public Pedido mapRow(ResultSet rs, int rowNum) throws SQLException {
								Cliente cliente = new Cliente();
								cliente.setCodigo(rs.getLong("codigo"));
								cliente.setDenominacion(rs.getString("denominacion"));
								Pedido pedido = new Pedido(cliente);
								pedido.setNumero(rs.getLong("numero"));
								pedido.setFecha(rs.getDate("fecha"));
								return pedido;
							}
						}, "%" + denominacionCliente.trim().toLowerCase() + "%");
	}

	public static Pedido obtenerPedido(Long numero, Long codigoCliente) {
		Pedido pedido = jdbcTemplate.queryForObject("select * from pedido where numero=?",
				ParameterizedBeanPropertyRowMapper.newInstance(Pedido.class), numero);
		pedido.setCliente(ClienteService.consultarCliente(codigoCliente));
		List<PedidoItem> items = jdbcTemplate.query(
				"select * from pedido_item where pedido_numero = ? order by orden",
				ParameterizedBeanPropertyRowMapper.newInstance(PedidoItem.class), numero);
		pedido.setItems(items);
		return pedido;
	}

	public static void eliminar(Pedido pedido) {
		Object[] args = { pedido.getNumero() };
		jdbcTemplate.update("delete pedido_item where pedido_numero = ?", args);
		jdbcTemplate.update("delete pedido where numero = ?", args);
	}

	@SuppressWarnings("unchecked")
	public static List<GananciaMensual> gananciaMensualizada() {
		return jdbcTemplate.query(GANANCIA_MENSUALIZADA, new ParameterizedRowMapper() {

			public GananciaMensual mapRow(ResultSet rs, int rowNum) throws SQLException {
				GananciaMensual ganancia = new GananciaMensual();
				ganancia.setAño(rs.getInt("año"));
				ganancia.setMes(rs.getInt("mes"));
				ganancia.setGanancia(rs.getBigDecimal("ganancia"));
				return ganancia;
			}
		}, (Map) null);
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
			argsItem.put("costo", item.getCosto());
			argsItem.put("precio", item.getPrecio());
			jdbcTemplate.getNamedParameterJdbcOperations().update(INSERT_PEDIDO_ITEM, argsItem);
		}
	}

	private static final String GANANCIA_MENSUALIZADA = "SELECT YEAR(p.fecha) as año, MONTH(p.fecha) as mes, IFNULL(sum(precio-costo),0) as ganancia "
			+ "FROM PEDIDO_ITEM pi join pedido p on pi.pedido_numero  = p.numero "
			+ "group by year(p.fecha), MONTH(p.fecha) order by 1 desc,2 desc";

	private static final String NUEVO_NRO_PEDIDO = "select nvl((select max(numero) from pedido),0) + 1 from dual";

	private static final String INSERT_PEDIDO = "insert into pedido (numero, codigo_cliente, fecha) values (?,?,?)";

	private static final String INSERT_PEDIDO_ITEM = "insert into pedido_item (pedido_numero, orden, cantidad, detalle, observaciones, costo, precio) values (:numero_pedido, :orden, :cantidad, :detalle, :observaciones, :costo, :precio)";

	private static final String DELETE_PEDIDO_ITEM = "delete from pedido_item where pedido_numero = ?";

}
