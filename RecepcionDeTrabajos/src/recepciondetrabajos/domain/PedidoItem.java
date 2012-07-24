package recepciondetrabajos.domain;

/**
 * 
 * @author Gabriel Tursi
 * @version $Revision$ $Date$
 */
public class PedidoItem implements Cloneable {

	private Integer orden;

	private Integer cantidad = 1;

	private String detalle;

	private String observaciones;

	public PedidoItem() {
		super();
	}

	public PedidoItem(Pedido pedido) {
		pedido.getItems().add(this);
	}

	private PedidoItem(Integer orden, Integer cantidad, String detalle, String observaciones) {
		super();
		this.orden = orden;
		this.cantidad = cantidad;
		this.detalle = detalle;
		this.observaciones = observaciones;
	}

	public PedidoItem(PedidoItem item) {
		this(item.getOrden(), item.getCantidad(), item.getDetalle(), item.getObservaciones());
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
