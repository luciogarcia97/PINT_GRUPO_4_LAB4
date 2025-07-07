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
import negocio.PrestamoNegocio;
import negocioImpl.PrestamoNegocioImpl;

@WebServlet("/ServletPrestamo")
public class ServletPrestamo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrestamoNegocio prestamoNegocio;
	
	
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
		
	}
}
