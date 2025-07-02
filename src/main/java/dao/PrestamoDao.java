package dao;

import java.util.List;


import entidades.Prestamo;

public interface PrestamoDao {

	boolean insertar(Prestamo prestamo);

	List<Prestamo> obtenerPrestamos();


	boolean denegarPrestamo(int idPrestamo);

	boolean aceptarPrestamo(int idPrestamo);





}
