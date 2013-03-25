package recepciondetrabajos.domain;

import java.math.BigDecimal;

/**
 * 
 * @author Gabriel Tursi
 * @version $Revision$ $Date$
 */
public class PedidoItem implements Cloneable {

	private Integer orden;

	private boolean entregado = false;

	private Integer cantidad = 1;

	private String detalle;

	private String observaciones;

	private String comentarios;

	private BigDecimal costo;

	private BigDecimal precio;

	public PedidoItem() {
		super();
	}

	public PedidoItem(Pedido pedido) {
		pedido.getItems().add(this);
	}

	private PedidoItem(Integer orden, Integer cantidad, boolean entregado, String detalle,
			String observaciones, String comentarios, BigDecimal costo, BigDecimal precio) {
		super();
		this.orden = orden;
		this.entregado = entregado;
		this.cantidad = cantidad;
		this.detalle = detalle;
		this.observaciones = observaciones;
		this.comentarios = comentarios;
		this.costo = costo;
		this.precio = precio;
	}

	public PedidoItem(PedidoItem item) {
		this(item.getOrden(), item.getCantidad(), item.isEntregado(), item.getDetalle(), item
				.getObservaciones(), item.getComentarios(), item.getCosto(), item.getPrecio());
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public boolean isEntregado() {
		return entregado;
	}

	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
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

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public BigDecimal getCosto() {
		return costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

}
