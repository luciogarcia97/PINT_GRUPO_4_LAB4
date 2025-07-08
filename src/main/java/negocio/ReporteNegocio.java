package negocio;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface ReporteNegocio {

	public Double sumaDepositos(Date fechaInicio, Date fechaFin);
	
	public int transferenciasRealizadas(Date fechaInicio, Date fechaFin);
	
	public int prestamosOtorgados(Date fechaInicio, Date fechaFin);
	
	public int clientesActivos(Date fechaInicio, Date fechaFin);
	
	public List<Map<String, Object>> obtenerDatosDetallados(String tipoReporte, Date fechaInicio, Date fechaFin);
	
}