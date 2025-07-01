package negocio;

import java.util.List;


import entidades.Prestamo;

public interface PrestamoNegocio {

	public boolean insertar(Prestamo prestamo);

	List<Prestamo> obtenerPrestamos();
}
