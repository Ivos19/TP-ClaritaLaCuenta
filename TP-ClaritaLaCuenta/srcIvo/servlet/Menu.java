package servlet;

import controlers.*;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Menu
 */
@WebServlet({ "/Menu/*", "/menu/*", "/menue/*", "/Menue/*" })
public class Menu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		switch (request.getPathInfo()) {
		case "/nuevaPersona":
			this.nuevaPersona(request, response);
			break;
		case "/sala":
			this.nuevaSala(request, response);
			break;
		case "/nuevoProducto":
			this.nuevoProducto(request, response);
			break;

		default:
			this.error(request, response);
			break;
		}
	}

	private void nuevoProducto(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setAttribute("texto", "Texto de request para PRODUCTO");
		CtrlABMProducto ctrl = new CtrlABMProducto();
		CtrlABMSala ctrlS = new CtrlABMSala();
		try {
			request.setAttribute("listaSalas", ctrlS.getAll());
			request.setAttribute("listaProductos", ctrl.getAll());

		} catch (Exception e1) {
			response.setStatus(502);
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en Menu.nuevaPersona() al obtener Peersonas y Categorias");
		}

		try {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/ABMProducto.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			response.setStatus(502);
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en Menu.nuevoProducto() al dispatch");
		} // TODO Auto-generated method stub

	}

	private void error(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(404);
		response.sendError(HttpServletResponse.SC_NOT_FOUND,
				"Ivo, La pagina solicitada no fue encontrada (en Menu.java)");
	}

	private void nuevaSala(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//CtrlABMProducto ctrl = new CtrlABMProducto();
		CtrlABMSala ctrlS = new CtrlABMSala();
		try {
			//request.setAttribute("listaProductos", ctrl.getAll());
			request.setAttribute("listaSalas", ctrlS.getAll());
		} catch (Exception e1) {
			response.setStatus(502);
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en Menu.nuevaPersona() al obtener Peersonas y Categorias");
		}


		try {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/ABMSala.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			response.setStatus(502);
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Ivo, Error de Servidor");
		}
	}

	private void nuevaPersona(HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.setAttribute("texto", "Texto de request para Persona");

		CtrlABMPersona ctr = new CtrlABMPersona();

		try {

			request.setAttribute("listaPersonas", ctr.getAll());
			request.setAttribute("listaCategorias", ctr.getCategorias());

		} catch (Exception e1) {
			response.setStatus(502);
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY,
					"Error en Menu.nuevaPersona() al obtener Peersonas y Categorias");
		}

		try {

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/ABMPersona.jsp");
			dispatcher.forward(request, response);

		} catch (ServletException e2) {
			response.setStatus(502);
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error en Menu.nuevaPersona() al dispatch");
		}

		/*
		 * try { RequestDispatcher dispatcher =
		 * getServletContext().getRequestDispatcher("/WEB-INF/ABMPersona.jsp");
		 * dispatcher.forward(request,response); } catch (ServletException e) {
		 * response.setStatus(502);
		 * response.sendError(HttpServletResponse.SC_BAD_GATEWAY,
		 * "Ivo, Error de Servidor"); }
		 */
	}

}
