package servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
				
				String fechaInicioStr = request.getParameter("fechaDesde");
				String fechaFinStr = request.getParameter("fechaHasta");
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date fechaInicio = null;
				Date fechaFin = null;

				try {
				    fechaInicio = (Date) sdf.parse(fechaInicioStr);
				    fechaFin = (Date) sdf.parse(fechaFinStr);
				} catch (ParseException e) {
				    e.printStackTrace();
				}			
				
				Date sqlFechaInicio = new Date(fechaInicio.getTime());
			    Date sqlFechaFin = new Date(fechaFin.getTime());
				
				switch (tipoReporte) {					
					case "depositos":						
						 resultadoGeneral = reporteNegocio.sumaDepositos(sqlFechaInicio, sqlFechaFin);
						 break;
					case "transferencias":
						 resultadoGeneral = reporteNegocio.transferenciasRealizadas(sqlFechaInicio, sqlFechaFin);
						 break;
					case "prestamos":
						resultadoGeneral = reporteNegocio.prestamosOtorgados(sqlFechaInicio, sqlFechaFin);
						break;
					case "clientes":
						resultadoGeneral = reporteNegocio.clientesActivos(sqlFechaInicio, sqlFechaFin);
						break;
					default:	
						break;				
				}				
					
			    request.setAttribute("resultado", resultadoGeneral);		
			    RequestDispatcher rd = request.getRequestDispatcher("/reportesInformes.jsp");
			    rd.forward(request, response);			
			}		
	}

}
