package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidades.Usuario;
import negocio.UsuarioNegocio;
import negocioImpl.UsuarioNegocioImpl;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   private UsuarioNegocio usuarioNegocio;
	
    public ServletLogin() {
        super();
        this.usuarioNegocio = new UsuarioNegocioImpl();
        
    }
/////
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("btnLogin") != null) {			
			
			String usuario = request.getParameter("usuario").trim();
			String contrasena = request.getParameter("contrasena").trim();
			boolean loginExitoso = false;
			
			List<Usuario> lista = usuarioNegocio.obtenerUsuarios();				
			
			for(Usuario usuarioLog : lista) {
								
				if(usuarioLog.getUsuario().equals(usuario) &&
				   usuarioLog.getContrasena().equals(contrasena)) {					
					
					  			      
					  if (usuarioLog.getEliminado() == 0) {
						  loginExitoso = true;
						  if (usuarioLog.getTipo_usuario().equals("admin")){
		                    request.getSession().setAttribute("adminLogueado", usuarioLog);
							  response.sendRedirect("ServletCliente?listar=1");
		                } else {
		                    request.getSession().setAttribute("usuarioLogueado", usuarioLog);
						    response.sendRedirect("ServletClienteMenu");
						}
		             }	
				 break;    
				 }
				
			}
			
			if (!loginExitoso) {
			    request.setAttribute("error", "Usuario o contraseña incorrectos");
			    request.getRequestDispatcher("index.jsp").forward(request, response);
			}
						
		}
		
		
		if(request.getParameter("btnCerrar") != null) {
			HttpSession session = request.getSession(false);
		    
			if (session != null) {
		        session.invalidate();
		    }
		    
		    request.getSession(true);
		    response.sendRedirect("index.jsp");
			
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}