package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("btnLogin") != null) {			
			
			String usuario = request.getParameter("usuario");
			String contrasena = request.getParameter("contrasena");
			boolean loginExitoso = false;
			
			List<Usuario> lista = usuarioNegocio.obtenerUsuarios();				
			
			for(Usuario usuarioLog : lista) {
				
				System.out.println(usuarioLog.toString());
				
				if(usuarioLog.getUsuario().equals(usuario) &&
				   usuarioLog.getContrasena().equals(contrasena)) {					
					
					  loginExitoso = true;						  
					  			      
				      if (usuario.equals("admin") && contrasena.equals("123")) {
		                    request.getSession().setAttribute("adminLogueado", usuarioLog);
		                    request.getRequestDispatcher("administrarClientes.jsp").forward(request, response);
		                } else {
		                    request.getSession().setAttribute("usuarioLogueado", usuarioLog);
		                    request.getRequestDispatcher("usuarioCliente.jsp").forward(request, response); 
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
				
				request.getSession().invalidate();			
				response.sendRedirect("index.jsp");
			}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
