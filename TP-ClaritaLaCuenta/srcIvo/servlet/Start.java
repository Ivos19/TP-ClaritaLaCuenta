package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controlers.CtrlABMPersona;
import entity.Persona;

/**
 * Servlet implementation class Start
 */
@WebServlet({ "/Start", "/start" })
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Start() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		if(request.getSession().getAttribute("user")==null) {
			String user=request.getParameter("user");
			String pass=request.getParameter("pass");
			
			Persona per=new Persona();
			per.setUser(user);
			per.setPass(pass);
			
			CtrlABMPersona ctrl= new CtrlABMPersona();
			Persona pers=new Persona();
			
			try {
				pers=ctrl.login(per);
				if(pers==null)
				{
					request.setAttribute("url", "login.html");
					request.setAttribute("error", "La cuenta no esta clarita. Verifique usuario, contrase�a y habilitaci�n.");
					request.getRequestDispatcher("Error.jsp").forward(request, response);
				}
				else
				{
					request.getSession().setAttribute("user", pers);
					request.getRequestDispatcher("WEB-INF/welcome.jsp").forward(request, response);
				}
			} catch (Exception e) {
				response.setStatus(502);
				response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Error de Servidor");
			}
		} else
			{
			request.getRequestDispatcher("WEB-INF/welcome.jsp").forward(request, response);
			}
			//request.setAttribute("listaPersonas", ctrl.getAll());
			
			//request.getSession().setAttribute("user", pers);
			
			//request.getRequestDispatcher("WEB-INF/welcome.jsp").forward(request, response);
			//response.getWriter().append(user).append(" ").append(pass);
			
			
		 //catch (Exception e) {
			//e.printStackTrace();
		
		//doGet(request, response);
	}

}
