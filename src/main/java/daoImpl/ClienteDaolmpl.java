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
import entidades.Usuario;

public class ClienteDaolmpl implements ClienteDao {

	@Override
	public boolean insertarCliente(Cliente cliente) {

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
			pst.setBoolean(12, true);

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
	public Cliente BuscarPorID(int id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		Cliente cliente = null;

		try {
			String query = "SELECT id_cliente, dni, cuil, nombre, apellido, sexo, nacionalidad, fecha_nacimiento, direccion, localidad, provincia, correo_electronico FROM cliente WHERE id_cliente = ?";
			pst = conexion.prepareStatement(query);
			pst.setInt(1, id);
			rs = pst.executeQuery();

			if (rs.next()) {
				cliente = new Cliente();
				cliente.setIdCliente(rs.getInt("id_cliente"));
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
			}
		} catch (Exception e) {
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

		return cliente;
	}

	@Override
	public boolean ModificarCliente(Cliente cliente) {
		PreparedStatement pst = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean resultado = false;

		try {
			String query = "UPDATE  cliente SET nombre = ?, apellido = ?,sexo = ?, direccion = ?,  localidad = ?, provincia = ?, correo_electronico = ? WHERE id_cliente =  ?";

			pst = conexion.prepareStatement(query);
			pst.setString(1, cliente.getNombre());
			pst.setString(2, cliente.getApellido());
			pst.setString(3, cliente.getSexo());
			pst.setString(4, cliente.getDireccion());
			pst.setString(5, cliente.getLocalidad());
			pst.setString(6, cliente.getProvincia());
			pst.setString(7, cliente.getCorreoElectronico());
			pst.setInt(8, cliente.getIdCliente());

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
	public List<Cliente> obtenerClientes() {
		List<Cliente> listaClientes = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try {
			String query = "SELECT id_cliente, dni, cuil, nombre, apellido, sexo, nacionalidad, fecha_nacimiento, direccion, localidad, provincia, correo_electronico, eliminado FROM cliente";
			pst = conexion.prepareStatement(query);
			rs = pst.executeQuery();

			while (rs.next()) {

				Cliente cliente = new Cliente();
				cliente.setIdCliente(rs.getInt("id_cliente"));
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
	
	@Override
	public boolean eliminarUsuario(int idUsuario, int idCliente) {
		PreparedStatement pst = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean resultado = false;
		
		try {
			String query = "UPDATE usuario SET eliminado = 0 WHERE id_usuario = ?";
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
		
		if(eliminarCliente(idCliente) && eliminarCuentasUsuario(idCliente) && resultado) {
			return true;
		} else {
			return false;
		}	
			
	}
	
	@Override
	public boolean eliminarCliente(int idCliente) {
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
	
	@Override
	public int buscarPorIDUsuario(int id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int resultado = -1;

		try {
			String query = "SELECT id_cliente FROM usuario WHERE id_cliente = ?";
			pst = conexion.prepareStatement(query);
			pst.setInt(1, id);
			rs = pst.executeQuery();

			if (rs.next()) {
				resultado = rs.getInt("id_cliente");
			}
		} catch (Exception e) {
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

		return resultado;
	}

}
