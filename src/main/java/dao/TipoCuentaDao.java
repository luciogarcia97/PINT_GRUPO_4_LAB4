package dao;

import java.util.List;

import entidades.TipoCuenta;

public interface TipoCuentaDao {

	public List<TipoCuenta> obtenerTiposCuenta();

	public TipoCuenta obtenerTipoPorId(int id);

}
