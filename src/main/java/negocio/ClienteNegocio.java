package negocio;

import java.util.List;

import entidades.Cliente;
import entidades.Usuario;


public interface ClienteNegocio {

	public boolean insertarCliente(Cliente cliente); 
	
	public int ultimoIdCliente();
	
	public boolean insertarUsuario(Usuario usuario);
	
	public boolean modificarCliente(Cliente cliente);
	
	public boolean eliminarCliente(int idCliente);
	
	public boolean eliminarCuentasUsuario(int idCliente);
	    
	public boolean eliminarUsuario(int idUsuario, int idCliente);
	
	public List<Cliente> obtenerClientes();
    
	public int buscarPorIDUsuario(int id);
	
	public Cliente BuscarPorID(int id);
	
}
