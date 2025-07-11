package dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface ReporteDao {
	
	public Double sumaDepositos(Date fechaInicio, Date fechaFin);
	
	public int transferenciasRealizadas(Date fechaInicio, Date fechaFin);
	
	public int prestamosOtorgados(Date fechaInicio, Date fechaFin);
	
	public int clientesActivos(Date fechaInicio, Date fechaFin);
	
	// Obtiene datos detalaldos de cualquier tipo de reporte
	public List<Map<String, Object>> obtenerDatosDetallados(String tipoReporte, Date fechaInicio, Date fechaFin);

}