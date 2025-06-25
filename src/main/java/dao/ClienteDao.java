package dao;

import java.util.List;

import entidades.Cliente;
import entidades.TipoCuenta;
import entidades.Usuario;

public interface ClienteDao {

	public boolean insertarCliente(Cliente cliente);
	
	public int ultimoIdCliente();
	
	public boolean insertarUsuario(Usuario usuario);

    public Cliente BuscarPorID(int id); 

    public boolean ModificarCliente(Cliente cliente);
	
    public List<Cliente> obtenerClientes();
    
    public boolean eliminarCliente(int idCliente);

}

