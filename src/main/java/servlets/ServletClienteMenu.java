package servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Cliente;

import entidades.Usuario;
import negocio.ClienteNegocio;
import negocioImpl.ClienteNegociolmpl;


@WebServlet("/ServletClienteMenu")
public class ServletClienteMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ClienteNegocio clienteNegocio;
       
    
    public ServletClienteMenu() {
        super();
        this.clienteNegocio = new ClienteNegociolmpl();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
		if (usuarioLogueado == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		request.getSession().setAttribute("usuarioLogueado", usuarioLogueado);
			
		Cliente cliente = clienteNegocio.BuscarPorID(usuarioLogueado.getId_cliente());
		request.setAttribute("cliente", cliente);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("usuarioClienteMenu.jsp");
		dispatcher.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

}
