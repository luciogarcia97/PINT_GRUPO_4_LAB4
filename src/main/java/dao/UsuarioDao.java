package dao;


import java.util.List;

import entidades.Cliente;
import entidades.Usuario;

public interface UsuarioDao {
	
	public boolean insertarUsuario(Usuario usuario); 
	
	public boolean insertarCliente(Cliente cliente);
	
	public int ultimoIdCliente(); 
    
    public List<Usuario> obtenerUsuarios();   	
    	    
    public boolean modificarUsuario(Usuario usuario);  
    
    public boolean eliminarUsuario(int idUsuario, int idCliente);
    
    public boolean eliminarClienteUsuario(int idCliente);
    
    public boolean eliminarCuentasUsuario(int idCliente);

}
