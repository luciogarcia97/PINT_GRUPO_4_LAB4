package negocioImpl;
import java.util.List;
import dao.ClienteDao;
import daoImpl.ClienteDaolmpl;
import entidades.Cliente;
import entidades.Localidad;
import entidades.Provincia;
import negocio.ClienteNegocio;


public class ClienteNegociolmpl implements ClienteNegocio {

	private ClienteDao clienteDao;
	
	public ClienteNegociolmpl() {
		super();
		this.clienteDao = new ClienteDaolmpl();
	}
	
	@Override
	public int insertarCliente(Cliente cliente) {
		
		return clienteDao.insertarCliente(cliente);	
	}	
    
	@Override
	public boolean eliminarCliente(int idCliente) {
	
		return clienteDao.eliminarCliente(idCliente);		
		
	}
	
	@Override
	public boolean eliminarUsuario(int idUsuario, int idCliente) {
		
		return clienteDao.eliminarUsuario(idUsuario, idCliente);
	}

	@Override
	public boolean eliminarCuentasUsuario(int idCliente) {
		
		return clienteDao.eliminarCuentasUsuario(idCliente);
	}
	
	@Override
	public int buscarPorIDCliente(int id) {
		
		return clienteDao.buscarPorIDCliente(id);
	}
    
	@Override
	public boolean modificarCliente(Cliente cliente) {

		return clienteDao.ModificarCliente(cliente);
		
	}

	@Override
	public List<Cliente> obtenerClientes() {
		
		return clienteDao.obtenerClientes();
	}
	
	@Override
	public Cliente BuscarPorID(int id) {
		
		return clienteDao.BuscarPorID(id);
	}
	
	@Override
	public boolean existeCliente(int idCliente) {
		
		return clienteDao.existeCliente(idCliente);
		
	}
	
	@Override
	public boolean existeCuil(String cuil) {
	
		return clienteDao.existeCuil(cuil);
	}    
	
	@Override
    public boolean existeDni(int dni) {
    
		return clienteDao.existeDni(dni);
    }

	@Override
	public List<Provincia> listarProvincias() {

		return clienteDao.listarProvincias();
	}

	@Override
	public List<Localidad> listarLocalidades() {
		
		return clienteDao.listarLocalidades();
	}			
    
}
	

	

	
