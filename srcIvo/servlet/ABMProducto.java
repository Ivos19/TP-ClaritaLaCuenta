package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controlers.CtrlABMEstadoPago;
import controlers.CtrlABMPersona;
import controlers.CtrlABMProducto;
import controlers.CtrlABMSala;
import entity.EstadoPago;
import entity.Persona;
import entity.Producto;
import entity.Sala;

/**
 * Servlet implementation class ABMProducto
 */
@WebServlet({"/productos/*", "/Productos/*"})
public class ABMProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ABMProducto() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		switch (request.getPathInfo()){
		case "/buscarProd":
			this.buscar(request, response);
			break;
			
		case "/insertarProd":
			this.insertar(request, response);
			break;
			
		case "/eliminarProducto":
			this.eliminar(request, response);
			break;
			
		case "/modificarProd":
			this.modificar(request, response);
			break;
			
		case "/actualizarEstadoP":
			this.actualizarEstadoPagado(request, response);
			break;
		
		case "/actualizarEstadoC":
			this.actualizarEstadoPagadoC(request, response);
			break;
			
		default:
			this.error(request, response);
			break;
		}
		
		
	}


	private void actualizarEstadoPagadoC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("nomS").length() == 0) {
			request.setAttribute("url", "start");
			request.setAttribute("error", "error al cambiar estado producto");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
			dispatcher.forward(request,response);
		} else {
			
			Producto p = new Producto();
			CtrlABMProducto cP = new CtrlABMProducto();
			CtrlABMEstadoPago cEP = new CtrlABMEstadoPago();
			EstadoPago ep = new EstadoPago();
			
			p = cP.getById(request.getParameter("nomS"));
			try {
				ep = cEP.getByIdProducto(p);
			} catch (Exception e) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "#11 No se pudo actualiza estado");
			}
			
				if (!p.getEstadoPago().isConfirmado()) {
					ep.setConfirmado(true);
					request.setAttribute("acConfirmar", "Confirmado el pago!");					
				} else {
					ep.setConfirmado(false);
					request.setAttribute("acConfirmar", "Te hicieron el amague? Se elimino la confirmacion");
				}
				try {
					cEP.update(ep);
				} catch (Exception e) {
					response.setStatus(502);
					response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "#22 No se pudo actualiza estado");
				}
				request.setAttribute("accion", "Confirmar");	
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/AccionProducto.jsp");
				dispatcher.forward(request,response);
		}
		
	}

	private void actualizarEstadoPagado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("nomS").length() == 0) {
			request.setAttribute("url", "start");
			request.setAttribute("error", "error al cambiar estado producto");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
			dispatcher.forward(request,response);
		} else {
			
			Producto p = new Producto();
			CtrlABMProducto cP = new CtrlABMProducto();
			CtrlABMEstadoPago cEP = new CtrlABMEstadoPago();
			EstadoPago ep = new EstadoPago();
			
			p = cP.getById(request.getParameter("nomS"));
			try {
				ep = cEP.getByIdProducto(p);
			} catch (Exception e) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "#21 No se pudo actualiza estado");
			}
			
				if (!p.getEstadoPago().isPagado()) {
					ep.setPagado(true);
					request.setAttribute("acPago", "Pagado!");
					
				} else {
					ep.setPagado(false);
					request.setAttribute("acPago", "Devuelto... espero.");
				}
				try {
					cEP.update(ep);
				} catch (Exception e) {
					response.setStatus(502);
					response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "#22 No se pudo actualiza estado");
				}
			request.setAttribute("accion", "Pagar");
						
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/AccionProducto.jsp");
			dispatcher.forward(request,response);
		}	
	}

	private void modificar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		{
			request.setAttribute("accion", "modificar");
			String id = request.getParameter("selectProducto");
			
			if (id.length() == 0 || (request.getParameter("nombre").length() == 0 && request.getParameter("costo").length() == 0))
			{
				request.setAttribute("url", "start");
				if (id.length() == 0) {request.setAttribute("error", "Debe seleccionar un producto de la lista");}
				else {request.setAttribute("error", "Debe ingresar almenos un nombre o valor");}
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
				dispatcher.forward(request,response);
			}			
			else
			{
				Producto prE = new Producto();
				CtrlABMProducto ctrPr = new CtrlABMProducto();
				try {
					prE = ctrPr.getById(id);
				} catch (Exception e) {
					response.setStatus(502);
					response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMProducto-Modificar, al buscar y asignar producto");
				}
				
				
				
				try {
					if (request.getParameter("costo").length() > 0) {
					prE.setCosto(Float.parseFloat(request.getParameter("costo")));
					}
					if (request.getParameter("nombre").length() > 0) {
					prE.setNombre(request.getParameter("nombre")); 
					}
					} 
				catch (Exception e2) {
					response.setStatus(502);
					response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMProducto-Modificar al intentar setCosto()");
				}
				
				try {
					ctrPr.update(prE);
				} catch (Exception e) {
					response.setStatus(502);
					response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMProducto-Modificar al hacer update()");
				}
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/AccionProducto.jsp");
				dispatcher.forward(request,response);
				
			}
			
		}
		
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setAttribute("accion", "eliminar");
		Producto p = new Producto();
		Producto pro = new Producto();
		CtrlABMProducto ctrlP = new CtrlABMProducto();
		//p.setIdProd(request.getParameter("nomS"));
		try {
			pro = ctrlP.getById(request.getParameter("nomS"));
		} catch (Exception e1) {
			response.setStatus(502);
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error al getByName() en eliminar() en ABMProducto");
		}
		if (pro==null) {
			request.setAttribute("url", "start");
			request.setAttribute("error", "Producto no encontrado al intentar eliminar.");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
			dispatcher.forward(request,response);
		}
		else {
			try {
				ctrlP.delete(pro);
			} catch (Exception e) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Al eliminar producto");
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/AccionProducto.jsp");
			dispatcher.forward(request,response);
		}
	}

	private void insertar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (request.getParameter("selectSala").equals("") || request.getParameter("nom").equals("") ||request.getParameter("cos").equals(""))
		{
			request.setAttribute("url", "start");
			request.setAttribute("error", "Para crear un producto se requieren todos los campos");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
			dispatcher.forward(request,response);
		}
		else
		{
			Producto pr = new Producto();
			Sala s = new Sala();
			CtrlABMSala ctrSala = new CtrlABMSala();
			Persona pe = new Persona();
			CtrlABMPersona ctrlPer = new CtrlABMPersona();
			request.setAttribute("accion", "insertar");
			
			try {
				pr.setSala(ctrSala.getById(Integer.parseInt(request.getParameter("selectSala"))));
			} catch (Exception e1) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMProducto al intentar add(Sala)");
			}
			try {
				Persona p2 = new Persona();
				p2.setNombre(request.getParameter("nombreO"));
				p2.setApellido(request.getParameter("apellidoO"));
				p2.setDni(request.getParameter("dniO"));
				pr.setPersona(ctrlPer.getByDni(p2));
			} catch (Exception e1) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMProducto al intentar add(Persona)");
			}
			
			pr.setNombre(request.getParameter("nom"));
			try { pr.setCosto(Float.parseFloat(request.getParameter("cos")));} 
			catch (Exception e2) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMProducto al intentar setCosto()");
			}
			
			try { 
				CtrlABMProducto ctrl = new CtrlABMProducto();
				ctrl.add(pr);
 			} catch (Exception e3) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMProducto al intentar add(Producto)");
			}
			try {
				request.setAttribute("producto", pr);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/AccionProducto.jsp");
				dispatcher.forward(request,response);
				} catch (ServletException e4) {
					throw e4;
					//response.setStatus(502);
					//response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMProducto al redireccionar a AccionProducto");
				}
			
			
		}
	}

	private void buscar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (request.getParameter("nomS" ).equals(""))
		{
			request.setAttribute("url", "start");
			request.setAttribute("error", "Nombre es obligatorio");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
			dispatcher.forward(request,response);
		}
		else
		{
		Producto p = new Producto();
		request.setAttribute("accion", "buscar");
		p.setNombre(request.getParameter("nomS"));
		CtrlABMProducto ctrl=new CtrlABMProducto();
		Producto pro = new Producto();
			try {
				pro=ctrl.getByName(p);
			} catch (Exception e) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en ABMProducto al buscar en getByName()");
			} 
			if (pro==null) {
				request.setAttribute("url", "start");
				request.setAttribute("error", "Producto no encontrado");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Error.jsp");
				dispatcher.forward(request,response);
			}
			else {
				request.setAttribute("producto", pro);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/AccionProducto.jsp");
				dispatcher.forward(request,response);
			}
		}
		
	}
	
	private void error(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setStatus(404);
		response.sendError(HttpServletResponse.SC_NOT_FOUND, "Error en ABMProducto al seleccionar opcion (switch) - Gracias Guss :D");
	}

}
