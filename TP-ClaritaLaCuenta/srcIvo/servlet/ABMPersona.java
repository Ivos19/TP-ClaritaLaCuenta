package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controlers.CtrlABMPersona;
import entity.Categoria;
import entity.Persona;

/**
 * Servlet implementation class ABMPersona
 */
@WebServlet({ "/ABMPersona", "/personas/*", "/Personas/*", "/PERSONAS/*" })
public class ABMPersona extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ABMPersona() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		switch (request.getPathInfo()){
		case "/buscar":
			this.buscar(request, response);
			break;
			
		case "/insertarPersona":
			this.insertar(request, response);
			break;
			
		case "/eliminar":
			this.eliminar(request, response);
			break;
			
		case "/modificar":
			this.modificar(request, response);
			break;
			
			
		default:
			this.error(request, response);
			break;
		} 
	}
	
	private void buscar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Persona p=new Persona();
		request.setAttribute("accion", "buscar");
		p.setDni(request.getParameter("dni"));
		CtrlABMPersona ctrl= new CtrlABMPersona();
		Persona pers=new Persona();
			try {
				pers=ctrl.getByDni(p);
			} catch (Exception e) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error de servidor");
			}
			if(pers==null)
			{
				request.setAttribute("url", "start");
				request.setAttribute("error", "Persona no encontrada");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
				dispatcher.forward(request,response);
			}
			else
			{
				request.setAttribute("persona", pers);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/AccionPersona.jsp");
				dispatcher.forward(request,response);
			}
	}
	
	private void insertar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getParameter("dni").equals("") || request.getParameter("apellido").equals("") || request.getParameter("nombre").equals("") || request.getParameter("pass").equals("") || request.getParameter("user").equals(""))
		{
			request.setAttribute("url", "start");
			request.setAttribute("error", "No debe haber campos vacíos.");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
			dispatcher.forward(request,response);
		}
		else
		{
			
			Persona p=new Persona();
			Persona pv =new Persona();
			Categoria c=new Categoria();
			CtrlABMPersona ctrl= new CtrlABMPersona();
			
			try {
				pv = ctrl.getByDni(request.getParameter("dni"));
			} catch (Exception e1) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMPersona al intentar validar dni");
			}
			
			if (pv == null) {
			
			request.setAttribute("accion", "insertar");
			c.setDescripcion(request.getParameter("categoria"));
			p.setDni(request.getParameter("dni"));
			p.setApellido(request.getParameter("apellido"));
			p.setNombre(request.getParameter("nombre"));
			p.setUser(request.getParameter("user"));
			p.setPass(request.getParameter("pass"));
			try {
				p.setCategoria(ctrl.getByNombre(c));
			} catch (Exception e2) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMPersona al intentar obtener la categoria por nombre");
			}

			if(request.getParameter("habilitado").equals("si"))
			{
				p.setHabilitado(true);
			}
			else
			{
				p.setHabilitado(false);
			}
			
			try {
				ctrl.add(p);
			} catch (Exception e3) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMPersona al intentar hacer add");
			}
			
			try {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/AccionPersona.jsp");
			dispatcher.forward(request,response);
			} catch (ServletException e4) {
				throw e4;
				//response.setStatus(502);
				//response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMPersona.Insertar() al dispatch");
			}
			
			
		} else {
			request.setAttribute("url", "start");
			request.setAttribute("error", "El DNI ya existe");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
			dispatcher.forward(request,response);
		}
		}
	}
	
	private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Persona p=new Persona();
		Persona per=new Persona();
		CtrlABMPersona ctrl= new CtrlABMPersona();
		request.setAttribute("accion", "eliminar");
		p.setDni(request.getParameter("dni"));
		try {
			per=ctrl.getByDni(p);
		} catch (Exception e1) {
			response.setStatus(502);
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error al getByDni() de Eliminar en SABMPersona");
		}
		if(per==null)
		{
			request.setAttribute("url", "start");
			request.setAttribute("error", "Persona no encontrada.");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
			dispatcher.forward(request,response);
		}
		else
		{
			try {
				ctrl.delete(per);
			} catch (Exception e) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error de servidor");
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/AccionPersona.jsp");
			dispatcher.forward(request,response);
		}		
	}
	
	private void modificar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getParameter("dni").equals("") || request.getParameter("apellido").equals("") || request.getParameter("nombre").equals("") || request.getParameter("pass").equals("") || request.getParameter("user").equals(""))
		{
			request.setAttribute("url", "start");
			request.setAttribute("error", "No debe haber campos vacíos.");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
			dispatcher.forward(request,response);
		}
		else
		{
			Persona p=new Persona();
			Persona per=new Persona();
			Categoria c=new Categoria();
			CtrlABMPersona ctrl= new CtrlABMPersona();
			request.setAttribute("accion", "modificar");
			c.setDescripcion(request.getParameter("categoria"));
			p.setDni(request.getParameter("dni"));
			p.setApellido(request.getParameter("apellido"));
			p.setNombre(request.getParameter("nombre"));
			try {
				p.setCategoria(ctrl.getByNombre(c));
			} catch (Exception e) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error de servidor");
			}
			p.setPass(request.getParameter("pass"));
			if(request.getParameter("habilitado").equals("si"))
			{
				p.setHabilitado(true);
			}
			else
			{
				p.setHabilitado(false);
			}
			p.setUser(request.getParameter("user"));
			try {
				per=ctrl.getByDni(p);
			} catch (Exception e1) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error de servidor");
			}
			if(per==null)
			{
				request.setAttribute("url", "start");
				request.setAttribute("error", "Persona no encontrada.");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
				dispatcher.forward(request,response);
			}
			else
			{
				p.setId(per.getId());
				try {
					ctrl.update(p);
				} catch (Exception e) {
					response.setStatus(502);
					response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error de servidor");
				}
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/AccionPersona.jsp");
				dispatcher.forward(request,response);
			}
		}
	}

	private void error(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setStatus(404);
		response.sendError(HttpServletResponse.SC_NOT_FOUND, "Error en ABMPersona al seleccionar opcion");
	}
}
