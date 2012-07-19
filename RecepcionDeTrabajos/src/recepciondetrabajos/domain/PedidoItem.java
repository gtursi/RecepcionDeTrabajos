package recepciondetrabajos.domain;

/**
 * 
 * @author Gabriel Tursi
 * @version $Revision$ $Date$
 */
public class PedidoItem implements Cloneable {

	private Pedido pedido;

	private Integer orden;

	private Integer cantidad = 1;

	private String detalle;

	private String observaciones;

	public PedidoItem(Pedido pedido) {
		this.pedido = pedido;
	}

	private PedidoItem(Pedido pedido, Integer orden, Integer cantidad, String detalle,
			String observaciones) {
		super();
		this.pedido = pedido;
		this.orden = orden;
		this.cantidad = cantidad;
		this.detalle = detalle;
		this.observaciones = observaciones;
	}

	public PedidoItem(PedidoItem item) {
		this(item.getPedido(), item.getOrden(), item.getCantidad(), item.getDetalle(), item
				.getObservaciones());
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

}
