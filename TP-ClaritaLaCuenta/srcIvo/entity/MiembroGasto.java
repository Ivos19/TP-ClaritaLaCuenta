package entity;

public class MiembroGasto {

	public int id;
	public Persona persona;
	public float gasto;
	 
	public MiembroGasto() {};
	 
	public int getId() {return id;} 
	public void setId(int id) {this.id = id;} 
	 
	public float getGasto() {return gasto;} 
	public void setGasto(float gasto) {this.gasto = gasto;}

	public Persona getPersona() {return persona;}

	public void setPersona(Persona persona) {this.persona = persona;}
	 
	 
}


