package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Conexion;
import dao.ClienteDao;
import entidades.Cliente;
import entidades.Cuenta;

public class ClienteDaolmpl implements ClienteDao {

	public boolean insertarCliente(Cliente cliente) 
	{
		
		PreparedStatement pst = null;
	    Connection conexion = Conexion.getConexion().getSQLConexion();
	    boolean resultado = false;
	    
	    try {
	    	
            String query = "INSERT INTO cliente (dni, cuil, nombre, apellido, sexo, nacionalidad, fecha_nacimiento, direccion, localidad, provincia, correo_electronico, eliminado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
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
            pst.setBoolean(12, false); 
            
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
	
	
	public Cliente BuscarPorID(int id) {
		 PreparedStatement pst = null;
	     ResultSet rs = null;
	     Connection conexion = Conexion.getConexion().getSQLConexion();
	     Cliente cliente = new Cliente();
		
		try 
		{
			String query = "SELECT id_cliente, dni, cuil, nombre, apellido, sexo, nacionalidad, fecha_nacimiento, direccion, localidad, provincia, correo_electronico FROM cliente where id_cliente = ? ";
			pst = conexion.prepareStatement(query);
			pst.setInt(1, id);
			rs = pst.executeQuery();	
			cliente.setIdCliente(rs.getInt(1));
			cliente.setDni(rs.getInt("dni"));
			cliente.setNombre(rs.getString("nombre"));
			cliente.setCuil(rs.getString("cuil"));
			cliente.setApellido(rs.getString("apellido"));
			cliente.setSexo(rs.getString("sexo"));
			cliente.setNacionalidad(rs.getString("nacionalidad"));
			cliente.setFechaNacimiento(rs.getString("fecha_nacimiento"));
			cliente.setDireccion(rs.getString("direccion"));
			cliente.setLocalidad(rs.getString("localidad"));
			cliente.setProvincia(rs.getString("provincia"));
			cliente.setCorreoElectronico(rs.getString("correo_electronico"));
		}
		catch(Exception e) {
          e.printStackTrace();
      }
		finally {
			try {
				if(rs != null) rs.close();
				if(pst != null) pst.close();
			}catch(SQLException e){
				 e.printStackTrace();
			}
		}
		
		
		
		return cliente;
	}
	
	
	
	
	
	public boolean ModificarCliente(Cliente cliente) {
		 PreparedStatement pst = null;
	     ResultSet rs = null;
	     Connection conexion = Conexion.getConexion().getSQLConexion();
		
		try 
		{
			String query = "UPDATE  cliente SET nombre = ?, apellido = ?,sexo = ?, direccion = ?,  localidad = ?, provincia = ?, correo_electronico = ? WHERE id_cliente =  ? ";
			pst = conexion.prepareStatement(query);
			pst.setString(1, cliente.getNombre());
			pst.setString(2, cliente.getApellido());
			pst.setString(3, cliente.getSexo());
			pst.setString(4, cliente.getDireccion());
			pst.setString(5, cliente.getLocalidad());
			pst.setString(6, cliente.getProvincia());
			pst.setString(7, cliente.getCorreoElectronico());
			pst.setInt(7, cliente.getIdCliente());
			
			rs = pst.executeQuery();		
			 
			return true;
		}
		catch(Exception e) {
          e.printStackTrace();
          return false;
      }
		finally {
			try {
				if(rs != null) rs.close();
				if(pst != null) pst.close();
			}catch(SQLException e){
				 e.printStackTrace();
			}
		}
		
		
	}
	
	public List<Cliente> obtenerClientes() {
		List<Cliente> listaClientes = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try {
			String query = "SELECT dni, cuil, nombre, apellido, sexo, nacionalidad, fecha_nacimiento, direccion, localidad, provincia, correo_electronico, eliminado FROM cliente WHERE eliminado = false";
			pst = conexion.prepareStatement(query);
			rs = pst.executeQuery();

			while (rs.next()) {
				
				Cliente cliente = new Cliente();
				cliente.setDni(rs.getInt("dni"));
				cliente.setCuil(rs.getString("cuil"));
				cliente.setNombre(rs.getString("nombre"));
				cliente.setApellido(rs.getString("apellido"));
				cliente.setSexo(rs.getString("sexo"));
				cliente.setNacionalidad(rs.getString("nacionalidad"));
				cliente.setFechaNacimiento(rs.getString("fecha_nacimiento")); 
				cliente.setDireccion(rs.getString("direccion"));
				cliente.setLocalidad(rs.getString("localidad"));
				cliente.setProvincia(rs.getString("provincia"));
				cliente.setCorreoElectronico(rs.getString("correo_electronico"));
				cliente.setEliminado(rs.getBoolean("eliminado"));  
				    listaClientes.add(cliente);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaClientes;
	}
	
	
}
