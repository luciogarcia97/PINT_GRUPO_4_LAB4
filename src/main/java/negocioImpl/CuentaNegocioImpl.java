package negocioImpl;

import dao.ClienteDao;
import dao.CuentaDao;
import daoImpl.ClienteDaolmpl;
import daoImpl.CuentaDaoImpl;
import entidades.Cuenta;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import negocio.CuentaNegocio;

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
		int cantidadCuentas = cuentaDao.contarCuentasActivasPorCliente(idCliente);
		if (cantidadCuentas < 3) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean tieneSaldoSuficiente(int idCuenta, BigDecimal monto)
	{
		return cuentaDao.tieneSaldoSuficiente(idCuenta, monto);
	}
	
	@Override
	public boolean modificarSaldo(int idCuenta, BigDecimal saldoFinal)
	{
		return cuentaDao.modificarSaldo(idCuenta, saldoFinal);
	}
	
	@Override
	public Cuenta buscarIdConCbu(String cbu) 
	{
		return cuentaDao.buscarIdConCbu(cbu);
	}
	
	@Override
	public int obtenerUltimaIdCuenta() {
	    return cuentaDao.obtenerUltimaIdCuenta();
	}
	
}
