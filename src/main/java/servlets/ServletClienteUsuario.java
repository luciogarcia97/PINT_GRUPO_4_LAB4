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
import entidades.Movimiento;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocio.TipoCuentaNegocio;
import negocio.MovimientoNegocio;
import negocioImpl.ClienteNegociolmpl;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.TipoCuentaNegocioImpl;
import negocioImpl.MovimientoNegocioImpl;

@WebServlet("/ServletClienteUsuario")
public class ServletClienteUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ClienteNegocio clienteNegocio;
	private CuentaNegocio cuentaNegocio;
	private TipoCuentaNegocio tipoCuentaNegocio;
	private MovimientoNegocio movimientoNegocio;
	
    public ServletClienteUsuario() {
        super();
        this.clienteNegocio = new ClienteNegociolmpl();
        this.cuentaNegocio = new CuentaNegocioImpl();
        this.tipoCuentaNegocio = new TipoCuentaNegocioImpl();
        this.movimientoNegocio = new MovimientoNegocioImpl();
    }
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Verifica que el usuario este cargado
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
	    if (usuario == null || usuario.getEliminado() == 1) {
	        response.sendRedirect("index.jsp");
	        return;
	    }
	    
	    String accion = request.getParameter("accion");
	    
	    try {
	        
	        cargarDatosBasicos(request, usuario);
	        
	        if ("movimientos".equals(accion)) {
	            manejarConsultaMovimientos(request, usuario);
	        } else if ("transferencias".equals(accion)) {
	            // Aca cargamos los datos que sean necesarios en transferencias
	        } else if ("prestamos".equals(accion)) {
	            // Aca cargamos los datos que sean necesarios en prestamos
	        }

	        
	        RequestDispatcher rd = request.getRequestDispatcher("/usuarioCliente.jsp");
	        rd.forward(request, response);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("error", "Error del sistema: " + e.getMessage());
	        RequestDispatcher rd = request.getRequestDispatcher("/usuarioCliente.jsp");
	        rd.forward(request, response);
	    }
	}
	
	private void cargarDatosBasicos(HttpServletRequest request, Usuario usuario) {
		// Cargar cuentas
	    List<Cuenta> cuentasCliente = cuentaNegocio.obtenerCuentasPorCliente(usuario.getId_cliente());
	    request.setAttribute("cuentasCliente", cuentasCliente);
	    
	    // Cargar cliente
	    Cliente cliente = clienteNegocio.BuscarPorID(usuario.getId_cliente());
	    request.setAttribute("cliente", cliente);
	    
	    // Cargar tipos de cuenta para mostrar nombres
	    List<TipoCuenta> tiposCuenta = tipoCuentaNegocio.obtenerTiposCuenta();
	    request.setAttribute("tiposCuenta", tiposCuenta);
	    
	}
	
	private void manejarConsultaMovimientos(HttpServletRequest request, Usuario usuario) {
	    String idCuentaStr = request.getParameter("idCuenta");
	    if (idCuentaStr != null && !idCuentaStr.isEmpty()) {
	        try {
	            int idCuenta = Integer.parseInt(idCuentaStr);
	            
	            // Validar que la cuenta pertenece al usuario
	            Cuenta cuenta = cuentaNegocio.buscarPorID(idCuenta);
	            if (cuenta != null && cuenta.getIdCliente() == usuario.getId_cliente()) {
	                List<Movimiento> movimientos = movimientoNegocio.obtenerMovimientosPorCuenta(idCuenta);
	                request.setAttribute("movimientos", movimientos);
	                request.setAttribute("cuentaSeleccionada", cuenta);
	            } else {
	                request.setAttribute("error", "No tienes permisos para ver esta cuenta.");
	            }
	        } catch (NumberFormatException e) {
	            request.setAttribute("error", "ID de cuenta inválido.");
	        }
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
} 