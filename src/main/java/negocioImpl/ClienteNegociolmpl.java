package negocioImpl;

import entidades.Cliente;
import negocio.ClienteNegocio;

public class ClienteNegociolmpl implements ClienteNegocio {

	private Cliente clienteDaoImpl;
	
	public ClienteNegociolmpl() {
	      this.clienteDaoImpl = new ClienteDaoImpl(); 
  }
	
	@Override
	public boolean insertarCliente(Cliente cliente) {
		
		boolean fila = clienteDaoImpl.insertarCliente(cliente);
		
		return fila;
	}
    
	@Override
	public boolean elimiarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return false;
	}
    
	@Override
	public boolean modificarCliente(Cliente cliente) {

		boolean fila = clienteDaoImpl.ModificarCliente(cliente);
		
		return fila;
	}
    
	@Override
	public List<Cliente> listadoClientes() {
		// TODO Auto-generated method stub
		return null;
	}
	

	

	
