package negocioImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.CuentaDao;
import daoImpl.CuentaDaoImpl;
import entidades.Cuenta;
import negocio.CuentaNegocio;

public class CuentaNegocioImpl implements CuentaNegocio {
	
	private CuentaDao cuentaDao;	
	

	public CuentaNegocioImpl() {
		super();
		this.cuentaDao = new CuentaDaoImpl();
	}

	public CuentaNegocioImpl(CuentaDao cuentaDao) {		
		this.cuentaDao = cuentaDao;
	}

	@Override
	public boolean insertarCuenta(Cuenta cuenta) {
		
		return cuentaDao.insertarCuenta(cuenta);		
	}

	@Override
	public List<Cuenta> obtenerCuentasPorCliente(int idCliente) {
		
		return cuentaDao.obtenerCuentasPorCliente(idCliente);
	}

	@Override
	public String generarNumeroCuenta() {
		
		return cuentaDao.generarNumeroCuenta();
	}

	@Override
	public boolean existeNumeroCuenta(String numeroCuenta) {
		
		return cuentaDao.existeNumeroCuenta(numeroCuenta);
	}

	@Override
	public String generarCBU(String numeroCuenta) {
		
		return cuentaDao.generarCBU(numeroCuenta);
	}

	@Override
	public Cuenta mapearCuenta(ResultSet rs) throws SQLException {
		
		return cuentaDao.mapearCuenta(rs);
	}

}
