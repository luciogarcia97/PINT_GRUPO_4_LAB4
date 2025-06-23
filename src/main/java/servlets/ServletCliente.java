package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDao;
import entidades.Cliente;

@WebServlet("/ServletCliente")
public class ServletCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletCliente() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
			c.setProvincia(request.getParameter("txtProvinicia"));
			c.setCorreoElectronico(request.getParameter("txtCorreoElectronico"));
			c.setEliminado(false);
			
			ClienteDao cDao = new ClienteDao();
			resultado = cDao.insertarCliente(c);
			
			request.setAttribute("resultado", resultado);
			RequestDispatcher rd = request.getRequestDispatcher("/registrarUsuario.jsp");
			rd.forward(request, response);
			
		}
		
	}

}
