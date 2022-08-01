package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controlers.CtrlABMEstadoPago;
import controlers.CtrlABMPersona;
import entity.*;
import util.AppDataException;

public class DataProducto {
	
	public Producto getById(Producto pro) throws Exception{
		Producto p = null;
		CtrlABMEstadoPago cEP = new CtrlABMEstadoPago();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try {
			stmt=FactoryConexion.getInstancia().getConn().prepareStatement(
					"select p.idProd, p.nombre, p.costo, idPersona, s.idSala, s.nombreSala, per.dni from producto p inner join estadopago ep on ep.idProducto=p.idProd inner join sala s on s.idSala = p.idSala inner join persona per on per.id = p.idPersona where p.idProd=?");
			stmt.setInt(1, pro.getIdProd());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()){
				p=new Producto();
				p.setIdProd(rs.getInt("idProd"));
				p.setNombre(rs.getString("nombre"));
				p.setCosto(rs.getFloat("costo"));
				p.setIdPersona(rs.getInt("idPersona"));
				p.setIdSala(rs.getInt("idSala"));
				//Creo EstadoPago, lo busco y agrego
				EstadoPago ep = new EstadoPago();
				ep.setIdproducto(rs.getInt("idProd"));
				p.setEstadoPago(cEP.getByIdProducto(p));
				
			}
		} catch (Exception e) {
			throw e;
		} finally{
			try {
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				FactoryConexion.getInstancia().releaseConn();
			} catch (SQLException e) {
				throw e;
			}
		}
		return p;
	}
	
	public Producto getByName(Producto pro) throws Exception{
		Producto p = null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		DataPersona dataPer= new DataPersona();
		DataSala dataSal= new DataSala();
		try {
			stmt=FactoryConexion.getInstancia().getConn().prepareStatement(
					"select p.idProd, p.nombre, p.costo, idPersona, s.idSala, s.nombreSala, per.dni from producto p inner join sala s on s.idSala = p.idSala inner join persona per on per.id = p.idPersona where p.nombre=?");
			stmt.setString(1, pro.getNombre());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()){
				p=new Producto();				
				p.setIdProd(rs.getInt("idProd"));
				p.setNombre(rs.getString("nombre"));
				p.setCosto(rs.getFloat("costo"));
				p.setIdPersona(rs.getInt("idPersona"));
				p.setIdSala(rs.getInt("idSala"));
				
				//Creo la persona, la busco y la asigno
				Persona per = new Persona();
				per.setDni(rs.getString("dni"));
				p.setPersona(dataPer.getByDni(per));
				//Creo Sala, la busco y la asigno
				Sala sal =new Sala();
				sal.setIdSala(rs.getInt("idSala"));
				p.setSala(dataSal.getById(sal));				
				
			}
		} catch (Exception e) {
			throw e;
		} finally{
			try {
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				FactoryConexion.getInstancia().releaseConn();
			} catch (SQLException e) {
				throw e;
			}
		}
		return p;
	}
	
	public void add(Producto p) throws Exception{
		PreparedStatement stmt=null;
		ResultSet keyResultSet=null;
		try {
			stmt=FactoryConexion.getInstancia().getConn()
					.prepareStatement(
					"insert into producto(nombre, costo, idPersona, idSala) values (?,?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS
					);
			stmt.setString(1, p.getNombre());
			stmt.setFloat(2, p.getCosto());
			stmt.setInt(3, p.getIdPersona());
			stmt.setInt(4, p.getIdSala());
			stmt.executeUpdate();
			keyResultSet=stmt.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()){
				p.setIdProd(keyResultSet.getInt(1));
			}
		} catch (SQLException | AppDataException e) {
			throw e;
		}
		try {
			if(keyResultSet!=null)keyResultSet.close();
			if(stmt!=null)stmt.close();
			FactoryConexion.getInstancia().releaseConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void remove(Producto p) throws Exception{
		PreparedStatement stmt=null;
		try {
			stmt=FactoryConexion.getInstancia().getConn()
				.prepareStatement(
					"delete from producto where idProd=?"
				);
			stmt.setInt(1, p.getIdProd());
			stmt.executeUpdate();
		} catch (SQLException | AppDataException e) {
			throw e;
		}
		try {
			if(stmt!=null)stmt.close();
			FactoryConexion.getInstancia().releaseConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<Producto> getAll() throws Exception {
		
		Statement stmt=null;
		ResultSet rs=null;
		ArrayList<Producto> pros= new ArrayList<Producto>();
		DataPersona dataPer= new DataPersona();
		DataSala dataSal= new DataSala();
		
		try {
			stmt = FactoryConexion.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("select * from producto pr inner join persona pe on pe.id = pr.idPersona inner join sala s on s.idSala = pr.idSala");
			if(rs!=null){
				while(rs.next()){
					
					Producto p = new Producto();
									
					p.setIdProd(rs.getInt("idProd"));
					p.setNombre(rs.getString("nombre"));
					p.setCosto(rs.getFloat("costo"));
					
					//Creo la persona, la busco y la asigno
					Persona per = new Persona();
					per.setDni(rs.getString("dni"));
					p.setPersona(dataPer.getByDni(per));
					//Creo Sala, la busco y la asigno
					Sala sal =new Sala();
					sal.setIdSala(rs.getInt("idSala"));
					p.setSala(dataSal.getById(sal));					
					pros.add(p);
				}
			}
		} catch (SQLException e) {throw e;}
		
		try {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			FactoryConexion.getInstancia().releaseConn();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return pros;
	}

	public void update(Producto p) throws Exception {
		PreparedStatement stmt=null;
		try {
			stmt=FactoryConexion.getInstancia().getConn()
					.prepareStatement(
					"update producto set nombre=?, costo=? where idProd=?"
					);
			stmt.setString(1, p.getNombre());
			stmt.setFloat(2, p.getCosto());	
			stmt.setInt(3, p.getIdProd());
			stmt.executeUpdate();
		} catch (SQLException | AppDataException e) {
			throw e;
		}
		try {
			if(stmt!=null)stmt.close();
			FactoryConexion.getInstancia().releaseConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void deleteRelatedProducts(Sala s) throws Exception {
		
		PreparedStatement stmt=null;
		try {
			stmt=FactoryConexion.getInstancia().getConn()
				.prepareStatement(
					"delete from producto where idSala=?"
				);
			stmt.setInt(1, s.getIdSala());
			stmt.executeUpdate();
		} catch (SQLException | AppDataException e) {
			throw e;
		}
		try {
			if(stmt!=null)stmt.close();
			FactoryConexion.getInstancia().releaseConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<Producto> getByIdSala(Sala s) throws Exception {
		//Devuelve todos los productos encontrados para una sala, incluye el estado.
		PreparedStatement stmt=null;
		ResultSet rs=null;
		ArrayList<Producto> pros= new ArrayList<Producto>();
		DataPersona dataPer= new DataPersona();
		DataSala dataSal= new DataSala();
		DataEstadoPago dataEP = new DataEstadoPago();
		
		try {
			
			stmt=FactoryConexion.getInstancia().getConn().prepareStatement(
	"select * from producto pr inner join persona pe on pe.id = pr.idPersona inner join sala s on s.idSala = pr.idSala inner join estadopago ep on pr.idProd = ep.idProducto where pr.idSala=?");
			stmt.setInt(1, s.getIdSala());
			rs=stmt.executeQuery();			
			if(rs!=null){
				while(rs.next()){
					
					Producto p = new Producto();									
					p.setIdProd(rs.getInt("idProd"));
					p.setNombre(rs.getString("nombre"));
					p.setCosto(rs.getFloat("costo"));
					
					//Creo la persona, la busco y la asigno
					Persona per = new Persona();
					per.setDni(rs.getString("dni"));
					p.setPersona(dataPer.getByDni(per));
					//Creo Sala, la busco y la asigno
					Sala sal =new Sala();
					sal.setIdSala(rs.getInt("idSala"));
					p.setSala(dataSal.getById(sal));					
					pros.add(p);
					//Creo EstadoPago, lo busco y agrego
					EstadoPago ep = new EstadoPago();
					ep.setIdproducto(rs.getInt("idProd"));
					p.setEstadoPago(dataEP.getByIdProducto(p));
					
					
				}
			}
		} catch (SQLException e1) {
			throw e1;
		}
		try {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			FactoryConexion.getInstancia().releaseConn();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return pros;
	}

	public ArrayList<Persona> getMiembros(Sala s) throws Exception {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		ArrayList<Persona> miembros= new ArrayList<Persona>();
		DataPersona dataPer= new DataPersona();
		
		try {
			
			stmt=FactoryConexion.getInstancia().getConn().prepareStatement(
					"select distinct p.idPersona from producto p where p.idSala=?");
			stmt.setInt(1, s.getIdSala());
			rs=stmt.executeQuery();			
			if(rs!=null){
				while(rs.next()){								
					miembros.add(dataPer.getById(rs.getInt("idPersona")));
				}
			}
		} catch (SQLException e1) {throw e1;}
		
		try {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			FactoryConexion.getInstancia().releaseConn();
		} catch (SQLException e2) {e2.printStackTrace();
		}
		
		return miembros;
	}

	public float getGastoTotal(Sala s) throws AppDataException, SQLException {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		float total = 0;
		
		try {
			stmt=FactoryConexion.getInstancia().getConn().prepareStatement(
					"select sum(p.costo) as total from producto p inner join estadopago ep on p.idProd = ep.idProducto where p.idSala =? and !ep.confirmado");
			stmt.setInt(1, s.getIdSala());
			rs=stmt.executeQuery();			
			if(rs!=null){
				while(rs.next()){								
					total = rs.getFloat("total");
				}
			}
		} catch (SQLException e1) {throw e1;}
		
		try {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			FactoryConexion.getInstancia().releaseConn();
		} catch (SQLException e2) {e2.printStackTrace();
		}
		
		return total;
	}

	public void expulsarPersona(String p, String s) throws Exception {
		PreparedStatement stmt=null;
		try {
			stmt=FactoryConexion.getInstancia().getConn()
				.prepareStatement(
					"delete from producto where idPersona=? and idSala=?"
				);
			stmt.setInt(1, Integer.parseInt(p));
			stmt.setInt(2, Integer.parseInt(s));
			stmt.executeUpdate();
		} catch (SQLException | AppDataException e) {
			throw e;
		}
		try {
			if(stmt!=null)stmt.close();
			FactoryConexion.getInstancia().releaseConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<MiembroGasto> getGastosMiembros(float gastoTotal, Sala sCom) throws Exception {
		
		PreparedStatement stmt=null;
		ResultSet rs=null;
		ArrayList<MiembroGasto> listaMG = new ArrayList<MiembroGasto>();
		try {
			stmt=FactoryConexion.getInstancia().getConn().prepareStatement(
					"select p.idPersona,( ? - sum(costo)) as paga from producto p inner join estadopago ep on p.idProd = ep.idProducto where p.idSala=? and !ep.confirmado group by p.idPersona");
			stmt.setFloat(1, gastoTotal);
			stmt.setInt(2, sCom.getIdSala());
			rs=stmt.executeQuery();
			if(rs!=null){
				while(rs!=null && rs.next()){
					MiembroGasto mg = new MiembroGasto();
					CtrlABMPersona cP = new CtrlABMPersona();
					mg.setId(rs.getInt("idPersona"));
					mg.setGasto(rs.getFloat("paga"));
					mg.setPersona(cP.getById(rs.getInt("idPersona")));
					listaMG.add(mg);
				}
			}
		} catch (SQLException e) {
			throw e;
		} catch (AppDataException ade){
			throw ade;
		}
		
		try {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			FactoryConexion.getInstancia().releaseConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaMG;
		
	}

	public ArrayList<Producto> getProductosMiembro(String p, String s) throws AppDataException, SQLException {
		
		PreparedStatement stmt=null;
		ResultSet rs=null;
		ArrayList<Producto> productos = new ArrayList<Producto>();
		try {
			stmt=FactoryConexion.getInstancia().getConn().prepareStatement(
					"select idProd from producto p inner join estadopago ep on p.idProd = ep.idProducto where p.idPersona=? and p.idSala=? " );
			stmt.setInt(1, Integer.parseInt(p));
			stmt.setInt(2, Integer.parseInt(s));
			rs=stmt.executeQuery();
			if(rs!=null){
				while(rs!=null && rs.next()){
					Producto pr = new Producto();
					pr.setIdProd(rs.getInt("idProd"));
					productos.add(pr);
				}
			}
		} catch (SQLException e) {
			throw e;
		} catch (AppDataException ade){
			throw ade;
		}
		
		try {
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			FactoryConexion.getInstancia().releaseConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productos;
	}
	
	


}
