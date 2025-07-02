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
import negocio.UsuarioNegocio;
import entidades.Cliente;
import entidades.Validaciones;
import entidades.Localidad;
import entidades.Provincia;
import entidades.Usuario;
import excepciones.ClienteMenorEdad;
import excepciones.CuilInvalido;
import excepciones.DniInvalido;
import entidades.TipoCuenta;
import negocioImpl.ClienteNegociolmpl;
import negocioImpl.UsuarioNegocioImpl;

@WebServlet("/ServletCliente")
public class ServletCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteNegociolmpl clienteNegocio;
	private UsuarioNegocioImpl usuarioNegocio;
       
   
    public ServletCliente() {
        super();
        this.clienteNegocio = new ClienteNegociolmpl();
        this.usuarioNegocio = new UsuarioNegocioImpl();
        
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		  if (request.getParameter("cargarFormulario") != null) {
		        List<Provincia> listaProvincia = clienteNegocio.listarProvincias();
		        request.setAttribute("listaProvincias", listaProvincia);
		        List<Localidad> listaLocalidades = clienteNegocio.listarLocalidades();
		        request.setAttribute("listaLocalidades", listaLocalidades);

		        RequestDispatcher rd = request.getRequestDispatcher("/registrarCliente.jsp");
		        rd.forward(request, response);
		        return;
		    }
		
	    
	    if (request.getParameter("listar") != null) {
	        List<Cliente> listaClientes = clienteNegocio.obtenerClientes();
	        
	        request.setAttribute("listaClientes", listaClientes);
	        
	        RequestDispatcher rd = request.getRequestDispatcher("/administrarClientes.jsp");
	        rd.forward(request, response);
	    }
	    
	   
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Boolean resultado = false;
		int resultado1 = -1;
		
		if(request.getParameter("btnRegistrarCliente")!= null) {
			
			Cliente cliente = new Cliente();			
			
			try {
				
				Validaciones.verificarDniInvalido(request.getParameter("txtDni"));				
				cliente.setDni(Integer.parseInt(request.getParameter("txtDni")));
				
				LocalDate fechaNacimiento = LocalDate.parse(request.getParameter("txtFechaNacimiento").trim());
			    Validaciones.esMenorDeEdad(fechaNacimiento);				
				cliente.setFechaNacimiento(request.getParameter("txtFechaNacimiento"));
				
				Validaciones.verificarCuilInvalido(request.getParameter("txtCuil"));
				cliente.setCuil(request.getParameter("txtCuil"));
				
				cliente.setNombre(request.getParameter("txtNombre"));
				cliente.setApellido(request.getParameter("txtApellido"));
				cliente.setSexo(request.getParameter("txtSexo"));
				cliente.setNacionalidad(request.getParameter("txtNacionalidad"));			
				cliente.setDireccion(request.getParameter("txtDireccion"));
				cliente.setLocalidad(request.getParameter("ddlLocalidades"));
				cliente.setProvincia(request.getParameter("ddlProvincias"));
				cliente.setCorreoElectronico(request.getParameter("txtCorreo"));
				cliente.setEliminado(false);
				
				
			} catch (DniInvalido e) {
				
				request.setAttribute("dniInvalido", true);
				
				List<Provincia> listaProvincia = clienteNegocio.listarProvincias();
			    request.setAttribute("listaProvincias", listaProvincia);

			    List<Localidad> listaLocalidades = clienteNegocio.listarLocalidades();
			    request.setAttribute("listaLocalidades", listaLocalidades);				
				
				RequestDispatcher rd = request.getRequestDispatcher("/registrarCliente.jsp");
				rd.forward(request, response);
				
				e.getMessage();
				
				return; 
				
		   } catch (ClienteMenorEdad e) {				
				
				request.setAttribute("menorEdad", true);
				
				List<Provincia> listaProvincia = clienteNegocio.listarProvincias();
			    request.setAttribute("listaProvincias", listaProvincia);

			    List<Localidad> listaLocalidades = clienteNegocio.listarLocalidades();
			    request.setAttribute("listaLocalidades", listaLocalidades);				
				
				RequestDispatcher rd = request.getRequestDispatcher("/registrarCliente.jsp");
				rd.forward(request, response);
				e.getMessage();
				return;
				
			} catch (CuilInvalido e) {
				
				request.setAttribute("cuilInvalido", true);
				
				List<Provincia> listaProvincia = clienteNegocio.listarProvincias();
			    request.setAttribute("listaProvincias", listaProvincia);

			    List<Localidad> listaLocalidades = clienteNegocio.listarLocalidades();
			    request.setAttribute("listaLocalidades", listaLocalidades);				
				
				RequestDispatcher rd = request.getRequestDispatcher("/registrarCliente.jsp");
				rd.forward(request, response);
				
				e.getMessage();
				
				return; 	
			}							
			
			
			if(clienteNegocio.existeDni(cliente.getDni())) 
			{
				// vuelve al registro con un cartel de que hubo un dni repetido	
				request.setAttribute("dni", 1);
				request.setAttribute("clienteEditado", cliente);
				RequestDispatcher rd = request.getRequestDispatcher("/registrarCliente.jsp");
				rd.forward(request, response);	
				return;
			}
			
			if(clienteNegocio.existeCuil(cliente.getCuil()))
			{
				// vuelve al registro con un cartel de que hubo un cuil repetido
				request.setAttribute("cuil", 1);
				request.setAttribute("clienteEditado", cliente);
				RequestDispatcher rd = request.getRequestDispatcher("/registrarCliente.jsp");
				rd.forward(request, response);
				return;
			}
			
			resultado1 = clienteNegocio.insertarCliente(cliente); //inserta cliente devuelve ultimo ID
						
			Usuario usuario = new Usuario();			
			usuario.setId_cliente(resultado1);
			usuario.setUsuario(request.getParameter("txtUsuario"));
			usuario.setContrasena(request.getParameter("txtContrasena"));
			usuario.setTipo_usuario(request.getParameter("txtTipoUsuario"));
			usuario.setEliminado(0);			
			usuario.setFecha_creacion(LocalDate.now());
			
			List<Usuario> listaUsuario = usuarioNegocio.obtenerUsuarios();
			
			for(Usuario u : listaUsuario) // Chequea que ya exista ese nombre de usuario de un usuario activo
			{
				if(u.getUsuario().equals(usuario.getUsuario())) 
				{
					// vuelve al registro con un cartel de que hubo un usuario repetido
					request.setAttribute("usuario", 1);
	
					RequestDispatcher rd = request.getRequestDispatcher("/registrarCliente.jsp");
					rd.forward(request, response);
					return;
				}
			}			
			
			resultado = usuarioNegocio.insertarUsuario(usuario); // Guarda en DB al usuario

			if(resultado1 > 0 && resultado) {
					
				List<Cliente> listaClientes = clienteNegocio.obtenerClientes();
				request.setAttribute("listaClientes", listaClientes);

				RequestDispatcher rd = request.getRequestDispatcher("/administrarClientes.jsp");
				rd.forward(request, response);				
					
			} else {
					
				request.setAttribute("error", "No se pudo registrar el cliente.");
				List<Cliente> listaClientes = clienteNegocio.obtenerClientes();
				request.setAttribute("listaUsuarios", listaClientes);		
				RequestDispatcher rd = request.getRequestDispatcher("/administrarClientes.jsp");
				rd.forward(request, response);
			}							
		}
		
		
		//MODIFICAR CLIENTE
		
		if(request.getParameter("btnModificarCliente")!= null) {			
			
			Cliente c = new Cliente();
			Boolean resultado2 = false;
			
			try {				
				
				Validaciones.verificarDniInvalido(request.getParameter("txtDni"));				
				c.setDni(Integer.parseInt(request.getParameter("txtDni")));
				
				Validaciones.verificarCuilInvalido(request.getParameter("txtCuil"));
				c.setCuil(request.getParameter("txtCuil"));				
				
				LocalDate fechaNacimiento = LocalDate.parse(request.getParameter("txtFechaNacimiento").trim());
			    Validaciones.esMenorDeEdad(fechaNacimiento);				
				c.setFechaNacimiento(request.getParameter("txtFechaNacimiento"));				
				
				c.setIdCliente(Integer.parseInt(request.getParameter("idCliente"))); 
				c.setNombre(request.getParameter("txtNombre"));
				c.setApellido(request.getParameter("txtApellido"));
				c.setSexo(request.getParameter("txtSexo"));
				c.setNacionalidad(request.getParameter("txtNacionalidad"));				
				c.setDireccion(request.getParameter("txtDireccion"));
				c.setLocalidad(request.getParameter("txtLocalidad"));
				c.setProvincia(request.getParameter("txtProvincia"));
				c.setCorreoElectronico(request.getParameter("txtEmail"));				
				
			} catch (DniInvalido e) {
				
				request.setAttribute("dniInvalido", true);
				
				List<Provincia> listaProvincia = clienteNegocio.listarProvincias();
			    request.setAttribute("listaProvincias", listaProvincia);

			    List<Localidad> listaLocalidades = clienteNegocio.listarLocalidades();
			    request.setAttribute("listaLocalidades", listaLocalidades);				
				
				RequestDispatcher rd = request.getRequestDispatcher("/modificarCliente.jsp");
				rd.forward(request, response);
				
				e.getMessage();
				
				return;
				
		   } catch (CuilInvalido e) {
				
				request.setAttribute("cuilInvalido", true);
				
				List<Provincia> listaProvincia = clienteNegocio.listarProvincias();
			    request.setAttribute("listaProvincias", listaProvincia);

			    List<Localidad> listaLocalidades = clienteNegocio.listarLocalidades();
			    request.setAttribute("listaLocalidades", listaLocalidades);				
				
				RequestDispatcher rd = request.getRequestDispatcher("/modificarCliente.jsp");
				rd.forward(request, response);
				
				e.getMessage();
				
				return; 
				
			} catch (ClienteMenorEdad e) {				
				
				request.setAttribute("menorEdad", true);
				
				List<Provincia> listaProvincia = clienteNegocio.listarProvincias();
			    request.setAttribute("listaProvincias", listaProvincia);

			    List<Localidad> listaLocalidades = clienteNegocio.listarLocalidades();
			    request.setAttribute("listaLocalidades", listaLocalidades);				
				
				RequestDispatcher rd = request.getRequestDispatcher("/modificarCliente.jsp");
				rd.forward(request, response);
				e.getMessage();
				return;				
			}		
						
			// Creo cliente con los atributos del ID que viene por parametro, es una replica ANTES de la modificacion
			Cliente cliente = clienteNegocio.BuscarPorID(c.getIdCliente());
			
			
			//Si el Dni existe y es distinto al del ID modificado
			if(clienteNegocio.existeDni(c.getDni()) && cliente.getDni() != c.getDni())
			{
				// vuelve al registro con un cartel de que hubo un dni repetido	
				request.setAttribute("dni", 1);
				request.setAttribute("clienteEditado", c);
				RequestDispatcher rd = request.getRequestDispatcher("/modificarCliente.jsp");
				rd.forward(request, response);	
				return;									
			}
			
			if(clienteNegocio.existeCuil(c.getCuil()) && !cliente.getCuil().equals(c.getCuil())) //Si el Cuil existe y es distinto al de este ID
			{
				// vuelve al registro con un cartel de que hubo un cuil repetido
				request.setAttribute("cuil", 1);
					
				request.setAttribute("clienteEditado", c);
				RequestDispatcher rd = request.getRequestDispatcher("/modificarCliente.jsp");
				rd.forward(request, response);
				return;
			}
					
               if (clienteNegocio.modificarCliente(c)) {			 
				
				List<Cliente> listaClientes = clienteNegocio.obtenerClientes();
				request.setAttribute("listaClientes", listaClientes);		    
			   
			    RequestDispatcher rd = request.getRequestDispatcher("/administrarClientes.jsp");
			    rd.forward(request, response);
			} 
		}
		
		
		
		if(request.getParameter("btnEliminarCliente")!= null) {
					
			int idCliente = Integer.parseInt(request.getParameter("idCliente"));			
			
			int idEliminar = usuarioNegocio.buscarPorIDUsuario(idCliente);			
			
			if (usuarioNegocio.eliminarUsuario(idEliminar, idCliente)) {			 
							
				List<Cliente> listaClientes = clienteNegocio.obtenerClientes();
				request.setAttribute("listaClientes", listaClientes);		    
						   
			    RequestDispatcher rd = request.getRequestDispatcher("/administrarClientes.jsp");
				rd.forward(request, response);
			 } else {
					
					request.setAttribute("error", "No se pudo eliminar el cliente.");
				    List<Cliente> listaClientes = clienteNegocio.obtenerClientes();
				    request.setAttribute("listaClientes", listaClientes);		
				    RequestDispatcher rd = request.getRequestDispatcher("/administrarClientes.jsp");
				    rd.forward(request, response);
				}			
		}
		
	}


}