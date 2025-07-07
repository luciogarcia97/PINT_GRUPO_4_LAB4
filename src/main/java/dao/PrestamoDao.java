package dao;


import java.util.List;


import entidades.Prestamo;
import entidades.PrestamoCuota;

public interface PrestamoDao {

	boolean insertar(Prestamo prestamo);

	List<Prestamo> obtenerPrestamos();

	boolean denegarPrestamo(int idPrestamo);

	boolean aceptarPrestamo(int idPrestamo);
	
	Prestamo obtenerPrestamoID(int idPrestamo);
	
	boolean impactar_prestamo_cuenta(int idCuenta, double dinero );
	
	List<PrestamoCuota> obtenerCuotas(int idPrestamo);
	
	Prestamo obtenerPrestamoIDCuenta(int idCliente);
	
	boolean pagarCuota(int idCuota,int idCuenta,double monto);	
	
	Prestamo obtenerPrestamoPorIdCuota(int idCuota);
	
	boolean generarCuotasPrestamo(int idPrestamo, int cantidadCuotas, double montoPorCuota);

}
