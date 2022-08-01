package entity;

public class Sala {
	
	private int idSala;
	private String nombreSala;
	private int idCreador;
	private Persona creador;
	
	public void sala() {};
	
	public void setSala(int idS, String nom, int idC) {
		this.idSala=idS;
		this.nombreSala=nom;
		this.idCreador=idC;
	}
	
	public int getIdSala() {
		return idSala;
	}
	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}
	public String getNombreSala() {
		return nombreSala;
	}
	public void setNombreSala(String nombre) {
		this.nombreSala = nombre;
	}
	public int getIdCreador() {
		return idCreador;
	}
	public void setIdCreador(int idCreador) {
		this.idCreador = idCreador;
	}
	public Persona getCreador() {
		return creador;
	}
	public void setCreador(Persona creador) {
		this.setIdCreador(creador.getId());
		this.creador = creador;
	}

	
}
