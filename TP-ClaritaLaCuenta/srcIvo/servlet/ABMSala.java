package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controlers.CtrlABMPersona;
import controlers.CtrlABMProducto;
import controlers.CtrlABMSala;
import entity.Persona;
import entity.Producto;
import entity.Sala;
import util.AppDataException;
import entity.MiembroGasto;


/**
 * Servlet implementation class ABMSala
 */
@WebServlet({ "/ABMSala/*", "/sala/*", "/Salas/*" })
public class ABMSala extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ABMSala() {
        super();

    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		switch (request.getPathInfo()){
		case "/crearSala":
			this.crearSala(request, response);
			break;
		
		case "/gastosSala":
			this.gastos(request, response);
			break;
		
		case "/eliminar":
			this.eliminar(request, response);
			break;
			
		case "/eliminarMiembro":
			this.eliminarMiembro(request, response);
			break;
			
		case "/dividir":
			this.dividir(request, response);
			break;
			
		default:
			this.error(request, response);
			break;
		}
	}

	private void dividir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("nomS").equals("")) {
			request.setAttribute("url", "start");
			request.setAttribute("error", "Error al dividir, fallo id sala");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
			dispatcher.forward(request,response);
		}
		else {
			CtrlABMProducto cP = new CtrlABMProducto();	
			CtrlABMSala cS= new CtrlABMSala();
			Sala sCom = new Sala();
			
			
			try {
				Sala s = new Sala();
				s.setIdSala(Integer.parseInt(request.getParameter("nomS")));
				sCom = cS.getById(s);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			float gastoTotal = 0;			
			try {
				gastoTotal = gastoTola(sCom);
			} catch (Exception e2) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMSala en dividir, al buscar miembros sala");
			}
			
			ArrayList<MiembroGasto> listaMG = new ArrayList<MiembroGasto>();
			
			try {
				listaMG = cP.getGastosMiembros((gastoTotal/buscaMiembros(sCom).size()), sCom);
			} catch (Exception e3) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMSala en dividir, al dividir gastos");
			}

			try {
				request.setAttribute("accion", "dividirGastos");
				request.setAttribute("mg", listaMG);
				request.setAttribute("gastoTotal", gastoTotal);
				request.setAttribute("sala", sCom);
				request.setAttribute("miembros", buscaMiembros(sCom));
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/DivisionSala.jsp");
				dispatcher.forward(request,response);
			} catch (ServletException e3) {
					throw e3;			
			}
			
			
		}
	}
		


	private void eliminarMiembro(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		CtrlABMProducto cP= new CtrlABMProducto();
		try {
		cP.expulsarPersona((request.getParameter("persona")) , (request.getParameter("salaO")));
		} catch (Exception e1) {
			response.setStatus(502);
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMSala en gastos, expulsando miembro");
		}
		try {
			request.setAttribute("accion", "eliminarMiembro");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/AccionProducto.jsp");
			dispatcher.forward(request,response);
		} catch (ServletException e3) {
				throw e3;			
		}
		
			
	}

	private void gastos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getParameter("nomS").equals("")) { 
			request.setAttribute("url", "start");
			request.setAttribute("error", "Falta Id sala");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
			dispatcher.forward(request,response);			
		} else {
			request.setAttribute("accion", "gastos");
			CtrlABMProducto cP = new CtrlABMProducto();	
			CtrlABMSala cS= new CtrlABMSala();
			ArrayList<Producto> ps = new ArrayList<Producto>();
			Sala s = new Sala();
			Sala sCom = new Sala();
			s.setIdSala(Integer.parseInt(request.getParameter("nomS")));
			
			try {
				sCom = cS.getById(s);
			} catch (Exception e1) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMSala en gastos, al buscar Sala");
			}
			
			try {
				ps = cP.getByIdSala(s);
			} catch (Exception e2) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMSala en gastos, al buscar todos los productos de la sala");
			}
			
			ArrayList<Persona> miembros = new ArrayList<Persona>();
			try {
				miembros = buscaMiembros(s);
			} catch (Exception e3) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMSala en gastos, al buscar miembros sala");
			}
			
			float gastoTotal = 0;			
			try {
				gastoTotal = gastoTola(s);
			} catch (Exception e3) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMSala en gastos, al buscar miembros sala");
			}
			
			float div = (float)((double)Math.round((gastoTotal/miembros.size()) * 100d) / 100d);
			//float div = (gastoTotal/miembros.size());
			
			try {
				request.setAttribute("gastoTotal", gastoTotal);
				request.setAttribute("sala", sCom);
				request.setAttribute("miembros", miembros);
				request.setAttribute("listaPro", ps);
				request.setAttribute("div", div);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/DetalleGastos.jsp");
				dispatcher.forward(request,response);
			} catch (ServletException e3) {
					throw e3;			
			}			
		}		
	}
	
	private float gastoTola(Sala s) {
		float gastoTotal = 0;
		CtrlABMProducto cP = new CtrlABMProducto();
		
		try {
			gastoTotal = cP.getGastosSala(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gastoTotal;
	}

	private ArrayList<Persona> buscaMiembros(Sala s) {
		ArrayList<Persona> miembros = new ArrayList<Persona>();
			CtrlABMProducto cP = new CtrlABMProducto();	
			try {
				miembros = cP.getMiembrosSala(s);
			} catch (Exception e) {
				e.printStackTrace();
			}		
		return miembros;
		
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getParameter("nomS").equals("")) { 
			request.setAttribute("url", "start");
			request.setAttribute("error", "Falta Id sala");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
			dispatcher.forward(request,response);			
		} else {
			
			request.setAttribute("accion", "eliminar");
			
			CtrlABMSala cS = new CtrlABMSala();
			Sala s = new Sala();
			
			try {
				s = cS.getById(Integer.parseInt(request.getParameter("nomS")));
			} catch (Exception e1) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMSala al intentar buscarla por Id");
			}
			
			CtrlABMProducto cP = new CtrlABMProducto();
			try {
				cP.deleteRelatedProducts(s);
			} catch (Exception e2) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error al eliminar productos relacionados a Sala");
			}
			
			try {
				cS.delete(s);
			} catch (Exception e2) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error al eliminar Sala");
			}
			
			try {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/AccionSala.jsp");
				dispatcher.forward(request,response);
			} catch (ServletException e3) {
					throw e3;			
			}
			
		}
		
	}

	private void error(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setStatus(404);
		response.sendError(HttpServletResponse.SC_NOT_FOUND, "Error en ABMSala al seleccionar opcion (switch). No se encontro opt.");
	}

	private void crearSala(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		if(request.getParameter("nom").equals("")) { 
			request.setAttribute("url", "start");
			request.setAttribute("error", "Para crear una sala se requiere el nombre");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
			dispatcher.forward(request,response);			
		} else {
			request.setAttribute("accion", "insertar");
			CtrlABMSala ctrlS = new CtrlABMSala();
			Sala s = new Sala();
			CtrlABMPersona ctrlP = new CtrlABMPersona();
			
			try {
				s.setCreador(ctrlP.getByDni(request.getParameter("dniO")));
			} catch (Exception e1) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMSala en crearSala al buscar creador");
			}
			s.setNombreSala(request.getParameter("nom"));
			
			try {
				ctrlS.add(s);
			} catch (Exception e2) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMSala al intentar add(Sala)");
			}
			try {
				request.setAttribute("sala", s);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/AccionSala.jsp");
				dispatcher.forward(request,response);
			} catch (ServletException e3) {
					throw e3;			
			}		
		}
	}

}
