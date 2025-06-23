package negocioImpl;

import java.util.List;

import dao.UsuarioDao;
import daoImpl.UsuarioDaoImpl;
import entidades.Usuario;
import negocio.UsuarioNegocio;

public class UsuarioNegocioImpl implements UsuarioNegocio {

	private UsuarioDao usuarioDao;
	
	
	public UsuarioNegocioImpl() {
		super();
		this.usuarioDao = new UsuarioDaoImpl();
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

}
