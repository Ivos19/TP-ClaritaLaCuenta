package entity;

import java.io.Serializable;

public class Producto implements Serializable {
	private String nombre;
	private Sala sala;
	private Persona persona;
	private EstadoPago estadoPago;
	private float costo;
	private int idPersona;
	private int idSala;
	private int idProd;
	private int idEstado;
	
	public EstadoPago getEstadoPago() {
		return estadoPago;
	}

	public void setEstadoPago(EstadoPago estadoPago) {
		this.estadoPago = estadoPago;
	}

	public int getIdProd() {
		return idProd;
	}

	public void setIdProd(int idProd) {
		this.idProd = idProd;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}
	
	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
		this.idSala = sala.getIdSala();
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
		this.setIdPersona(persona.getId());
	}

	public Producto(String nombre, float costo, int idPersona, int idSala, int idProd) {
		this.setNombre(nombre);
		this.setCosto(costo);
		this.setIdPersona(idPersona);
		this.setIdSala(idSala);
		this.setIdProd(idProd);
	}
	
	public int getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

	public Producto() {}

}
	
	
	
	
	


