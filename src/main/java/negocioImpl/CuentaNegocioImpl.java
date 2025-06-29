package negocioImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.CuentaDao;
import daoImpl.CuentaDaoImpl;
import entidades.Cuenta;
import negocio.CuentaNegocio;

import dao.ClienteDao;
import daoImpl.ClienteDaolmpl;

public class CuentaNegocioImpl implements CuentaNegocio {
	
	private CuentaDao cuentaDao;	
	private ClienteDao clienteDao;

	public CuentaNegocioImpl() {
		super();
		this.cuentaDao = new CuentaDaoImpl();
		this.clienteDao = new ClienteDaolmpl();
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
	
	@Override
	public boolean eliminarCuenta(int idCuenta) {
		return cuentaDao.cambiarEstadoCuenta(idCuenta, false);
	}
	
	@Override
	public List<Cuenta> obtenerCuentas(){
		
		return cuentaDao.obtenerCuentas();
		
	}
	
	@Override
	public boolean puedeCrearCuenta(int idCliente, int idCuentaExcluir) {
		int cantidadCuentas = cuentaDao.contarCuentasActivasPorClienteExcepto(idCliente, idCuentaExcluir);
		return cantidadCuentas < 3;
	}
	
//	@Override
//	public boolean existeCliente(int idCliente) {
//	    return clienteDao.existeCliente(idCliente);
//	}
	
	@Override
	public boolean reactivarCuenta(int idCuenta) {
	    return cuentaDao.cambiarEstadoCuenta(idCuenta, true);
	}
	
	@Override
	public Cuenta obtenerCuentaPorId(int idCuenta) {
	    return cuentaDao.obtenerCuentaPorId(idCuenta);
	}
	
	@Override
	public boolean eliminarCuentasUsuario(int idCliente)
	{
		
		return cuentaDao.eliminarCuentasUsuario(idCliente);
		
	}

	@Override
	public Cuenta buscarPorID(int idCuenta) {
		return cuentaDao.buscarPorID(idCuenta);
	}

	@Override
	public boolean modificarCuenta(Cuenta cuenta) {
		return cuentaDao.modificarCuenta(cuenta);
	}

	@Override
	public boolean existeCBU(String cbu) {
		return cuentaDao.existeCBU(cbu);
	}

	@Override
	public boolean puedeCrearCuenta(int idCliente) {
		// TODO Auto-generated method stub
		return false;
	}

}
