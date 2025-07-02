package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Cliente;
import entidades.Cuenta;
import entidades.TipoCuenta;
import entidades.Usuario;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocio.TipoCuentaNegocio;
import negocioImpl.ClienteNegociolmpl;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.TipoCuentaNegocioImpl;

@WebServlet("/ServletClienteUsuario")
public class ServletClienteUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ClienteNegocio clienteNegocio;
	private CuentaNegocio cuentaNegocio;
	private TipoCuentaNegocio tipoCuentaNegocio;
	
    public ServletClienteUsuario() {
        super();
        this.clienteNegocio = new ClienteNegociolmpl();
        this.cuentaNegocio = new CuentaNegocioImpl();
        this.tipoCuentaNegocio = new TipoCuentaNegocioImpl();
    }
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Verifica que el usuario esté cargado
		Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
		if (usuarioLogueado == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		
		// Carga todos los datos del cliente
		cargarDatosCompletos(request, response, usuarioLogueado);
	}

	private void cargarDatosCompletos(HttpServletRequest request, HttpServletResponse response, Usuario usuarioLogueado) throws ServletException, IOException {
		try {
			// Obtiene el cliente asociado al usuario logueado
			Cliente cliente = clienteNegocio.BuscarPorID(usuarioLogueado.getId_cliente());
			request.setAttribute("cliente", cliente);
			
			// Obtiene las cuentas del cliente
			List<Cuenta> cuentas = cuentaNegocio.obtenerCuentasPorCliente(usuarioLogueado.getId_cliente());
			request.setAttribute("cuentas", cuentas);
			
			// Obtiene tipos de cuenta para mostrar nombres
			List<TipoCuenta> tiposCuenta = tipoCuentaNegocio.obtenerTiposCuenta();
			request.setAttribute("tiposCuenta", tiposCuenta);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("usuarioCliente.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error al cargar los datos: " + e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("usuarioCliente.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
} 