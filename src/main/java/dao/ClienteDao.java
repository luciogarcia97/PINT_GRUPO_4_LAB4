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
    
    public boolean eliminarCliente(int idCliente);
    
    public boolean eliminarUsuario(int idUsuario, int idCliente);
    
    public boolean eliminarCuentasUsuario(int idCliente);
    
    public int buscarPorIDCliente(int id);
    
    public boolean existeCliente(int idCliente);
    
    public boolean existeCuil(String cuil);
    
    public boolean existeDni(int dni);
    
    public List<Provincia> listarProvincias();
    
    public List<Localidad> listarLocalidades();
    

}

