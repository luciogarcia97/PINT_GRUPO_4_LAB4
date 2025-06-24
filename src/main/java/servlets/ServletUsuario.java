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
import negocioImpl.UsuarioNegocioImpl;

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Boolean resultado = false;
		if (request.getParameter("btnRegistrarUsuario") != null) {
			Usuario usuario = new Usuario();
			usuario.setId_usuario(Integer.parseInt(request.getParameter("txtIDCliente").toString()));
			usuario.setId_cliente(Integer.parseInt(request.getParameter("txtIDCliente").toString()));
			usuario.setUsuario(request.getParameter("txtUsuario"));
			usuario.setContrasena(request.getParameter("txtContrasena"));
			usuario.setTipo_usuario("cliente6");
			usuario.setEliminado(0);
			usuario.setFecha_creacion(LocalDate.now());

			resultado = usuarioNegocio.insertarUsuario(usuario);

			request.setAttribute("cargoUsuario", resultado);
			RequestDispatcher rd = request.getRequestDispatcher("/registrarUsuario.jsp");
			rd.forward(request, response);

			if (request.getParameter("eliminar") != null) {

				int idUsuario = Integer.parseInt(request.getParameter("idEliminar"));

				resultado = usuarioNegocio.eliminarUsuario(idUsuario);

				cargarFormulario(request, response);
			}

		}
		
		
		if(request.getParameter("btnModificar") != null) {
									
			int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
			String usuarioNuevo = request.getParameter("txtNombre");
			String clave = request.getParameter("txtContrasena");
			int idCliente = Integer.parseInt(request.getParameter("idCliente"));
			String fechaCreacionStr = request.getParameter("fechaCreacion");
						
			LocalDate fechaCreacion = LocalDate.parse(fechaCreacionStr);
			
			Usuario usuario = new Usuario();
			usuario.setId_usuario(idUsuario);
			usuario.setId_cliente(idCliente); 
			usuario.setUsuario(usuarioNuevo);
			usuario.setContrasena(clave);
			usuario.setTipo_usuario("cliente");
			usuario.setEliminado(0);
			usuario.setFecha_creacion(fechaCreacion);
			
			if (usuarioNegocio.modificarUsuario(usuario)) {			 
				
				List<Usuario> listaUsuarios = usuarioNegocio.obtenerUsuarios();
				request.setAttribute("listaUsuarios", listaUsuarios);		    
			   
			    RequestDispatcher rd = request.getRequestDispatcher("/administrarUsuarios.jsp");
			    rd.forward(request, response);
			} 
			
		}
		
		if(request.getParameter("eliminar") != null) {
			
			int idEliminar = Integer.parseInt(request.getParameter("idEliminar"));			
		
			
			/*if (usuarioNegocio.eliminarUsuario(idEliminar)) {			 
							
				List<Usuario> listaUsuarios = usuarioNegocio.obtenerUsuarios();
				request.setAttribute("listaUsuarios", listaUsuarios);		    
						   
			    RequestDispatcher rd = request.getRequestDispatcher("/administrarUsuarios.jsp");
				rd.forward(request, response);
			 } */
			
		}
		
		
	}

	private void cargarFormulario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// List<Cliente> listadoClientes = clienteNegocio.obtenerClientes();
		// request.setAttribute("listaClientes", listadoClientes);

	}

}
