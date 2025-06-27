package daoImpl;

import dao.Conexion;
import dao.CuentaDao;
import entidades.Cuenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		System.out.print(listaCuentas);
		return listaCuentas;
	}
	
	@Override
	public int contarCuentasActivasPorCliente(int idCliente) {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    Connection conexion = Conexion.getConexion().getSQLConexion();
	    int cantidadCuentas = 0;

	    try {
	        String query = "SELECT COUNT(*) as cantidad FROM cuenta WHERE id_cliente = ? AND activa = true";
	        pst = conexion.prepareStatement(query);
	        pst.setInt(1, idCliente);
	        rs = pst.executeQuery();

	        if (rs.next()) {
	            cantidadCuentas = rs.getInt("cantidad");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pst != null) pst.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return cantidadCuentas;
	}
	
	@Override
	public boolean cambiarEstadoCuenta(int idCuenta, boolean activa) {
	    PreparedStatement pst = null;
	    Connection conexion = Conexion.getConexion().getSQLConexion();
	    boolean resultado = false;
	    
	    try {
	        String query = "UPDATE cuenta SET activa = ? WHERE id_cuenta = ?";
	        pst = conexion.prepareStatement(query);
	        pst.setBoolean(1, activa);
	        pst.setInt(2, idCuenta);
	        	        
	        int filasAfectadas = pst.executeUpdate();
	        if (filasAfectadas > 0) {
	        	conexion.commit();
	        	resultado = true;
	        }
	        
	        	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        resultado = false;
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
	public Cuenta obtenerCuentaPorId(int idCuenta) {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    Connection conexion = Conexion.getConexion().getSQLConexion();
	    Cuenta cuenta = null;

	    try {
	        String query = "SELECT c.id_cuenta, c.id_cliente, c.fecha_creacion, c.id_tipo_cuenta, c.numero_cuenta, c.cbu, c.saldo, c.activa, ct.nombre as tipo_nombre " +
	                      "FROM cuenta c " +
	                      "INNER JOIN cuenta_tipo ct ON c.id_tipo_cuenta = ct.id " +
	                      "WHERE c.id_cuenta = ?";
	        
	        pst = conexion.prepareStatement(query);
	        pst.setInt(1, idCuenta);
	        rs = pst.executeQuery();

	        if (rs.next()) {
	            cuenta = mapearCuenta(rs);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pst != null) pst.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return cuenta;
	}
	@Override
	public Cuenta buscarPorID(int idCuenta) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		Cuenta cuenta = null;

		try {
			String query = "SELECT * FROM cuenta WHERE id_cuenta = ?";
			pst = conexion.prepareStatement(query);
			pst.setInt(1, idCuenta);
			rs = pst.executeQuery();

			if (rs.next()) {
				cuenta = mapearCuenta(rs);
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

		return cuenta;
	}

	@Override
	public boolean modificarCuenta(Cuenta cuenta) {
		PreparedStatement pst = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean resultado = false;

		try {
			String query = "UPDATE cuenta SET id_cliente = ?, id_tipo_cuenta = ?, numero_cuenta = ?, cbu = ?, saldo = ?, activa = ?, fecha_creacion = ? WHERE id_cuenta = ?";

			pst = conexion.prepareStatement(query);
			pst.setInt(1, cuenta.getIdCliente());
			pst.setInt(2, cuenta.getIdTipoCuenta());
			pst.setString(3, cuenta.getNumeroCuenta());
			pst.setString(4, cuenta.getCbu());
			pst.setBigDecimal(5, cuenta.getSaldo());
			pst.setBoolean(6, cuenta.isActiva());
			pst.setDate(7, java.sql.Date.valueOf(cuenta.getFechaCreacion()));
			pst.setInt(8, cuenta.getIdCuenta());

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
	public boolean existeCBU(String cbu) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean existe = false;

		try {
			String query = "SELECT COUNT(*) as cantidad FROM cuenta WHERE cbu = ?";
			pst = conexion.prepareStatement(query);
			pst.setString(1, cbu);
			rs = pst.executeQuery();

			if (rs.next()) {
				existe = rs.getInt("cantidad") > 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return existe;
	}

	@Override
	public int contarCuentasActivasPorClienteExcepto(int idCliente, int idCuentaExcluir) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int cantidadCuentas = 0;

		try {
			String query = "SELECT COUNT(*) as cantidad FROM cuenta WHERE id_cliente = ? AND activa = true AND id_cuenta <> ?";
			pst = conexion.prepareStatement(query);
			pst.setInt(1, idCliente);
			pst.setInt(2, idCuentaExcluir);
			rs = pst.executeQuery();

			if (rs.next()) {
				cantidadCuentas = rs.getInt("cantidad");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pst != null) pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cantidadCuentas;
	}

}