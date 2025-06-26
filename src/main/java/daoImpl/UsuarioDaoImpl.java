package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Conexion;
import dao.UsuarioDao;
import entidades.Cliente;
import entidades.Usuario;

public class UsuarioDaoImpl implements UsuarioDao {	
	

	@Override
	public boolean insertarUsuario(Usuario usuario) {
		PreparedStatement pst = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean resultado = false;
		
		try {
			String query = "INSERT INTO usuario (id_usuario, id_cliente, usuario, contraseña, tipo_usuario, eliminado, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, ?)";

			pst = conexion.prepareStatement(query);
			pst.setInt(1, usuario.getId_usuario());
			pst.setInt(2, usuario.getId_cliente());			
			pst.setString(3, usuario.getUsuario());
			pst.setString(4, usuario.getContrasena());			
			pst.setString(5, usuario.getTipo_usuario());			
			pst.setInt(6, usuario.getEliminado());			
			pst.setDate(7, java.sql.Date.valueOf(usuario.getFecha_creacion()));

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
				if (pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return resultado;
	}
	
	@Override
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
	
	@Override
	public int ultimoIdCliente() {			
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int ultimoIdCliente = -1;
		
		try {
			String query = "SELECT MAX(id_cliente) AS ultimo_id FROM cliente";
			pst = conexion.prepareStatement(query);			
			rs = pst.executeQuery();
			
			if (rs.next()) {
			    ultimoIdCliente = rs.getInt("ultimo_id");
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

		return ultimoIdCliente;			
	}
	

	@Override
	public List<Usuario> obtenerUsuarios() {
		List<Usuario> listaUsuario = new ArrayList<>();		
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try {
			String query = "SELECT * FROM usuario WHERE eliminado = 0";
			pst = conexion.prepareStatement(query);			
			rs = pst.executeQuery();

			while (rs.next()) {	
				Usuario usuario = new Usuario();
				usuario.setId_usuario(rs.getInt("id_usuario"));
				usuario.setId_cliente(rs.getInt("id_cliente"));								
				usuario.setUsuario(rs.getString("usuario"));
				usuario.setContrasena(rs.getString("contraseña"));
				usuario.setTipo_usuario(rs.getString("tipo_usuario"));
				usuario.setEliminado(rs.getInt("eliminado"));
				usuario.setFecha_creacion(rs.getDate("fecha_creacion").toLocalDate());							
				
				listaUsuario.add(usuario);
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

		return listaUsuario;	
		
	}

	@Override
	public boolean modificarUsuario(Usuario usuario) {
		
		PreparedStatement pst = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean resultado = false;
		
		try {
			String query = "UPDATE usuario SET id_cliente = ?, usuario = ?, contraseña = ?, tipo_usuario = ?, eliminado = ?, fecha_creacion = ? WHERE id_usuario = ?";

			pst = conexion.prepareStatement(query);
			pst.setInt(1, usuario.getId_cliente());			
			pst.setString(2, usuario.getUsuario());
			pst.setString(3, usuario.getContrasena());			
			pst.setString(4, usuario.getTipo_usuario());			
			pst.setInt(5, usuario.getEliminado());			
			pst.setDate(6, java.sql.Date.valueOf(usuario.getFecha_creacion()));
			pst.setInt(7, usuario.getId_usuario());  
			
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
				if (pst != null)
					pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return resultado;		
		
	}
	
	@Override
	public boolean eliminarUsuario(int idUsuario, int idCliente) {
		PreparedStatement pst = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean resultado = false;
		
		try {
			String query = "UPDATE usuario SET eliminado = 1 WHERE id_usuario = ?";
			pst = conexion.prepareStatement(query);
			pst.setInt(1, idUsuario);
			
			if (pst.executeUpdate() > 0) { 
				conexion.commit();
				resultado = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null)
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(eliminarClienteUsuario(idCliente) && eliminarCuentasUsuario(idCliente) && resultado) {
			return true;
		} else {
			return false;
		}	
			
	}
	
	@Override
	public boolean eliminarClienteUsuario(int idCliente) {
		PreparedStatement pst = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean resultado = false;
		
		try {
			String query = "UPDATE cliente SET eliminado = 0 WHERE id_cliente = ?";
			pst = conexion.prepareStatement(query);
			pst.setInt(1, idCliente);
			
			if (pst.executeUpdate() > 0) { 
				conexion.commit();
				resultado = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null)
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return resultado;	
	}
	
	@Override
	public boolean eliminarCuentasUsuario(int idCliente) {
		PreparedStatement pst = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean resultado = false;
		
		try {
			String query = "UPDATE cuenta SET activa = 0 WHERE id_cliente = ?";
			pst = conexion.prepareStatement(query);
			pst.setInt(1, idCliente);
			
			if (pst.executeUpdate() > 0) { 
				conexion.commit();
				resultado = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null)
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return resultado;	
	}


}
