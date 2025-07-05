package negocio;

import java.util.List;

import entidades.Cliente;
import entidades.Usuario;

public interface UsuarioNegocio {
	
	public boolean insertarUsuario(Usuario usuario);	

    public List<Usuario> obtenerUsuarios();   	
    	    
    public boolean modificarUsuario(Usuario usuario);
    
    public boolean eliminarClienteUsuarioCuentas(int idUsuario, int idCliente);
    
    public int buscarPorIDUsuario(int idCliente);
    
    public boolean existeNombreUsuario(String nombre, int idUsuario);    
    
    public Usuario buscarPorIdCliente(int idCliente);
}
