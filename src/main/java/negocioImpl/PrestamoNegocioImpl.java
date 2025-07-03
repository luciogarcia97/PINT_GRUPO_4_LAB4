package negocioImpl;


import java.math.BigDecimal;
import java.util.List;
import dao.PrestamoDao;
import daoImpl.PrestamoDaolmpl;
import entidades.Prestamo;
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



	
}
