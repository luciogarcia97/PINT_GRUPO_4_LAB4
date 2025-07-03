package negocio;

import java.math.BigDecimal;
import java.util.List;


import entidades.Prestamo;

public interface PrestamoNegocio {

	public boolean insertar(Prestamo prestamo);

	List<Prestamo> obtenerPrestamos();
	
	boolean denegarPrestamo(int idPrestamo);
	
	Prestamo obtenerPrestamoID(int idPrestamo);
	
	boolean impactar_prestamo_cuenta(int idCuenta, double dinero );

	
	
}
