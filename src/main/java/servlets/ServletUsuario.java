package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Cliente;
import entidades.Usuario;
import negocio.UsuarioNegocio;
import negocio.ClienteNegocio;
import negocioImpl.UsuarioNegocioImpl;
import negocioImpl.ClienteNegociolmpl;

@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioNegocio usuarioNegocio;
	

	public ServletUsuario() {
		super();
		this.usuarioNegocio = new UsuarioNegocioImpl();		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("listar") != null) {
			List<Usuario> listaUsuarios = usuarioNegocio.obtenerUsuarios();

			request.setAttribute("listaUsuarios", listaUsuarios);

			RequestDispatcher rd = request.getRequestDispatcher("/administrarUsuarios.jsp");
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		
		if(request.getParameter("btnModificar") != null) {
									
			int idUsuario = Integer.parseInt(request.getParameter("idUsuario").trim());
			String usuarioNuevo = request.getParameter("txtNombre").trim();
			String clave = request.getParameter("txtContrasena");
			int idCliente = Integer.parseInt(request.getParameter("idCliente").trim());
			String fechaCreacionStr = request.getParameter("fechaCreacion").trim();
			String tipoUsuario = request.getParameter("tipoUsuario").trim();
						
			LocalDate fechaCreacion = LocalDate.parse(fechaCreacionStr);
			
			Usuario usuario = new Usuario();
			usuario.setId_usuario(idUsuario);
			usuario.setId_cliente(idCliente); 
			usuario.setUsuario(usuarioNuevo);
			usuario.setContrasena(clave);
			usuario.setTipo_usuario(tipoUsuario);
			usuario.setEliminado(0);
			usuario.setFecha_creacion(fechaCreacion);			
			
			
			boolean existe = usuarioNegocio.existeNombreUsuario(usuarioNuevo, idUsuario);
						
			if (existe) {
			   
				request.setAttribute("errorNombre", true); 			    
			    RequestDispatcher rd = request.getRequestDispatcher("/modificarUsuario.jsp");
			    rd.forward(request, response);
			    return;
			}
			
			if (usuarioNegocio.modificarUsuario(usuario)) {			 
				
				List<Usuario> listaUsuarios = usuarioNegocio.obtenerUsuarios();
				request.setAttribute("listaUsuarios", listaUsuarios);		    
			   
				request.setAttribute("exitoModificado", true);
			    RequestDispatcher rd = request.getRequestDispatcher("/administrarUsuarios.jsp");
			    rd.forward(request, response);
			} 
			
		}
		
		if(request.getParameter("eliminar") != null) {
			
			int idEliminar = Integer.parseInt(request.getParameter("idEliminar"));			
			int idCliente = Integer.parseInt(request.getParameter("idCliente"));
			
			if (usuarioNegocio.eliminarClienteUsuarioCuentas(idEliminar, idCliente)) {			 
							
				List<Usuario> listaUsuarios = usuarioNegocio.obtenerUsuarios();
				request.setAttribute("listaUsuarios", listaUsuarios);		    
					
				request.setAttribute("exito", true);
			    RequestDispatcher rd = request.getRequestDispatcher("/administrarUsuarios.jsp");
				rd.forward(request, response);
			 } else {
					
				 	request.setAttribute("error", false);
				    List<Usuario> listaUsuarios = usuarioNegocio.obtenerUsuarios();
				    request.setAttribute("listaUsuarios", listaUsuarios);		
				    RequestDispatcher rd = request.getRequestDispatcher("/administrarUsuarios.jsp");
				    rd.forward(request, response);
			 }
			
		}		
		
	}
	

}
