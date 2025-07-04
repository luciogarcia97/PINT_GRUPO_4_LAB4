package negocioImpl;

import java.util.List;

import dao.UsuarioDao;
import dao.ClienteDao;
import dao.CuentaDao;
import daoImpl.UsuarioDaoImpl;
import daoImpl.ClienteDaolmpl;
import daoImpl.CuentaDaoImpl;
import entidades.Cliente;
import entidades.Usuario;
import negocio.UsuarioNegocio;
import negocio.ClienteNegocio;

public class UsuarioNegocioImpl implements UsuarioNegocio {

	private UsuarioDao usuarioDao;
	private ClienteDao clienteDao;	
	
	public UsuarioNegocioImpl() {
		super();
		this.usuarioDao = new UsuarioDaoImpl();
		this.clienteDao = new ClienteDaolmpl();		
	}

	@Override
	public boolean insertarUsuario(Usuario usuario) {

		return usuarioDao.insertarUsuario(usuario);
	}	

	@Override
	public List<Usuario> obtenerUsuarios() {
		
		return usuarioDao.obtenerUsuarios();
	}

	@Override
	public boolean modificarUsuario(Usuario usuario) {
		
		return usuarioDao.modificarUsuario(usuario);
	}
	
	@Override
	public boolean eliminarUsuario(int idUsuario, int idCliente) {		
		
		if(clienteDao.eliminarCliente(idCliente) && clienteDao.eliminarUsuario(idUsuario, idCliente) 
		   && clienteDao.eliminarCuentasUsuario(idCliente)) {
			
			return true;
		}		
		return false;
	}
	
	@Override
	public int buscarPorIDUsuario(int idCliente) {
	
		return usuarioDao.buscarPorIDUsuario(idCliente);
	}
	
	/*@Override
	public Usuario buscarPorNombre(String nombre, int id) {
	
		return usuarioDao.buscarPorNombre(nombre,id);
	}*/
	
	@Override
	public Usuario buscarPorIdCliente(int idCliente) {
	
		return usuarioDao.buscarPorIdCliente(idCliente);
	}

	@Override
	public boolean existeNombreUsuario(String nombre, int idUsuario) {
		
		return usuarioDao.existeNombreUsuario(nombre, idUsuario);
	}

}
