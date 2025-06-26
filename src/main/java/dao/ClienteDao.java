package dao;

import java.util.List;

import entidades.Cliente;
import entidades.TipoCuenta;
import entidades.Usuario;

public interface ClienteDao {

	public boolean insertarCliente(Cliente cliente);
	
	public int ultimoIdCliente();
	
	public boolean insertarUsuario(Usuario usuario);
	
	public Cliente BuscarPorID(int id);

	public int buscarPorIDUsuario(int id);

    public boolean ModificarCliente(Cliente cliente);
	
    public List<Cliente> obtenerClientes();
    
    public boolean eliminarCliente(int idCliente);
    
    public boolean eliminarCuentasUsuario(int idCliente);
    
    public boolean eliminarUsuario(int idUsuario, int idCliente);
    
    public boolean existeCliente(int idCliente);
    
    public boolean existeCuil(String cuil);
    
    public boolean existeDni(int dni);

}

