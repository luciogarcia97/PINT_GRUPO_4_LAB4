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
	private UsuarioNegocioImpl usuarioNegocio;
	

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
			String usuarioNuevo = request.getParameter("txtNombre");
			String clave = request.getParameter("txtContrasena");
			int idCliente = Integer.parseInt(request.getParameter("idCliente").trim());
			String fechaCreacionStr = request.getParameter("fechaCreacion").trim();
						
			LocalDate fechaCreacion = LocalDate.parse(fechaCreacionStr);
			
			Usuario usuario = new Usuario();
			usuario.setId_usuario(idUsuario);
			usuario.setId_cliente(idCliente); 
			usuario.setUsuario(usuarioNuevo);
			usuario.setContrasena(clave);
			usuario.setTipo_usuario("cliente");
			usuario.setEliminado(0);
			usuario.setFecha_creacion(fechaCreacion);
			
			Usuario existente = usuarioNegocio.buscarPorNombre(usuarioNuevo,idUsuario);
						
			if (existente != null) {
			    request.setAttribute("errorNombre", "Ya existe un usuario con ese nombre.");
			    request.setAttribute("id", idUsuario);
			    request.setAttribute("idCliente", idCliente);
			    request.setAttribute("fechaCreacion", fechaCreacion);
			    request.setAttribute("usuario", usuarioNuevo);
			    
			    RequestDispatcher rd = request.getRequestDispatcher("/modificarUsuario.jsp");
			    rd.forward(request, response);
			    return;
			}
			
			if (usuarioNegocio.modificarUsuario(usuario)) {			 
				
				List<Usuario> listaUsuarios = usuarioNegocio.obtenerUsuarios();
				request.setAttribute("listaUsuarios", listaUsuarios);		    
			   
			    RequestDispatcher rd = request.getRequestDispatcher("/administrarUsuarios.jsp");
			    rd.forward(request, response);
			} 
			
		}
		
		if(request.getParameter("eliminar") != null) {
			
			int idEliminar = Integer.parseInt(request.getParameter("idEliminar"));			
			int idCliente = Integer.parseInt(request.getParameter("idCliente"));
			
			if (usuarioNegocio.eliminarUsuario(idEliminar, idCliente)) {			 
							
				List<Usuario> listaUsuarios = usuarioNegocio.obtenerUsuarios();
				request.setAttribute("listaUsuarios", listaUsuarios);		    
						   
			    RequestDispatcher rd = request.getRequestDispatcher("/administrarUsuarios.jsp");
				rd.forward(request, response);
			 } else {
					
					request.setAttribute("error", "No se pudo eliminar el usuario.");
				    List<Usuario> listaUsuarios = usuarioNegocio.obtenerUsuarios();
				    request.setAttribute("listaUsuarios", listaUsuarios);		
				    RequestDispatcher rd = request.getRequestDispatcher("/administrarUsuarios.jsp");
				    rd.forward(request, response);
			 }
			
		}		
		
	}
	

}
