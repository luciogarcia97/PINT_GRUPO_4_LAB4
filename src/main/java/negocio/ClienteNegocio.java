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
	
	public List<Cliente> obtenerClientes();
    
	public Cliente BuscarPorID(int id);
	
}
