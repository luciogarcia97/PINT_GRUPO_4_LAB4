package dao;

import entidades.Cliente;

public interface ClienteDao {

	/*public boolean insertarCliente(Cliente cliente) 
	{
		
		PreparedStatement pst = null;
	    Connection conexion = Conexion.getConexion().getSQLConexion();
	    boolean resultado = false;
	    
	    try {
	    	
            String query = "INSERT INTO cliente (dni, cuil, nombre, apellido, sexo, nacionalidad, fechaNacimiento, direccion, localidad, provincia, correoElectronico, eliminado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            pst = conexion.prepareStatement(query);
            pst.setInt(1, cliente.getDni());
            pst.setString(2, cliente.getCuil());
            pst.setString(3, cliente.getNombre());
            pst.setString(4, cliente.getApellido());
            pst.setString(5, cliente.getSexo());
            pst.setString(6, cliente.getNacionalidad());
            pst.setDate(7, java.sql.Date.valueOf(cliente.getFechaNacimiento()));
            pst.setString(8, cliente.getDireccion());
            pst.setString(9, cliente.getLocalidad());
            pst.setString(10, cliente.getProvincia());
            pst.setString(11, cliente.getCorreoElectronico());
            pst.setBoolean(12, false); // Cuando se crea un cliente, por defecto no esta eliminado.
            
            if (pst.executeUpdate() > 0) {
                conexion.commit();
                resultado = true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conexion.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (pst != null) pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return resultado;
		
	}
	*/
	
	public boolean insertarCliente(Cliente cliente);

    public Cliente BuscarPorID(int id); 

    public boolean ModificarCliente(Cliente cliente);
	
	
}
