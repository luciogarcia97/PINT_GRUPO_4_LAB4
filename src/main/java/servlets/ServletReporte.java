package servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import negocio.ReporteNegocio;
import negocioImpl.ReporteNegocioImpl;


@WebServlet("/ServletReporte")
public class ServletReporte extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ReporteNegocio reporteNegocio;   
   
    public ServletReporte() {
        super();
        this.reporteNegocio = new ReporteNegocioImpl();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
			if(request.getParameter("btnReporte") != null) {
				
				Object resultadoGeneral = 0;				
				String tipoReporte = request.getParameter("txtReporte");
				
				//Obtiene fechas del formulario
				String fechaInicioStr = request.getParameter("fechaDesde");
				String fechaFinStr = request.getParameter("fechaHasta");
				
				//Las convierte de String a Date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date fechaInicio = null;
				Date fechaFin = null;

				try {
				    java.util.Date utilFechaInicio = sdf.parse(fechaInicioStr);
				    java.util.Date utilFechaFin = sdf.parse(fechaFinStr);

				    fechaInicio = new java.sql.Date(utilFechaInicio.getTime());
				    fechaFin = new java.sql.Date(utilFechaFin.getTime());
				} catch (ParseException e) {
				    e.printStackTrace();
				}				
				
				// Obtener el tipo de reporte y las fechas para el resultado
				switch (tipoReporte) {					
					case "depositos":						
						 resultadoGeneral = reporteNegocio.sumaDepositos(fechaInicio, fechaFin);
						 break;
					case "transferencias":
						 resultadoGeneral = reporteNegocio.transferenciasRealizadas(fechaInicio, fechaFin);
						 break;
					case "prestamos":
						resultadoGeneral = reporteNegocio.prestamosOtorgados(fechaInicio, fechaFin);
						break;
					case "clientes":
						resultadoGeneral = reporteNegocio.clientesActivos(fechaInicio, fechaFin);
						break;
					default:	
						break;				
				}
				
				// Obtener los datos detallados para la tabla
				List<Map<String, Object>> datosDetallados = reporteNegocio.obtenerDatosDetallados(tipoReporte, fechaInicio, fechaFin);
					
			    request.setAttribute("resultado", resultadoGeneral);
			    request.setAttribute("datosDetallados", datosDetallados);
			    request.setAttribute("tipoReporte", tipoReporte);
			    request.setAttribute("fechaInicio", fechaInicioStr);
			    request.setAttribute("fechaFin", fechaFinStr);
			    
			    RequestDispatcher rd = request.getRequestDispatcher("/reportesInformes.jsp");
			    rd.forward(request, response);			
			}		
	}

}