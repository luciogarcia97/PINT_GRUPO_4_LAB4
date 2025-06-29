package negocio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import entidades.Cuenta;

public interface CuentaNegocio {
	
	public boolean insertarCuenta(Cuenta cuenta); 
    
    public List<Cuenta> obtenerCuentasPorCliente(int idCliente);
    
    public String generarNumeroCuenta();	
    	    
    public boolean existeNumeroCuenta(String numeroCuenta);
    
    public String generarCBU(String numeroCuenta);       
  	    
    public Cuenta mapearCuenta(ResultSet rs) throws SQLException;
    
    public boolean eliminarCuenta(int idCuenta);
    
    public List<Cuenta> obtenerCuentas();
    
    public Cuenta buscarPorID(int idCuenta);
    
    public boolean modificarCuenta(Cuenta cuenta);

    public boolean puedeCrearCuenta(int idCliente);
    
    //public boolean existeCliente(int idCliente);
    
    public boolean reactivarCuenta(int idCuenta);
    
    public Cuenta obtenerCuentaPorId(int idCuenta);
    
    public boolean eliminarCuentasUsuario(int idCliente);

    public boolean existeCBU(String cbu);

    public boolean puedeCrearCuenta(int idCliente, int idCuentaExcluir);

}
