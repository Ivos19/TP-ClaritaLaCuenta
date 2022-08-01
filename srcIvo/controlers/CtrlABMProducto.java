package controlers;

import java.sql.SQLException;
import java.util.ArrayList;

import data.DataPersona;
import controlers.*;
import data.DataProducto;
import entity.EstadoPago;
import entity.MiembroGasto;
import entity.Persona;
import entity.Producto;
import entity.Sala;
import util.AppDataException;

public class CtrlABMProducto {
	
	private DataProducto dataProducto;
	private DataPersona dataPersona;
	
	public CtrlABMProducto() {
		dataProducto = new DataProducto();
		dataPersona = new DataPersona();
	}

	public Producto getByName(Producto p) throws Exception{
		return this.dataProducto.getByName(p);
	}

	public void add(Producto pr) throws Exception {
		this.dataProducto.add(pr);
		CtrlABMEstadoPago cE = new CtrlABMEstadoPago();
		cE.crearEP(pr);
		
	}

	public void delete(Producto pro) throws Exception {
		
		CtrlABMEstadoPago cE = new CtrlABMEstadoPago();
		cE.delete(pro);
		
		dataProducto.remove(pro);
		
	}

	public ArrayList<Producto> getAll() throws Exception{
		
		return dataProducto.getAll();
	}

	public void update(Producto prE) throws Exception{
		
		this.dataProducto.update(prE);
		
	}

	public Producto getById(String id) {
		try {
			Producto p = new Producto();
			p.setIdProd(Integer.parseInt(id));
			return dataProducto.getById(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void deleteRelatedProducts(Sala s) throws Exception {
		
		CtrlABMEstadoPago cEP = new CtrlABMEstadoPago();
		for (Producto p : this.dataProducto.getByIdSala(s)) {
			try {
				cEP.delete(p);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		this.dataProducto.deleteRelatedProducts(s);
		
	}

	public ArrayList<Producto> getByIdSala(Sala s) throws Exception {
		
		return this.dataProducto.getByIdSala(s);

	}

	public ArrayList<Persona> getMiembrosSala(Sala s) throws Exception {
		return this.dataProducto.getMiembros(s);
	
	}

	public float getGastosSala(Sala s) throws Exception {
		return this.dataProducto.getGastoTotal(s);
	}

	public void expulsarPersona(String p, String s) throws AppDataException, SQLException {
		
		ArrayList<Producto> productos = dataProducto.getProductosMiembro(p, s);
		CtrlABMEstadoPago cEP = new CtrlABMEstadoPago();
		
		for (Producto pro : productos) {
			
			try {
				cEP.delete(pro);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			this.dataProducto.expulsarPersona(p, s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<MiembroGasto> getGastosMiembros(float gastoTotal, Sala sCom) throws Exception {
		ArrayList<MiembroGasto> listaMG = new ArrayList<MiembroGasto>();
		listaMG = this.dataProducto.getGastosMiembros(gastoTotal, sCom);
		return listaMG;
	}

}
