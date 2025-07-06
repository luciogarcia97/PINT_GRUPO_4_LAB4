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


import entidades.Prestamo;

import entidades.Usuario;

import negocioImpl.PrestamoNegocioImpl;

@WebServlet("/ServletPrestamo")
public class ServletPrestamo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrestamoNegocioImpl prestamoNegocio;

	
	
	
	public ServletPrestamo() {
        super();
         this.prestamoNegocio = new PrestamoNegocioImpl();
        
    }
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
						
		
		if (request.getParameter("listar") != null) {
	        List<Prestamo> listaPrestamos = prestamoNegocio.obtenerPrestamos();
	        
	        request.setAttribute("listaPrestamos", listaPrestamos);
	        
	        RequestDispatcher rd = request.getRequestDispatcher("/administrarPrestamos.jsp");
	        rd.forward(request, response);
	    }		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		if (request.getParameter("btnSolicitarPrestamo") != null) {
	        try {
	            Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
	            if (usuario == null) {
	                response.sendRedirect("index.jsp");
	                return;
	            }

	            int idCliente = usuario.getId_cliente();
	            int idCuenta = Integer.parseInt(request.getParameter("cuentaPrestamo"));
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

	            RequestDispatcher rd = request.getRequestDispatcher("usuarioCliente.jsp");
	            rd.forward(request, response);

	        } catch (Exception e) {
	            e.printStackTrace();
	            request.setAttribute("error", "Ocurrió un error interno.");
	            RequestDispatcher rd = request.getRequestDispatcher("usuarioCliente.jsp");
	            rd.forward(request, response);
	        }
	    }
		
		if(request.getParameter("btnDenegarPrestamo")!= null) {
			
			int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
					
			
			if (prestamoNegocio.denegarPrestamo(idPrestamo)) {			 
							
				 List<Prestamo> listaPrestamos = prestamoNegocio.obtenerPrestamos();
			        
			        request.setAttribute("listaPrestamos", listaPrestamos);
			        
			        RequestDispatcher rd = request.getRequestDispatcher("/administrarPrestamos.jsp");
			        rd.forward(request, response);
			 } else {
					
					request.setAttribute("error", "No se pudo denegar el prestamo.");
                    List<Prestamo> listaPrestamos = prestamoNegocio.obtenerPrestamos();
			        request.setAttribute("listaPrestamos", listaPrestamos);
			        
			        RequestDispatcher rd = request.getRequestDispatcher("/administrarPrestamos.jsp");
			        rd.forward(request, response);
				}	
		}
		
		if(request.getParameter("btnAceptarPrestamo")!= null) {
			int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
			boolean resultado = false;
			Prestamo prestamo = new Prestamo();
			if (prestamoNegocio.aceptarPrestamo(idPrestamo)) {			 
				List<Prestamo> listaPrestamos = prestamoNegocio.obtenerPrestamos();
				request.setAttribute("listaPrestamos", listaPrestamos);
				prestamo=prestamoNegocio.obtenerPrestamoID(idPrestamo);
				resultado = prestamoNegocio.impactar_prestamo_cuenta(prestamo.getId_cuenta(), prestamo.getImporte_solicitado());
				RequestDispatcher rd = request.getRequestDispatcher("/administrarPrestamos.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("error", "No se pudo aceptar el prestamo.");
				List<Prestamo> listaPrestamos = prestamoNegocio.obtenerPrestamos();
				request.setAttribute("listaPrestamos", listaPrestamos);
				RequestDispatcher rd = request.getRequestDispatcher("/administrarPrestamos.jsp");
				rd.forward(request, response);
			}	
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
				RequestDispatcher rd = request.getRequestDispatcher("/usuarioCliente.jsp");
				rd.forward(request, response);
		     
		     
		       
		}
	}
}
