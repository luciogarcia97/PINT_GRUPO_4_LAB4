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

import dao.ClienteDao;
import negocio.ClienteNegocio;
import entidades.Cliente;
import entidades.TipoCuenta;
import entidades.Usuario;
import negocioImpl.ClienteNegociolmpl;

@WebServlet("/ServletCliente")
public class ServletCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteNegociolmpl clienteNegocio;
       
   
    public ServletCliente() {
        super();
        this.clienteNegocio = new ClienteNegociolmpl();
        
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	    
	    // Para listar todos los clientes
	    if (request.getParameter("listar") != null) {
	        List<Cliente> listaClientes = clienteNegocio.obtenerClientes();
	        
	        request.setAttribute("listaClientes", listaClientes);
	        
	        RequestDispatcher rd = request.getRequestDispatcher("/administrarClientes.jsp");
	        rd.forward(request, response);
	    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Boolean resultado = false;
		Boolean resultado1 = false;
		
		if(request.getParameter("btnRegistrarCliente")!= null) {
			
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
			cliente.setEliminado(true);
			
			System.out.println("Cargue el cliente");
			
			resultado1 = clienteNegocio.insertarCliente(cliente);
			int ultimoId = clienteNegocio.ultimoIdCliente();
			
			Usuario usuario = new Usuario();			
			usuario.setId_cliente(ultimoId);
			usuario.setUsuario(request.getParameter("txtUsuario"));
			usuario.setContrasena(request.getParameter("txtContrasena"));
			usuario.setTipo_usuario("cliente");
			usuario.setEliminado(1);
			usuario.setFecha_creacion(LocalDate.now());

			resultado = clienteNegocio.insertarUsuario(usuario);

			if(resultado1 && ultimoId != -1 && resultado) {
				
				List<Cliente> listaClientes = clienteNegocio.obtenerClientes();
				request.setAttribute("listaClientes", listaClientes);

				RequestDispatcher rd = request.getRequestDispatcher("/administrarClientes.jsp");
				rd.forward(request, response);				
				
			} else {
				
				request.setAttribute("error", "No se pudo eliminar el usuario.");
			    List<Cliente> listaClientes = clienteNegocio.obtenerClientes();
			    request.setAttribute("listaUsuarios", listaClientes);		
			    RequestDispatcher rd = request.getRequestDispatcher("/administrarClientes.jsp");
			    rd.forward(request, response);
			}
			
			
		}
		
		if(request.getParameter("btnModificarCliente")!= null) {
			
			
			Cliente c = new Cliente();
			System.out.println("Entre al modificar");
			Boolean resultado2 = false;
		    c.setIdCliente(Integer.parseInt(request.getParameter("idCliente"))); 
			c.setCuil(request.getParameter("txtCuil"));
			c.setNombre(request.getParameter("txtNombre"));
			c.setApellido(request.getParameter("txtApellido"));
			c.setSexo(request.getParameter("txtSexo"));
			c.setNacionalidad(request.getParameter("txtNacionalidad"));
			c.setFechaNacimiento(request.getParameter("txtFechaNacimiento"));
			c.setDireccion(request.getParameter("txtDireccion"));
			c.setLocalidad(request.getParameter("txtLocalidad"));
			c.setProvincia(request.getParameter("txtProvincia"));
			c.setCorreoElectronico(request.getParameter("txtEmail"));
			
               if ( clienteNegocio.modificarCliente(c)) {			 
				
				List<Cliente> listaClientes = clienteNegocio.obtenerClientes();
				request.setAttribute("listaClientes", listaClientes);		    
			   
			    RequestDispatcher rd = request.getRequestDispatcher("/administrarClientes.jsp");
			    rd.forward(request, response);
			} 
		}
		
		if(request.getParameter("btnEliminarCliente")!= null) {
					
			int idCliente = Integer.parseInt(request.getParameter("idCliente"));			
			
			int idEliminar = clienteNegocio.buscarPorIDUsuario(idCliente);			
			
			if (clienteNegocio.eliminarUsuario(idEliminar, idCliente)) {			 
							
				List<Cliente> listaClientes = clienteNegocio.obtenerClientes();
				request.setAttribute("listaClientes", listaClientes);		    
						   
			    RequestDispatcher rd = request.getRequestDispatcher("/administrarClientes.jsp");
				rd.forward(request, response);
			 } else {
					
					request.setAttribute("error", "No se pudo eliminar el usuario.");
				    List<Cliente> listaClientes = clienteNegocio.obtenerClientes();
				    request.setAttribute("listaClientes", listaClientes);		
				    RequestDispatcher rd = request.getRequestDispatcher("/administrarClientes.jsp");
				    rd.forward(request, response);
				}			
		}
		
	}


}