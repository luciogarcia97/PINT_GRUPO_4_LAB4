package negocio;

import java.util.List;

import entidades.Cliente;
import entidades.Localidad;
import entidades.Provincia;


public interface ClienteNegocio {

	public int insertarCliente(Cliente cliente); 
	
	public boolean modificarCliente(Cliente cliente);
	
	public boolean eliminarCliente(int idCliente);
	
	public List<Cliente> obtenerClientes();
	
	public Cliente BuscarPorID(int id);
	
	public boolean existeCliente(int idCliente);
	
	public boolean existeCuil(String cuil);
    
    public boolean existeDni(int dni);
    
    public List<Provincia> listarProvincias();
    
    public List<Localidad> listarLocalidades();

    
}
