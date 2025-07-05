package dao;

import java.util.List;

import entidades.Usuario;

public interface UsuarioDao {
	
	public boolean insertarUsuario(Usuario usuario); 
    
    public List<Usuario> obtenerUsuarios();   	
    	    
    public boolean modificarUsuario(Usuario usuario);    
    
    public int buscarPorIDUsuario(int idCliente);
    
    public boolean existeNombreUsuario(String nombre, int idUsuario);   
    
    public Usuario buscarPorIdCliente(int idCliente);

}
