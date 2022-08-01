package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controlers.CtrlABMPersona;
import entity.*;
import util.AppDataException;

public class DataSala {

	public Sala getById(Sala s) throws Exception {

		Sala sala = null;
		DataPersona dataPer = new DataPersona();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = FactoryConexion.getInstancia().getConn()
					.prepareStatement("select idSala, nombreSala, idCreador from sala where idSala=?");
			stmt.setInt(1, s.getIdSala());
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				sala = new Sala();
				sala.setIdSala(rs.getInt("idSala"));
				sala.setNombreSala(rs.getString("nombreSala"));

				Persona per = new Persona();
				per.setId(rs.getInt("idCreador"));
				sala.setCreador(dataPer.getById(per.getId()));

			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				FactoryConexion.getInstancia().releaseConn();
			} catch (SQLException e) {
				throw e;
			}
		}

		return sala;
	}

	public Sala getById(int s) throws Exception {

		Sala sala = null;
		DataPersona dataPer = new DataPersona();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = FactoryConexion.getInstancia().getConn()
					.prepareStatement("select idSala, nombreSala, idCreador from sala where idSala=?");
			stmt.setInt(1, s);
			rs = stmt.executeQuery();
			if (rs != null && rs.next()) {
				sala = new Sala();
				sala.setIdSala(rs.getInt("idSala"));
				sala.setNombreSala(rs.getString("nombreSala"));
				sala.setIdCreador(rs.getInt("idCreador"));
				Persona per = new Persona();
				per.setId(rs.getInt("idCreador"));
				sala.setCreador(dataPer.getById(per.getId()));

			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				FactoryConexion.getInstancia().releaseConn();
			} catch (SQLException e) {
				throw e;
			}
		}

		return sala;
	}

	public ArrayList<Sala> getAll() throws Exception {

		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Sala> salas = new ArrayList<Sala>();

		try {
			stmt = FactoryConexion.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("select * from sala s inner join persona p on s.idCreador=p.id");
			if (rs != null) {
				while (rs.next()) {
					Persona p = new Persona();
					CtrlABMPersona ctrl = new CtrlABMPersona();
					Sala s = new Sala();
					s.setIdSala(rs.getInt("idSala"));
					s.setNombreSala(rs.getString("nombreSala"));
					s.setCreador(ctrl.getByDni(rs.getString("dni"))); // Setea Id tambien
					salas.add(s);
				}
			}
		} catch (SQLException e) {

			throw e;
		} catch (AppDataException ade) {
			throw ade;
		}

		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			FactoryConexion.getInstancia().releaseConn();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return salas;
	}

	public void add(Sala s) throws Exception{
		PreparedStatement stmt=null;
		ResultSet keyResultSet=null;
		try {
			stmt=FactoryConexion.getInstancia().getConn()
					.prepareStatement(
					"insert into sala (nombreSala, idCreador) values (?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS
					);
			stmt.setString(1, s.getNombreSala());
			stmt.setInt(2, s.getIdCreador());			
			stmt.executeUpdate();
			keyResultSet=stmt.getGeneratedKeys();
			if(keyResultSet!=null && keyResultSet.next()){
				s.setIdSala(keyResultSet.getInt(1));
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

	public void delete(Sala s) throws Exception {
		PreparedStatement stmt=null;
		try {
			stmt=FactoryConexion.getInstancia().getConn()
				.prepareStatement(
					"delete from sala where idSala=?"
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
}
