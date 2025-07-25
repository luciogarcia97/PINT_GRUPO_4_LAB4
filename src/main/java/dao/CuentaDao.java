package dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import entidades.Cuenta;

public interface CuentaDao {
	
	    
	    public boolean insertarCuenta(Cuenta cuenta); 
	    
	    public List<Cuenta> obtenerCuentasPorCliente(int idCliente);
	    
	    public String generarNumeroCuenta();	
	    	    
	    public boolean existeNumeroCuenta(String numeroCuenta);
	    
	    public String generarCBU(String numeroCuenta);       
	  	    
	    public Cuenta mapearCuenta(ResultSet rs) throws SQLException;
	    
	    public boolean eliminarCuenta(int idCuenta);
	    
	    public List<Cuenta> obtenerCuentas();
	    	    
	    public int contarCuentasActivasPorCliente(int idCliente);
	    
	    public boolean cambiarEstadoCuenta(int idCuenta, boolean activa);
	    
	    public Cuenta obtenerCuentaPorId(int idCuenta);
	    
	    public boolean eliminarCuentasUsuario(int idCliente);

	    public Cuenta buscarPorID(int idCuenta);
	    
	    public boolean modificarCuenta(Cuenta cuenta);

	    public boolean existeCBU(String cbu);

	    public int contarCuentasActivasPorClienteExcepto(int idCliente, int idCuentaExcluir);
	    
	    public boolean tieneSaldoSuficiente(int idCuenta, BigDecimal monto);
	    
	    public boolean modificarSaldo(int idCuenta, BigDecimal saldoFinal);
	    
	    public Cuenta buscarIdConCbu(String cbu);
	    
	    public int obtenerUltimaIdCuenta();
}
