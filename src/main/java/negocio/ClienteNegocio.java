package negocio;

import java.util.List;

import entidades.Cliente;


public interface ClienteNegocio {

	public boolean insertarCliente(Cliente cliente); 
	
	public boolean modificarCliente(Cliente cliente);
	
	public boolean elimiarCliente(Cliente cliente);
	
	public List<Cliente> listadoClientes();
	
}
