package dao;

import java.util.List;

import entidades.Cliente;

public interface ClienteDao {

	public boolean insertarCliente(Cliente cliente);
	
	public int ultimoIdCliente();
	
	public Cliente BuscarPorID(int id);

    public boolean ModificarCliente(Cliente cliente);
	
    public List<Cliente> obtenerClientes();
    
    public boolean eliminarCliente(int idCliente);
    
    public boolean existeCliente(int idCliente);
    
    public boolean existeCuil(String cuil);
    
    public boolean existeDni(int dni);

}

