package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import entidades.Cliente;
import entidades.Cuenta;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocioImpl.ClienteNegociolmpl;
import negocioImpl.CuentaNegocioImpl;

/**
 * Servlet implementation class ServletBuscarTitularCBU
 */
@WebServlet("/ServletBuscarTitularCBU")
public class ServletBuscarTitularCBU extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
	private CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();
	    private ClienteNegocio clienteNegocio = new ClienteNegociolmpl();
	    
	    
    public ServletBuscarTitularCBU() {
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   String cbu = request.getParameter("cbu");

		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");

		    PrintWriter out = response.getWriter();

		    if (cuentaNegocio.existeCBU(cbu)) {
		        Cuenta cuenta = cuentaNegocio.buscarIdConCbu(cbu);
		        Cliente cliente = clienteNegocio.BuscarPorID(cuenta.getIdCliente());

		        String json = "{ \"nombre\": \"" + cliente.getNombre() + "\", " +
		                      "\"apellido\": \"" + cliente.getApellido() + "\", " +
		                      "\"dni\": \"" + cliente.getDni() + "\" }";

		        out.print(json);
		    } else {
		        out.print("{ \"error\": \"CBU no encontrado\" }");
		    }

		    out.flush();
		}

}
