package negocioImpl;


import java.math.BigDecimal;
import java.util.List;
import dao.PrestamoDao;
import daoImpl.PrestamoDaolmpl;
import entidades.Prestamo;
import entidades.PrestamoCuota;
import negocio.PrestamoNegocio;

public class PrestamoNegocioImpl implements PrestamoNegocio {

	
	
	 private PrestamoDao prestamoDao;

	 
	 public PrestamoNegocioImpl() {
			super();
			this.prestamoDao =  new PrestamoDaolmpl();
		}
	 


		@Override
		public boolean insertar(Prestamo prestamo) {
			return prestamoDao.insertar(prestamo);
			
		}
		
		@Override
		public List<Prestamo> obtenerPrestamos(){
			return prestamoDao.obtenerPrestamos();
		}

		@Override 
		public boolean denegarPrestamo(int idPrestamo) {
			
			return prestamoDao.denegarPrestamo(idPrestamo);
		}


		@Override
		public boolean aceptarPrestamo(int idPrestamo) {
			return prestamoDao.aceptarPrestamo(idPrestamo);
		
		}



		@Override
		public Prestamo obtenerPrestamoID(int idPrestamo) {
			
			return prestamoDao.obtenerPrestamoID(idPrestamo);
		}



		@Override
		public boolean impactar_prestamo_cuenta(int idCuenta, double dinero) {
			
			return prestamoDao.impactar_prestamo_cuenta(idCuenta, dinero);
		}



		@Override
		public List<PrestamoCuota> obtenerCuotas(int idPrestamo) {
			
			return prestamoDao.obtenerCuotas(idPrestamo);
		}



		@Override
		public Prestamo obtenerPrestamoIDCuenta(int idCliente) {
		
			return prestamoDao.obtenerPrestamoIDCuenta(idCliente);
		}




		@Override
		public boolean pagarCuota(int idCuota, int idCuenta, double monto) {
			
			return prestamoDao.pagarCuota(idCuota, idCuenta, monto);
		}

		@Override
		public Prestamo obtenerPrestamoPorIdCuota(int idCuota) {
			return prestamoDao.obtenerPrestamoPorIdCuota(idCuota);
		}
		
		@Override
		public boolean generarCuotasPrestamo(int idPrestamo, int cantidadCuotas, double montoPorCuota) {
		    return prestamoDao.generarCuotasPrestamo(idPrestamo, cantidadCuotas, montoPorCuota);
		}
}
