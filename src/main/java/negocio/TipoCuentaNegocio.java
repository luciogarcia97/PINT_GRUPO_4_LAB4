package negocio;

import java.util.List;

import entidades.TipoCuenta;

public interface TipoCuentaNegocio {
	
	public List<TipoCuenta> obtenerTiposCuenta();

	public TipoCuenta obtenerTipoPorId(int id);

}
