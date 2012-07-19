package recepciondetrabajos.domain;

import java.util.Calendar;
import java.util.List;

public class Pedido {

	private Cliente cliente;

	private Long numero;

	private Calendar fecha;

	private List<PedidoItem> items;

	public Pedido(Cliente cliente) {
		setCliente(cliente);
		setFecha(Calendar.getInstance());
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

	public Calendar getFecha() {
		return fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}

	public List<PedidoItem> getItems() {
		return items;
	}

	public void setItems(List<PedidoItem> items) {
		this.items = items;
	}

}
