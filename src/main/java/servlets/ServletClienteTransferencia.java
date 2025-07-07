package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CuentaDao;
import dao.TipoCuentaDao;
import daoImpl.CuentaDaoImpl;
import daoImpl.TipoCuentaDaoImpl;
import entidades.Cliente;
import entidades.Cuenta;
import entidades.TipoCuenta;
import entidades.Usuario;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocio.TipoCuentaNegocio;
import negocio.UsuarioNegocio;
import negocio.MovimientoNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.TipoCuentaNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;
import negocioImpl.ClienteNegociolmpl;
import negocioImpl.MovimientoNegocioImpl;


@WebServlet("/ServletClienteTransferencia")
public class ServletClienteTransferencia extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ClienteNegocio clienteNegocio;
	private CuentaNegocio cuentaNegocio;
	private TipoCuentaNegocio tipoCuentaNegocio;
	private UsuarioNegocio usuarioNegocio;
	private MovimientoNegocio movimientoNegocio;
	
    public ServletClienteTransferencia() {
        super();
        this.clienteNegocio = new ClienteNegociolmpl();
        this.cuentaNegocio = new CuentaNegocioImpl();
        this.tipoCuentaNegocio = new TipoCuentaNegocioImpl();
        this.usuarioNegocio = new UsuarioNegocioImpl();
        this.movimientoNegocio = new MovimientoNegocioImpl();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
	    if (usuarioLogueado == null) {
	        response.sendRedirect("index.jsp");
	        return;
	    }
		
	    Cliente cliente = clienteNegocio.BuscarPorID(usuarioLogueado.getId_cliente());
	    request.setAttribute("cliente", cliente);
	    
	    List<Cuenta> cuentasCliente = cuentaNegocio.obtenerCuentasPorCliente(cliente.getIdCliente());
	    request.setAttribute("cuentasCliente", cuentasCliente);
	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher("usuarioClienteTransferencias.jsp");
	    dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("btnTransferencia") != null)
    	{
    		
    		int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));       	     	
    		String cbu = request.getParameter("txtCbu").toString();       	
    		BigDecimal monto = new BigDecimal(request.getParameter("txtMonto"));
    		
    		
    		//Valida si la cuenta tiene el dinero suficiente:
    		if(cuentaNegocio.tieneSaldoSuficiente(idCuenta, monto) == false)
    		{
    			response.sendRedirect("ServletClienteUsuario?saldoInsuficiente=1");
    		    return;
    		}
    		
    		//Valida si el CBU existe:
    		if(cuentaNegocio.existeCBU(cbu) == false)
    		{
    			response.sendRedirect("ServletClienteUsuario?cbuInexistente=1");
    			return;
    		}
    		
    		//Resta el monto al saldo de la cuenta origen y lo suma en la cuenta destino a la que le pertenezca ese CBU:
    		Cuenta cuentaOrigen = cuentaNegocio.buscarPorID(idCuenta);
    		Cuenta cuentaDestino = cuentaNegocio.buscarIdConCbu(cbu);
    		
    		Boolean debitoResultado = false;
    		BigDecimal saldoFinal = cuentaOrigen.getSaldo().subtract(monto);
    		debitoResultado = cuentaNegocio.modificarSaldo(cuentaOrigen.getIdCuenta(), saldoFinal);
    		
    		Boolean acreditacionResultado = false;
    		saldoFinal = cuentaDestino.getSaldo().add(monto);
    		acreditacionResultado = cuentaNegocio.modificarSaldo(cuentaDestino.getIdCuenta(), saldoFinal);
    		
    		// Si se registro todo correctamente, registro el movimiento
    		if(acreditacionResultado && debitoResultado) {
    			String detalle = "CBU: " + cuentaDestino.getCbu();
    		    boolean movimientoRegistrado = movimientoNegocio.registrarMovimientoTransferencia(cuentaOrigen.getIdCuenta(), cuentaDestino.getIdCuenta(), monto, detalle);
    		
    		    if (!movimientoRegistrado) {
    		        System.out.println("Advertencia: No se pudieron registrar los movimientos de transferencia");
    		    }
    		}
    		
    		// Recargar datos y mostrar la página de transferencias
    		Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
    		Cliente cliente = clienteNegocio.BuscarPorID(usuarioLogueado.getId_cliente());
    		request.setAttribute("cliente", cliente);

    		// Cargar las cuentas del cliente para el desplegable
    		List<Cuenta> cuentasCliente = cuentaNegocio.obtenerCuentasPorCliente(cliente.getIdCliente());
    		request.setAttribute("cuentasCliente", cuentasCliente);

    		RequestDispatcher dispatcher = request.getRequestDispatcher("usuarioClienteTransferencias.jsp");
    		dispatcher.forward(request, response);
	        
    	}
		
	}

}
