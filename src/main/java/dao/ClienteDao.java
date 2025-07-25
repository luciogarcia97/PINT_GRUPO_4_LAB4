package dao;

import java.util.List;

import entidades.Cliente;
import entidades.Localidad;
import entidades.Provincia;

public interface ClienteDao {

	public int insertarCliente(Cliente cliente);	
	
	public Cliente BuscarPorID(int id);

    public boolean ModificarCliente(Cliente cliente);
	
    public List<Cliente> obtenerClientes();    
    
    public boolean eliminarClienteUsuarioCuentas(int idUsuario, int idCliente);    
    
    public int buscarPorIDCliente(int id);
    
    public boolean verificoClienteEliminado(int idCliente);
    
    public boolean existeCliente(int idCliente);
    
    public boolean existeCuil(String cuil);
    
    public boolean existeDni(int dni);
    
    public List<Provincia> listarProvincias();
    
    public List<Localidad> listarLocalidades();
    

}

