package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.EstadoPago;
import entity.Persona;
import entity.Producto;
import entity.Sala;
import util.AppDataException;

public class DataEstadoPago {
	
	public void add(Producto p) throws Exception{
		PreparedStatement stmt=null;
		ResultSet keyResultSet=null;
		try {
			stmt=FactoryConexion.getInstancia().getConn()
					.prepareStatement(
					"insert into estadoPago(idProducto) values (?)",
					PreparedStatement.RETURN_GENERATED_KEYS
					);
			stmt.setInt(1, p.getIdProd());
			stmt.executeUpdate();
			keyResultSet=stmt.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()){
				p.setIdEstado(keyResultSet.getInt(1));
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
	
	public void update(EstadoPago p) throws Exception {
		PreparedStatement stmt=null;
		try {
			stmt=FactoryConexion.getInstancia().getConn()
					.prepareStatement(
					"update estadopago set pagado=?, confirmado=? where idProducto=?"
					);
			stmt.setBoolean(1, p.isPagado());
			stmt.setBoolean(2, p.isConfirmado());	
			stmt.setInt(3, p.getIdproducto());
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

	public void delete(Producto pro) throws Exception {
		PreparedStatement stmt=null;
		try {
			stmt=FactoryConexion.getInstancia().getConn()
				.prepareStatement(
					"delete from estadopago where idProducto=?"
				);
			stmt.setInt(1, pro.getIdProd());
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

	public EstadoPago getByIdProducto(Producto pr) throws Exception {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		EstadoPago ep = new EstadoPago();
		try {
			stmt=FactoryConexion.getInstancia().getConn().prepareStatement(
					"select idEstado, pagado, confirmado, idProducto from estadopago ep where ep.idProducto=?");
			stmt.setInt(1, pr.getIdProd());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()){
				ep.setIdproducto(rs.getInt("idProducto"));
				ep.setPagado(rs.getBoolean("pagado"));
				ep.setConfirmado(rs.getBoolean("confirmado"));
				ep.setIdEstado(rs.getInt("idEstado"));							
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
		return ep;
	}

	
	

}
