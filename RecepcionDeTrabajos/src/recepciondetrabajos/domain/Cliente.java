package recepciondetrabajos.domain;

/**
 * 
 * @author Gabriel Tursi
 * @version $Revision$ $Date$
 */
public class Cliente {

	private Long codigo;

	private String denominacion;

	private String direccion;

	private String email;

	public Cliente() {
		super();
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return getCodigo() + " - " + getDenominacion();
	}

	public boolean isNuevo() {
		return getCodigo() == null;
	}

}
