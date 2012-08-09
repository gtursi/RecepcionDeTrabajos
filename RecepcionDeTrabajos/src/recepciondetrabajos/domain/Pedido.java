package recepciondetrabajos.domain;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Pedido {

	private Cliente cliente;

	private Long numero;

	public Pedido() {
		super();
	}

	private Date fecha;

	private List<PedidoItem> items = new LinkedList();

	public Pedido(Cliente cliente) {
		setCliente(cliente);
		setFecha(new Date(Calendar.getInstance().getTimeInMillis()));
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

	public List<PedidoItem> getItems() {
		return items;
	}

	public void setItems(List<PedidoItem> items) {
		this.items = items;
	}

	public boolean nuevo() {
		return getNumero() == null;
	}

}
