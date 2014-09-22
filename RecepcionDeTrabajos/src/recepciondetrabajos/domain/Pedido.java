package recepciondetrabajos.domain;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Pedido {

	private Cliente cliente;

	private Long numero;

	private Date fecha;

	private boolean entregado = false;

	private List<PedidoItem> items = new LinkedList();

	private String primerItem;

	public Pedido() {
		super();
	}

	public Pedido(Cliente cliente) {
		setCliente(cliente);
		setFecha(new Date(Calendar.getInstance().getTimeInMillis()));
	}

	public BigDecimal getGanancia() {
		return getPrecio().subtract(getCosto());
	}

	private BigDecimal getPrecio() {
		BigDecimal precio = BigDecimal.ZERO;
		for (PedidoItem item : getItems()) {
			if ((item != null) && (item.getPrecio() != null)) {
				precio = precio.add(item.getPrecio());
			}
		}
		return precio;
	}

	private BigDecimal getCosto() {
		BigDecimal costo = BigDecimal.ZERO;
		for (PedidoItem item : getItems()) {
			if ((item != null) && (item.getCosto() != null)) {
				costo = costo.add(item.getCosto());
			}
		}
		return costo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
	}

	public boolean isEntregado() {
		return entregado;
	}

	public List<PedidoItem> getItems() {
		return items;
	}

	public void setItems(List<PedidoItem> items) {
		this.items = items;
	}

	public boolean nuevo() {
		return getNumero() == null;
	}

	public String getPrimerItem() {
		return primerItem;
	}

	public void setPrimerItem(String primerItem) {
		this.primerItem = primerItem;
	}

}
