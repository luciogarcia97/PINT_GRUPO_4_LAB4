package negocioImpl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import dao.ReporteDao;
import daoImpl.ReporteDaoImpl;
import negocio.ReporteNegocio;

public class ReporteNegocioImpl implements ReporteNegocio {

	private ReporteDao reporteDao;	
	
	public ReporteNegocioImpl() {
		super();
		this.reporteDao = new ReporteDaoImpl();
	}

	@Override
	public Double sumaDepositos(Date fechaInicio, Date fechaFin) {
		
		return  reporteDao.sumaDepositos(fechaInicio, fechaFin);
	}

	@Override
	public int transferenciasRealizadas(Date fechaInicio, Date fechaFin) {
				
		return reporteDao.transferenciasRealizadas(fechaInicio, fechaFin);
	}

	@Override
	public int prestamosOtorgados(Date fechaInicio, Date fechaFin) {
		
		return reporteDao.prestamosOtorgados(fechaInicio, fechaFin);
	}

	@Override
	public int clientesActivos(Date fechaInicio, Date fechaFin) {
		
		return reporteDao.clientesActivos(fechaInicio, fechaFin);
	}

	@Override
	public List<Map<String, Object>> obtenerDatosDetallados(String tipoReporte, Date fechaInicio, Date fechaFin) {
		
		return reporteDao.obtenerDatosDetallados(tipoReporte, fechaInicio, fechaFin);
	}

}