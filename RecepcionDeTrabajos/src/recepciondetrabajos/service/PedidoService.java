package recepciondetrabajos.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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
		for (PedidoItem item : nuevoPedido.getItems()) {
			Object[] argsItem = { numeroPedido, item.getOrden(), item.getCantidad(),
					item.getDetalle(), item.getObservaciones() };
			jdbcTemplate.update(INSERT_PEDIDO_ITEM, argsItem);
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Pedido> consultarPedidos(Integer codigoCliente, String denominacionCliente) {
		return jdbcTemplate
				.query("select p.*, c.codigo, c.denominacion from pedido p join cliente c on p.codigo_cliente = c.codigo join pedido_item pi on p.numero = pi.pedido_numero",
						new RowMapper() {

							public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
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

	public static final JdbcTemplate jdbcTemplate = ApplicationContext.getInstance().getBean(
			JdbcTemplate.class);

	private static final String NUEVO_NRO_PEDIDO = "select nvl((select max(numero) from pedido),0) + 1 from dual";

	private static final String INSERT_PEDIDO = "insert into pedido (numero, codigo_cliente, fecha) values (?,?,?)";

	private static final String INSERT_PEDIDO_ITEM = "insert into pedido_item (numero_pedido, orden, cantidad, detalle, observaciones) values (:numero_pedido, :orden, :cantidad, :detalle, :observaciones)";

}
