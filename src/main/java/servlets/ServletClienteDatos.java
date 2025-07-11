package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Cliente;
import entidades.Cuenta;
import entidades.Prestamo;
import entidades.PrestamoCuota;
import entidades.TipoCuenta;
import entidades.Usuario;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocio.PrestamoNegocio;
import negocio.TipoCuentaNegocio;
import negocio.MovimientoNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.ClienteNegociolmpl;
import negocioImpl.PrestamoNegocioImpl;
import negocioImpl.TipoCuentaNegocioImpl;
import negocioImpl.MovimientoNegocioImpl;


@WebServlet("/ServletClienteDatos")
public class ServletClienteDatos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteNegocio clienteNegocio;
    private CuentaNegocio cuentaNegocio;
    private PrestamoNegocio prestamoNegocio;
    private TipoCuentaNegocio tipoCuentaNegocio;
    private MovimientoNegocio movimientoNegocio;
    
    public ServletClienteDatos() {
        super(); 
        this.clienteNegocio = new ClienteNegociolmpl();
        this.cuentaNegocio = new CuentaNegocioImpl();
        this.prestamoNegocio = new PrestamoNegocioImpl();
        this.tipoCuentaNegocio = new TipoCuentaNegocioImpl();
        this.movimientoNegocio = new MovimientoNegocioImpl();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
		if (usuarioLogueado == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		request.getSession().setAttribute("usuarioLogueado", usuarioLogueado);	
		
		// Carga todos los datos del cliente
		cargarDatosCompletos(request, response, usuarioLogueado);
	}
	
	private void cargarDatosCompletos(HttpServletRequest request, HttpServletResponse response, Usuario usuarioLogueado) throws ServletException, IOException {
		try {
			// Obtiene el cliente asociado al usuario logueado
			Cliente cliente = clienteNegocio.BuscarPorID(usuarioLogueado.getId_cliente());
			request.setAttribute("cliente", cliente);
			
			// Obtiene cuotas del prestamo vigente
			List<PrestamoCuota> cuotas = new ArrayList<>();
			Prestamo prestamo = new Prestamo();
			if(prestamoNegocio.obtenerPrestamoIDCuenta(cliente.getIdCliente()) != null)
				{
					prestamo = prestamoNegocio.obtenerPrestamoIDCuenta(cliente.getIdCliente());
				}
			cuotas = prestamoNegocio.obtenerCuotas(prestamo.getId_prestamo());
			request.setAttribute("cuotas", cuotas);
			// Obtiene las cuentas del cliente
			List<Cuenta> cuentas = cuentaNegocio.obtenerCuentasPorCliente(usuarioLogueado.getId_cliente());
			request.setAttribute("cuentas", cuentas);
			
			// Obtiene tipos de cuenta para mostrar nombres
			List<TipoCuenta> tiposCuenta = tipoCuentaNegocio.obtenerTiposCuenta();
			request.setAttribute("tiposCuenta", tiposCuenta);
			
			for (PrestamoCuota pc : cuotas) {
			    System.out.println("Cuota: " + pc.getNumCuota() + ", Monto: " + pc.getMonto());
			}
			
			System.out.println("ID del cliente: " + cliente.getIdCliente());


			RequestDispatcher dispatcher = request.getRequestDispatcher("usuarioClienteDatos.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error al cargar los datos: " + e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("usuarioClienteDatos.jsp");
			dispatcher.forward(request, response);
		}
	}	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
