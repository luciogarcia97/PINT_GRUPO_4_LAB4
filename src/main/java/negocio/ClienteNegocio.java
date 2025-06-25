package negocio;

import java.util.List;

import entidades.Cliente;


public interface ClienteNegocio {

	public boolean insertarCliente(Cliente cliente); 
	
	public boolean modificarCliente(Cliente cliente);
	
	public boolean eliminarCliente(int idCliente);
	
	public List<Cliente> obtenerClientes();
    
	public Cliente BuscarPorID(int id);
	
}
