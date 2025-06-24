package servlets;

import java.io.IOException;
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
import negocioImpl.ClienteNegociolmpl;

@WebServlet("/ServletCliente")
public class ServletCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteNegociolmpl clienteNegocio;
       
   
    public ServletCliente() {
        super();
        this.clienteNegocio = new ClienteNegociolmpl();
        
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		// Para mostrar el formulario de registro
	    if (request.getParameter("registrar") != null) {
	        RequestDispatcher rd = request.getRequestDispatcher("/registrarCliente.jsp");
	        rd.forward(request, response);
	    }
	    
	    // Para listar todos los clientes
	    if (request.getParameter("listar") != null) {
	        List<Cliente> listaClientes = clienteNegocio.obtenerClientes();
	        
	        request.setAttribute("listaClientes", listaClientes);
	        
	        RequestDispatcher rd = request.getRequestDispatcher("/administrarClientes.jsp");
	        rd.forward(request, response);
	    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Entre en el doPost");
		
		if(request.getParameter("btnRegistrarCliente")!= null) 
		{
			
			Cliente c = new Cliente();
			Boolean resultado = false;
			
			c.setDni(Integer.parseInt(request.getParameter("txtDni")));
			c.setCuil(request.getParameter("txtCuil"));
			c.setNombre(request.getParameter("txtNombre"));
			c.setApellido(request.getParameter("txtApellido"));
			c.setSexo(request.getParameter("txtSexo"));
			c.setNacionalidad(request.getParameter("txtNacionalidad"));
			c.setFechaNacimiento(request.getParameter("txtFechaNacimiento"));
			c.setDireccion(request.getParameter("txtDireccion"));
			c.setLocalidad(request.getParameter("txtLocalidad"));
			c.setProvincia(request.getParameter("txtProvincia"));
			c.setCorreoElectronico(request.getParameter("txtCorreo"));
			c.setEliminado(false);
			
			System.out.println("Cargue el cliente");
			
			resultado = clienteNegocio.insertarCliente(c);
			if (resultado) System.out.println("Registre el cliente");
			request.setAttribute("resultado", resultado);
			RequestDispatcher rd = request.getRequestDispatcher("/registrarUsuario.jsp");
			rd.forward(request, response);
      cargarFormulario(request, response);
		}
		
		if(request.getParameter("btnModificarCliente")!= null) {
			
			
			Cliente c = new Cliente();
			Boolean resultado = false;
			
			c.setNombre(request.getParameter("txtNombre"));
			c.setApellido(request.getParameter("txtApellido"));
			c.setSexo(request.getParameter("txtSexo"));
			c.setNacionalidad(request.getParameter("txtNacionalidad"));
			c.setFechaNacimiento(request.getParameter("txtFechaNacimiento"));
			c.setDireccion(request.getParameter("txtDireccion"));
			c.setLocalidad(request.getParameter("txtLocalidad"));
			c.setProvincia(request.getParameter("txtProvinicia"));
			c.setCorreoElectronico(request.getParameter("txtEmail"));
			
			resultado = clienteNegocio.insertarCliente(c);
			
		}
		
		if(request.getParameter("eliminar")!= null) 
		{
			
			Boolean resultado = false;
			
			int idCliente = Integer.parseInt(request.getParameter("idEliminar"));
			
			resultado = clienteNegocio.eliminarCliente(idCliente);
			
			cargarFormulario(request, response);
		}
		
	}

	private void cargarFormulario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
       // List<Cliente> listadoClientes = 	clienteNegocio.obtenerClientes();
        //request.setAttribute("listaClientes", listadoClientes);
        
        RequestDispatcher rd = request.getRequestDispatcher("/administrarClientes.jsp");
        rd.forward(request, response);
    }
}
