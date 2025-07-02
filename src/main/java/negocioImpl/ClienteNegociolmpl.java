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
	
		boolean fila = clienteDao.eliminarCliente(idCliente);
		
		return fila;
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
	
	@Override
	public Cliente BuscarPorID(int id) {
		
		return clienteDao.BuscarPorID(id);
	}
	
	@Override
	public boolean existeCliente(int idCliente) {
		
		return clienteDao.existeCliente(idCliente);
		
	}
	
	@Override
	public boolean existeCuil(String cuil)
	{
		return clienteDao.existeCuil(cuil);
	}
    
	
	@Override
    public boolean existeDni(int dni)
    {
		return clienteDao.existeDni(dni);
    }

	@Override
	public List<Provincia> listarProvincias() {

		return clienteDao.listarProvincias();
	}

	public List<Localidad> listarLocalidades() {
		
		return clienteDao.listarLocalidades();
	}

	
    
}
	

	

	
