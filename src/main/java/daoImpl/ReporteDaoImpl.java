package daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.Conexion;
import dao.ReporteDao;

public class ReporteDaoImpl implements ReporteDao {

	@Override
	public Double sumaDepositos(Date fechaInicio, Date fechaFin) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		Double suma = 0.0;

		try {
			String query = """
					SELECT SUM(m.importe) AS total_depositos FROM movimiento m
					JOIN movimiento_tipo mt ON m.id_tipo_movimiento = mt.id_tipo_movimiento
					WHERE mt.descripcion = 'Depósito'
					  AND m.fecha BETWEEN ? AND ? """;

			pst = conexion.prepareStatement(query);
			pst.setDate(1, new java.sql.Date(fechaInicio.getTime()));
			pst.setDate(2, new java.sql.Date(fechaFin.getTime()));

			rs = pst.executeQuery();

			if (rs.next()) {
				suma = rs.getDouble("total_depositos");
				if (rs.wasNull()) {
					suma = 0.0;
				}
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

		return suma;
	}

	@Override
	public int transferenciasRealizadas(Date fechaInicio, Date fechaFin) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int cantidad = 0;

		try {
			String query = """
					SELECT COUNT(*) AS total_transferencias FROM movimiento m
					JOIN movimiento_tipo mt ON m.id_tipo_movimiento = mt.id_tipo_movimiento
					WHERE mt.descripcion = 'Transferencia'
					  AND m.fecha BETWEEN ? AND ? """;

			pst = conexion.prepareStatement(query);
			pst.setDate(1, new java.sql.Date(fechaInicio.getTime()));
			pst.setDate(2, new java.sql.Date(fechaFin.getTime()));

			rs = pst.executeQuery();

			if (rs.next()) {
				cantidad = rs.getInt("total_transferencias");
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

		return cantidad;
	}

	@Override
	public int prestamosOtorgados(Date fechaInicio, Date fechaFin) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int cantidad = 0;

		try {
			String query = """
					SELECT COUNT(*) AS total_prestamos FROM prestamo
					WHERE fecha_aprobacion IS NOT NULL
					  AND eliminado = 0
					  AND fecha_aprobacion BETWEEN ? AND ? """;

			pst = conexion.prepareStatement(query);
			pst.setDate(1, new java.sql.Date(fechaInicio.getTime()));
			pst.setDate(2, new java.sql.Date(fechaFin.getTime()));

			rs = pst.executeQuery();

			if (rs.next()) {
				cantidad = rs.getInt("total_prestamos");
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

		return cantidad;
	}

	@Override
	public int clientesActivos(Date fechaInicio, Date fechaFin) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		int cantidad = 0;

		try {
			String query = """
					SELECT COUNT(DISTINCT c.id_cliente) AS total_clientes_activos FROM cliente c
					JOIN cuenta cu ON c.id_cliente = cu.id_cliente
					WHERE cu.activa = 1 AND cu.fecha_creacion BETWEEN ? AND ?
					AND c.eliminado = 0 """;

			pst = conexion.prepareStatement(query);
			pst.setDate(1, new java.sql.Date(fechaInicio.getTime()));
			pst.setDate(2, new java.sql.Date(fechaFin.getTime()));

			rs = pst.executeQuery();

			if (rs.next()) {
				cantidad = rs.getInt("total_clientes_activos");
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

		return cantidad;
	}

}
