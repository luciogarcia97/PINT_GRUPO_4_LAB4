package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Conexion;
import dao.CuentaDao;
import entidades.Cliente;
import entidades.Cuenta;

public class CuentaDaoImpl implements CuentaDao {

	@Override
	public boolean insertarCuenta(Cuenta cuenta) {
		PreparedStatement pst = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean resultado = false;

		try {
			String query = "INSERT INTO cuenta (id_cliente, fecha_creacion, id_tipo_cuenta, numero_cuenta, cbu, saldo, activa) VALUES (?, ?, ?, ?, ?, ?, ?)";

			pst = conexion.prepareStatement(query);
			pst.setInt(1, cuenta.getIdCliente());
			pst.setDate(2, java.sql.Date.valueOf(cuenta.getFechaCreacion()));
			pst.setInt(3, cuenta.getIdTipoCuenta());
			pst.setString(4, cuenta.getNumeroCuenta());
			pst.setString(5, cuenta.getCbu());
			pst.setBigDecimal(6, cuenta.getSaldo());
			pst.setBoolean(7, cuenta.isActiva());

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
	public List<Cuenta> obtenerCuentasPorCliente(int idCliente) {
		List<Cuenta> listaCuentas = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try {
			String query = "SELECT * FROM cuenta WHERE id_cliente = ? AND activa = true";
			pst = conexion.prepareStatement(query);
			pst.setInt(1, idCliente);
			rs = pst.executeQuery();

			while (rs.next()) {
				// Cree el metodo mapear cuenta para evitar repetir el codigo de mapeo de datos
				// recopilados de la db
				Cuenta cuenta = mapearCuenta(rs);
				listaCuentas.add(cuenta);
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

		return listaCuentas;
	}

	
	@Override
	public String generarNumeroCuenta() {
		String numeroCuenta = "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try {
			String query = "SELECT MAX(numero_cuenta) as max_numero FROM cuenta";
			pst = conexion.prepareStatement(query);
			rs = pst.executeQuery();

			long proximoNumero = 0;
			if (rs.next() && rs.getLong("max_numero") >= 0) {
				proximoNumero = rs.getLong("max_numero") + 1;
			}

			numeroCuenta = String.valueOf(proximoNumero);

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

		return numeroCuenta;
	}

	@Override
	public boolean existeNumeroCuenta(String numeroCuenta) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean existe = false;

		try {
			String query = "SELECT COUNT(*) FROM cuenta WHERE numero_cuenta = ?";
			pst = conexion.prepareStatement(query);
			pst.setString(1, numeroCuenta);
			rs = pst.executeQuery();

			if (rs.next()) {
				existe = rs.getInt(1) > 0;
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
	public String generarCBU(String numeroCuenta) {
		// CBU de 22 digitos: 001 (sucursal) + completar con ceros + numero de cuenta
		String sucursal = "001";

		// Calcular cuantos ceros necesitamos para completar 22 digitos
		int cerosFaltantes = 22 - sucursal.length() - numeroCuenta.length();

		// Generar los ceros de relleno
		StringBuilder ceros = new StringBuilder();
		for (int i = 0; i < cerosFaltantes; i++) {
			ceros.append("0");
		}

		// CBU final: sucursal + ceros + numero de cuenta
		return sucursal + ceros.toString() + numeroCuenta;
	}

	@Override
	public Cuenta mapearCuenta(ResultSet rs) throws SQLException {
		Cuenta cuenta = new Cuenta();
		cuenta.setIdCuenta(rs.getInt("id_cuenta"));
		cuenta.setIdCliente(rs.getInt("id_cliente"));
		cuenta.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
		cuenta.setIdTipoCuenta(rs.getInt("id_tipo_cuenta"));
		cuenta.setNumeroCuenta(rs.getString("numero_cuenta"));
		cuenta.setCbu(rs.getString("cbu"));
		cuenta.setSaldo(rs.getBigDecimal("saldo"));
		cuenta.setActiva(rs.getBoolean("activa"));
		return cuenta;
	}
	
	
	public boolean eliminarCuenta(int idCuenta) 											 
	{
		PreparedStatement pst = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean resultado = false;
	
		try {
			String query = "UPDATE cuenta SET activa = 0 WHERE id_cuenta = ?";
			pst = conexion.prepareStatement(query);
			pst.setInt(1, idCuenta);
		
			if (pst.executeUpdate() > 0) { //Si borro algo...
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
	
	public List<Cuenta> obtenerCuentas() 
	{	
		
		List<Cuenta> listaCuentas = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();

		try {
			String query = "SELECT id_cuenta, id_cliente, fecha_creacion, id_tipo_cuenta, numero_cuenta, cbu, saldo, activa FROM cuenta";
			pst = conexion.prepareStatement(query);
			rs = pst.executeQuery();

			while (rs.next()) {
				
				Cuenta c = new Cuenta();
				c.setIdCuenta(rs.getInt("id_cuenta"));
				c.setIdCliente(rs.getInt("id_cliente"));
				c.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
				c.setIdTipoCuenta(rs.getInt("id_tipo_cuenta"));
				c.setNumeroCuenta(rs.getString("numero_cuenta"));
				c.setCbu(rs.getString("cbu"));
				c.setSaldo(rs.getBigDecimal("saldo"));
				c.setActiva(rs.getBoolean("activa")); 
				listaCuentas.add(c);
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

		return listaCuentas;
	}

}