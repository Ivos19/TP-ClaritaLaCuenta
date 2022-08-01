package controlers;

import data.DataEstadoPago;
import data.DataProducto;
import entity.EstadoPago;
import entity.Producto;

public class CtrlABMEstadoPago {

	private DataEstadoPago dataEstadoPago;
	private DataProducto dataProducto;
	
	public CtrlABMEstadoPago() {
		dataEstadoPago = new DataEstadoPago();
	}
	
	public void crearEP(Producto pr) throws Exception {
		this.dataEstadoPago.add(pr);	
	}

	public void delete(Producto pro) throws Exception {
		this.dataEstadoPago.delete(pro);
		
	}

	public void update(EstadoPago ep) throws Exception {
		this.dataEstadoPago.update(ep);
		
	}
	public EstadoPago getByIdProducto(Producto pr) throws Exception {
		return this.dataEstadoPago.getByIdProducto(pr);
	}
		
}
