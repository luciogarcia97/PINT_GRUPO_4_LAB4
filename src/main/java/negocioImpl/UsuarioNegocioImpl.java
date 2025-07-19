package negocioImpl;

import dao.ClienteDao;
import dao.CuentaDao;
import dao.UsuarioDao;
import daoImpl.ClienteDaolmpl;
import daoImpl.CuentaDaoImpl;
import daoImpl.UsuarioDaoImpl;
import entidades.Usuario;
import java.util.List;
import negocio.UsuarioNegocio;

public class UsuarioNegocioImpl implements UsuarioNegocio {

	private UsuarioDao usuarioDao;
	private ClienteDao clienteDao;
	private CuentaDao cuentaNegocio;
	
	
	public UsuarioNegocioImpl() {
		super();
		this.usuarioDao = new UsuarioDaoImpl();
		this.clienteDao = new ClienteDaolmpl();
		this.cuentaNegocio = new CuentaDaoImpl();
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
	public boolean eliminarClienteUsuarioCuentas(int idUsuario, int idCliente) {		
		
		return  clienteDao.eliminarClienteUsuarioCuentas(idUsuario, idCliente);		
	}
	
	@Override
	public int buscarPorIDUsuario(int idCliente) {
	
		return usuarioDao.buscarPorIDUsuario(idCliente);
	}
	
	@Override
	public Usuario buscarPorIdCliente(int idCliente) {
	
		return usuarioDao.buscarPorIdCliente(idCliente);
	}

	@Override
	public boolean existeNombreUsuario(String nombre, int idUsuario) {
		
		return usuarioDao.existeNombreUsuario(nombre, idUsuario);
	}

}
