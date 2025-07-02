package daoImpl;

import dao.ClienteDao;
import dao.Conexion;
import entidades.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Conexion;
import dao.ClienteDao;
import entidades.Cliente;
import entidades.Localidad;
import entidades.Provincia;

public class ClienteDaolmpl implements ClienteDao {

		
	@Override
	public int insertarCliente(Cliente cliente) {

		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int idGenerado = -1;

		try {
			String query = "INSERT INTO cliente (dni, cuil, nombre, apellido, sexo, nacionalidad, fecha_nacimiento, direccion, localidad, provincia, correo_electronico, eliminado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			pst = conexion.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
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
			pst.setBoolean(12, cliente.getEliminado());

			if (pst.executeUpdate() > 0) {
				rs = pst.getGeneratedKeys();
				if (rs.next()) {
					idGenerado = rs.getInt(1); // Recupera el ID generado
				}
				conexion.commit();
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
				if (rs != null) rs.close();
				if (pst != null) pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return idGenerado;
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
			String query = "UPDATE cliente SET dni = ?, cuil= ?, nombre= ?, apellido= ?, sexo= ?, nacionalidad= ?, fecha_nacimiento= ?, direccion= ?, localidad= ?, provincia= ?, correo_electronico= ? WHERE id_cliente= ?";

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
			pst.setInt(12, cliente.getIdCliente());

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
	public boolean eliminarCliente(int idCliente) {
		PreparedStatement pst = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean resultado = false;

		try {
			String query = "UPDATE cliente SET eliminado = 1 WHERE id_cliente = ?";
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
	public boolean existeCliente(int idCliente) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int cantidad = 0;
		boolean existe = false;

		try {
			String query = "SELECT COUNT(*) as cantidad FROM cliente WHERE id_cliente = ? AND eliminado = 0";
			pst = conexion.prepareStatement(query);
			pst.setInt(1, idCliente);
			rs = pst.executeQuery();

			if (rs.next()) {
				cantidad += rs.getInt("cantidad");
			}

			if (cantidad > 0) {
				existe = true;
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
		return existe;
	}

	@Override
	public boolean existeDni(int dni) {

		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int cantidad = 0;
		boolean existe = false;

		try {
			String query = "SELECT COUNT(*) as cantidad FROM cliente WHERE dni = ?";
			pst = conexion.prepareStatement(query);
			pst.setInt(1, dni);
			rs = pst.executeQuery();

			if (rs.next()) {
				cantidad += rs.getInt("cantidad");
			}

			if (cantidad > 0) {
				existe = true;
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

		return existe;
	}

	@Override
	public boolean existeCuil(String cuil) {

		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int cantidad = 0;
		boolean existe = false;

		try {
			String query = "SELECT COUNT(*) as cantidad FROM cliente WHERE cuil = ?";
			pst = conexion.prepareStatement(query);
			pst.setString(1, cuil);
			rs = pst.executeQuery();

			if (rs.next()) {
				cantidad += rs.getInt("cantidad");
			}

			if (cantidad > 0) {
				existe = true;
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

		return existe;

	}

	@Override
	public List<Provincia> listarProvincias() {

		List<Provincia> listaProvincia = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try {
			String query = "SELECT id_provincia,nombre_provincia FROM provincias;";
			pst = conexion.prepareStatement(query);
			rs = pst.executeQuery();

			while (rs.next()) {

				Provincia provincia = new Provincia();
				provincia.setId(rs.getInt("id_provincia"));
				provincia.setNombre(rs.getString("nombre_provincia"));

				listaProvincia.add(provincia);
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

		return listaProvincia;
	}

	@Override
	public List<Localidad> listarLocalidades() {
		List<Localidad> listaLocalidades = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try {
			String query = "SELECT id_localidad,nombre_localidad,id_provincia FROM localidades;";
			pst = conexion.prepareStatement(query);
			rs = pst.executeQuery();

			while (rs.next()) {

				Localidad localidad = new Localidad();
				localidad.setId(rs.getInt("id_localidad"));
				localidad.setNombre(rs.getString("nombre_localidad"));
				localidad.setId_provincia(rs.getInt("id_provincia"));

				listaLocalidades.add(localidad);
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

		return listaLocalidades;

	}

}
