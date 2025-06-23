package negocioImpl;
import java.util.List;
import dao.ClienteDao;
import daoImpl.ClienteDaolmpl;
import entidades.Cliente;
import negocio.ClienteNegocio;


public class ClienteNegociolmpl implements ClienteNegocio {

	private ClienteDao clienteDao;
	
	public ClienteNegociolmpl() {
		super();
		this.clienteDao = new ClienteDaolmpl();
	}
	
	@Override
	public boolean insertarCliente(Cliente cliente) {
		
		boolean fila = clienteDao.insertarCliente(cliente);
		
		return fila;
	}
    
	@Override
	public boolean elimiarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return false;
	}
    
	@Override
	public boolean modificarCliente(Cliente cliente) {

		boolean fila = clienteDao.ModificarCliente(cliente);
		
		return fila;
	}

	@Override
	public List<Cliente> obtenerClientes() {
		return clienteDao.obtenerClientes();
	}
    
}
	

	

	
