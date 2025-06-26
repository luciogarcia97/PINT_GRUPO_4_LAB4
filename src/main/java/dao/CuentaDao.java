package dao;

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

}
