package negocioImpl;

import java.util.List;

import dao.TipoCuentaDao;
import daoImpl.TipoCuentaDaoImpl;
import entidades.TipoCuenta;
import negocio.TipoCuentaNegocio;

public class TipoCuentaNegocioImpl implements TipoCuentaNegocio {

	private TipoCuentaDao tipoCuentaDao;	
	
	
	public TipoCuentaNegocioImpl() {
		super();
		this.tipoCuentaDao = new TipoCuentaDaoImpl();
	}

	public TipoCuentaNegocioImpl(TipoCuentaDao tipoCuentaDao) {
		super();
		this.tipoCuentaDao = tipoCuentaDao;
	}

	@Override
	public List<TipoCuenta> obtenerTiposCuenta() {
		
		return tipoCuentaDao.obtenerTiposCuenta();
	}

	@Override
	public TipoCuenta obtenerTipoPorId(int id) {
		
		return tipoCuentaDao.obtenerTipoPorId(id);
	}

}
