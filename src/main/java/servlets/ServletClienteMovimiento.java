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
import entidades.Cuenta;
import entidades.Movimiento;
import entidades.Usuario;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocio.MovimientoNegocio;
import negocioImpl.ClienteNegociolmpl;
import negocioImpl.CuentaNegocioImpl;

import negocioImpl.MovimientoNegocioImpl;


@WebServlet("/ServletClienteMovimiento")
public class ServletClienteMovimiento extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MovimientoNegocio movimientoNegocio;
    private CuentaNegocio cuentaNegocio;
    private ClienteNegocio clienteNegocio;
    
    public ServletClienteMovimiento() {
        super();
        this.movimientoNegocio = new MovimientoNegocioImpl();
        this.cuentaNegocio = new CuentaNegocioImpl();
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
				
		List<Cuenta> cuentas = cuentaNegocio.obtenerCuentasPorCliente(usuarioLogueado.getId_cliente());
	    request.setAttribute("cuentas", cuentas);
	    	  	    
        
        if (request.getParameter("listar")!= null) {     	
                       
            try {
                int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));               
                
                List<Movimiento> listaMovimientos = movimientoNegocio.obtenerMovimientosPorCuenta(idCuenta);
                                              
                request.setAttribute("listaMovimientos", listaMovimientos);
                request.setAttribute("cuentas", cuentas);
                request.setAttribute("cuentaSeleccionada", idCuenta);
                request.setAttribute("mostrarSeccionMovimientos", true); // Esto lo declaro para que el jso sepa que viene de movimientos.
                
                
                RequestDispatcher rd = request.getRequestDispatcher("/usuarioClienteMovimientos.jsp");
                rd.forward(request, response);
                return;
                
            } catch (NumberFormatException e) {           	
            	request.setAttribute("mensaje", "¡Seleccione una cuenta para ver sus movimientos!");
                RequestDispatcher rd = request.getRequestDispatcher("/usuarioClienteMovimientos.jsp");
                rd.forward(request, response);
                return;
            }              
        
        }        	
        	
        RequestDispatcher rd = request.getRequestDispatcher("/usuarioClienteMovimientos.jsp");
        rd.forward(request, response);              
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Validar usuario logueado
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        if (usuario == null) {
            response.sendRedirect("index.jsp");
            return;
        }      
		request.getSession().setAttribute("usuarioLogueado", usuario);
        
        if (request.getParameter("btnFiltrarFechas") != null) {
            filtrarPorFechas(request, response, usuario);
        }
        
        if (request.getParameter("btnFiltrarTipo") != null) {
            filtrarPorTipo(request, response, usuario);
        }
    }
    
    private void filtrarPorFechas(HttpServletRequest request, HttpServletResponse response, Usuario usuario) 
            throws ServletException, IOException {
        
        try {
            int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
            String fechaDesdeStr = request.getParameter("fechaDesde");
            String fechaHastaStr = request.getParameter("fechaHasta");
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaDesde = new Date(sdf.parse(fechaDesdeStr).getTime());
            Date fechaHasta = new Date(sdf.parse(fechaHastaStr).getTime());
            
            List<Movimiento> listaMovimientos = movimientoNegocio.obtenerMovimientosPorCuentaYFechas(
                idCuenta, fechaDesde.toLocalDate(), fechaHasta.toLocalDate());
            
            List<Cuenta> cuentas = cuentaNegocio.obtenerCuentasPorCliente(usuario.getId_cliente());
            
            request.setAttribute("listaMovimientos", listaMovimientos);
            request.setAttribute("cuentas", cuentas);
            request.setAttribute("cuentaSeleccionada", idCuenta);
            request.setAttribute("fechaDesde", fechaDesdeStr);
            request.setAttribute("fechaHasta", fechaHastaStr);
            
            RequestDispatcher rd = request.getRequestDispatcher("/usuarioClienteMovimientos.jsp");
            rd.forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al filtrar por fechas: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/usuarioClienteMovimientos.jsp");
            rd.forward(request, response);
        }
    }
    
    private void filtrarPorTipo(HttpServletRequest request, HttpServletResponse response, Usuario usuario) 
            throws ServletException, IOException {
        
        try {
            int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
            int idTipoMovimiento = Integer.parseInt(request.getParameter("idTipoMovimiento"));
            
            List<Movimiento> listaMovimientos = movimientoNegocio.obtenerMovimientosPorCuentaYTipo(
                idCuenta, idTipoMovimiento);
            
            List<Cuenta> cuentas = cuentaNegocio.obtenerCuentasPorCliente(usuario.getId_cliente());
            
            request.setAttribute("listaMovimientos", listaMovimientos);
            request.setAttribute("cuentas", cuentas);
            request.setAttribute("cuentaSeleccionada", idCuenta);
            request.setAttribute("tipoSeleccionado", idTipoMovimiento);
            
            RequestDispatcher rd = request.getRequestDispatcher("/usuarioClienteMovimientos.jsp");
            rd.forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al filtrar por tipo: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/usuarioClienteMovimientos.jsp");
            rd.forward(request, response);
        }
    }
}