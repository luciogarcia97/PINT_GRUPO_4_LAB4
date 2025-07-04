package servlets;

import java.io.IOException;
import java.math.BigDecimal;

import java.io.IOException;
import java.math.BigDecimal;
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
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocio.TipoCuentaNegocio;
import negocio.UsuarioNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.TipoCuentaNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;
import negocioImpl.ClienteNegociolmpl;

import entidades.Cuenta;
import negocio.CuentaNegocio;

/**
 * Servlet implementation class ServletTransferencia
 */
@WebServlet("/ServletTransferencia")
public class ServletTransferencia extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ClienteNegocio clienteNegocio;
	private CuentaNegocio cuentaNegocio;
	private TipoCuentaNegocio tipoCuentaNegocio;
	private UsuarioNegocio usuarioNegocio;
	
    public ServletTransferencia() {
        super();
        this.clienteNegocio = new ClienteNegociolmpl();
        this.cuentaNegocio = new CuentaNegocioImpl();
        this.tipoCuentaNegocio = new TipoCuentaNegocioImpl();
        this.usuarioNegocio = new UsuarioNegocioImpl();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
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
    		
    		BigDecimal saldoFinal = cuentaOrigen.getSaldo().subtract(monto);
    		cuentaNegocio.modificarSaldo(cuentaOrigen.getIdCuenta(), saldoFinal);
    		
    		saldoFinal = cuentaDestino.getSaldo().add(monto);
    		cuentaNegocio.modificarSaldo(cuentaDestino.getIdCuenta(), saldoFinal);
    		
    		// Va directo al servlet para volver a cargar al usuario
    		response.sendRedirect("ServletClienteUsuario?saldoInsuficiente=1");
	        
    	}
		
	}

}
