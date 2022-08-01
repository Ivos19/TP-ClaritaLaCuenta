package controlers;

import java.util.ArrayList;

import data.DataPersona;
import data.DataSala;
import entity.Persona;
import entity.Sala;

public class CtrlABMSala {
	
	private int idSala;
	private String nombreSala;
	private int idCreador;
	private DataPersona dataPersona;
	private DataSala dataSala;
	
	public CtrlABMSala() {
		dataPersona = new DataPersona();
		dataSala = new DataSala();
	}

	public Sala getById (Sala s) throws Exception {
		return this.dataSala.getById(s);
	}
	
	public Sala getById(int id) throws Exception {
		return this.dataSala.getById(id);
	}

	public ArrayList<Sala> getAll() throws Exception {
		return dataSala.getAll();
	}

	public void add(Sala s) throws Exception {
		this.dataSala.add(s);
		
	}

	public void delete(Sala s) throws Exception {
		this.dataSala.delete(s);
		
	}
	
}
