package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import entidades.Prestamo;

import entidades.Usuario;
import negocio.MovimientoNegocio;
import negocio.PrestamoNegocio;
import negocioImpl.MovimientoNegocioImpl;

import negocioImpl.PrestamoNegocioImpl;

@WebServlet("/ServletPrestamo")
public class ServletPrestamo extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private PrestamoNegocio prestamoNegocio;
	private MovimientoNegocio movimientoNegocio;
	
	public ServletPrestamo() {
        super();
         this.prestamoNegocio = new PrestamoNegocioImpl();
         this.movimientoNegocio = new MovimientoNegocioImpl();
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
		
		if(request.getParameter("btnDenegarPrestamo")!= null) {
			
			int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
					
			
			if (prestamoNegocio.denegarPrestamo(idPrestamo)) {			 
							
				 List<Prestamo> listaPrestamos = prestamoNegocio.obtenerPrestamos();
			        
			        request.setAttribute("listaPrestamos", listaPrestamos);
			        
			        request.setAttribute("denegado", "¡Préstamo denegado!");
			        RequestDispatcher rd = request.getRequestDispatcher("/administrarPrestamos.jsp");
			        rd.forward(request, response);
			 } else {
					
					request.setAttribute("error", "No se pudo denegar el préstamo.");
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
				prestamo=prestamoNegocio.obtenerPrestamoID(idPrestamo);
				resultado = prestamoNegocio.impactar_prestamo_cuenta(prestamo.getId_cuenta(), prestamo.getImporte_solicitado());
				
				// Registramos las cuotas a pagar
				
				boolean cuotasGeneradas = prestamoNegocio.generarCuotasPrestamo(prestamo.getId_prestamo(), prestamo.getCantidad_cuotas(), prestamo.getImporte_por_cuota());
				
				if (!cuotasGeneradas) {
			        System.out.println("Advertencia: No se pudieron generar las cuotas del préstamo");
			    }
				
				// Si se registro la aprobacion del prestamo se registra el movimiento de saldo al cliente
				if (resultado) {
					boolean movimientoRegistrado = movimientoNegocio.registrarMovimientoAltaPrestamo(prestamo.getId_cuenta(), BigDecimal.valueOf(prestamo.getImporte_solicitado()), prestamo.getId_prestamo());
			            
		            if (!movimientoRegistrado) {
		                System.out.println("Advertencia: No se pudo registrar el movimiento del préstamo");
		            }
				}
				
				List<Prestamo> listaPrestamos = prestamoNegocio.obtenerPrestamos();
				request.setAttribute("listaPrestamos", listaPrestamos);
				
				request.setAttribute("exito", "¡Préstamo otorgado!");
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
		
	}
}
