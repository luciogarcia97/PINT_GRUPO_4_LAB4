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
		Boolean resultado1 = false;		
		
		if (request.getParameter("btnRegistrarUsuario") != null) {
			
			Cliente cliente = new Cliente();			
			cliente.setDni(Integer.parseInt(request.getParameter("txtDni")));
			cliente.setCuil(request.getParameter("txtCuil"));
			cliente.setNombre(request.getParameter("txtNombre"));
			cliente.setApellido(request.getParameter("txtApellido"));
			cliente.setSexo(request.getParameter("txtSexo"));
			cliente.setNacionalidad(request.getParameter("txtNacionalidad"));
			cliente.setFechaNacimiento(request.getParameter("txtFechaNacimiento"));
			cliente.setDireccion(request.getParameter("txtDireccion"));
			cliente.setLocalidad(request.getParameter("txtLocalidad"));
			cliente.setProvincia(request.getParameter("txtProvincia"));
			cliente.setCorreoElectronico(request.getParameter("txtCorreo"));
			cliente.setEliminado(false);			

			resultado1 = usuarioNegocio.insertarCliente(cliente);
			int ultimoId = usuarioNegocio.ultimoIdCliente();
			
			Usuario usuario = new Usuario();			
			usuario.setId_cliente(ultimoId);
			usuario.setUsuario(request.getParameter("txtUsuario"));
			usuario.setContrasena(request.getParameter("txtContrasena"));
			usuario.setTipo_usuario("cliente");
			usuario.setEliminado(0);
			usuario.setFecha_creacion(LocalDate.now());

			resultado = usuarioNegocio.insertarUsuario(usuario);

			if(resultado1 && ultimoId != -1 && resultado) {
				
				List<Usuario> listaUsuarios = usuarioNegocio.obtenerUsuarios();
				request.setAttribute("listaUsuarios", listaUsuarios);

				RequestDispatcher rd = request.getRequestDispatcher("/administrarUsuarios.jsp");
				rd.forward(request, response);				
				
			} else {
				
				request.setAttribute("error", "No se pudo registrar el usuario.");
			    List<Usuario> listaUsuarios = usuarioNegocio.obtenerUsuarios();
			    request.setAttribute("listaUsuarios", listaUsuarios);		
			    RequestDispatcher rd = request.getRequestDispatcher("/administrarUsuarios.jsp");
			    rd.forward(request, response);
			}

		}
		
		
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
