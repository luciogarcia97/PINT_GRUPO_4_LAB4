package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Cliente;
import entidades.Cuenta;
import entidades.Prestamo;
import entidades.Usuario;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocio.PrestamoNegocio;
import negocioImpl.ClienteNegociolmpl;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.PrestamoNegocioImpl;


@WebServlet("/ServletClientePrestamo")
public class ServletClientePrestamo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ClienteNegocio clienteNegocio;
    private CuentaNegocio cuentaNegocio;
    private PrestamoNegocio prestamoNegocio;
    
    public ServletClientePrestamo() {
        super();
        this.clienteNegocio = new ClienteNegociolmpl();
        this.cuentaNegocio = new CuentaNegocioImpl();
        this.prestamoNegocio = new PrestamoNegocioImpl();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
		if(usuarioLogueado != null) {
    		Cliente cliente = clienteNegocio.BuscarPorID(usuarioLogueado.getId_cliente());
			request.setAttribute("cliente", cliente);
    	}
		
		 if (usuarioLogueado == null) {
             response.sendRedirect("index.jsp");
             return;
         }		
		
		List<Cuenta> cuentas = cuentaNegocio.obtenerCuentasPorCliente(usuarioLogueado.getId_cliente());
	    request.setAttribute("cuentas", cuentas);
	    
	    RequestDispatcher rd = request.getRequestDispatcher("/usuarioClientePrestamos.jsp");
		rd.forward(request, response);	
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		   Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuarioLogueado");
		    if (usuarioLogueado == null) {
		        response.sendRedirect("index.jsp");
		        return;
		    }
		    
		    Cliente cliente = clienteNegocio.BuscarPorID(usuarioLogueado.getId_cliente());
		    request.setAttribute("cliente", cliente);

		    List<Cuenta> cuentas = cuentaNegocio.obtenerCuentasPorCliente(usuarioLogueado.getId_cliente());
		    request.setAttribute("cuentas", cuentas);
		
		
		 if (request.getParameter("btnSolicitarPrestamo") != null) {
		       
			 try {    		        	

		            int idCliente = usuarioLogueado.getId_cliente();
		            int idCuenta = Integer.parseInt(request.getParameter("cuentaDestino"));
		            double monto = Double.parseDouble(request.getParameter("montoPrestamo"));
		            int cuotas = Integer.parseInt(request.getParameter("cuotasPrestamo"));

		            Prestamo prestamo = new Prestamo();
		            prestamo.setId_cliente(idCliente);
		            prestamo.setId_cuenta(idCuenta);
		            prestamo.setFecha_solicitud(LocalDate.now());
		            prestamo.setImporte_solicitado(monto);
		            prestamo.setPlazo_pago_mes(cuotas);
		            prestamo.setImporte_por_cuota(monto / cuotas);
		            prestamo.setCantidad_cuotas(cuotas);
		            prestamo.setEstado("Pendiente");
		            prestamo.setEliminado(false);

		            
		            boolean resultado = prestamoNegocio.insertar(prestamo);

		            if (resultado) {		            	
		                request.setAttribute("mensaje", "Préstamo solicitado correctamente.");		               
		            } else {
		            	
		                request.setAttribute("error", "Error al solicitar el préstamo.");
		            }	           

		        } catch (Exception e) {
		            e.printStackTrace();
		            request.setAttribute("error", "Ocurrió un error interno.");		           
		        }
			 
			 
			 
			 RequestDispatcher rd = request.getRequestDispatcher("usuarioClientePrestamos.jsp");
		     rd.forward(request, response);
		     return;			 
		    }		
			
			if(request.getParameter("btnPagarCuota") != null) {
				boolean resultado = false;
				 int idCuenta = Integer.parseInt(request.getParameter("cuentaPago"));
				  String datosCuota = request.getParameter("cuotaSeleccion");
			        String[] datosCuotaArray = datosCuota.split("\\|");
			        int idCuota = Integer.parseInt(datosCuotaArray[0]);
			        double monto = Double.parseDouble(datosCuotaArray[1]);
			     
			     resultado = prestamoNegocio.pagarCuota(idCuota, idCuenta, monto);
			     
			     request.setAttribute("resultado", resultado);
				 RequestDispatcher rd = request.getRequestDispatcher("/usuarioClientePrestamos.jsp");
				 rd.forward(request, response);	
				 return;
				 
			}	
		
	}

}
