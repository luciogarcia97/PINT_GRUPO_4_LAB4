package dao;

import java.util.List;

import entidades.Cliente;
import entidades.TipoCuenta;

public interface ClienteDao {

	public boolean insertarCliente(Cliente cliente);

    public Cliente BuscarPorID(int id); 

    public boolean ModificarCliente(Cliente cliente);
	
    public List<Cliente> obtenerClientes();
    
    public boolean eliminarCliente(int idCliente);
    
	public int obtenerIDCliente(int dni); 

}

