package negocio;

import java.sql.Date;

public interface ReporteNegocio {

	public Double sumaDepositos(Date fechaInicio, Date fechaFin);
	
	public int transferenciasRealizadas(Date fechaInicio, Date fechaFin);
	
	public int prestamosOtorgados(Date fechaInicio, Date fechaFin);
	
	public int clientesActivos(Date fechaInicio, Date fechaFin);
	
}
