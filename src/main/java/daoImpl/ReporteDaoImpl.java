package daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			//Query para sumar todos los depositos
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
			// Query para contar solo las transferencias de egreso
			String query = """
					SELECT COUNT(*) AS total_transferencias FROM movimiento m
					JOIN movimiento_tipo mt ON m.id_tipo_movimiento = mt.id_tipo_movimiento
					WHERE mt.descripcion = 'Transferencia'
					  AND m.detalle LIKE 'Transferencia a%'
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
			// Query para contar los prestamos aprobados
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
			//Query para contar clientes activos
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

	@Override
	public List<Map<String, Object>> obtenerDatosDetallados(String tipoReporte, Date fechaInicio, Date fechaFin) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		List<Map<String, Object>> datos = new ArrayList<>();

		try {
			String query = "";
			
			switch (tipoReporte) {
				case "depositos":
					// Query que obtiene los detalles de los depositos
					query = """
						SELECT 
							CONCAT(cl.nombre, ' ', cl.apellido) AS 'Cliente',
							c.numero_cuenta AS 'Número de Cuenta',
							m.importe AS 'Monto',
							m.detalle AS 'Detalle',
							m.fecha AS 'Fecha'
						FROM movimiento m
						JOIN movimiento_tipo mt ON m.id_tipo_movimiento = mt.id_tipo_movimiento
						JOIN cuenta c ON m.id_cuenta = c.id_cuenta
						JOIN cliente cl ON c.id_cliente = cl.id_cliente
						WHERE mt.descripcion = 'Depósito'
						  AND m.fecha BETWEEN ? AND ?
						ORDER BY m.fecha DESC
						""";
					break;
					
				case "transferencias":
					// Quety para las transferencias que trae datos de movimientos, cliente y cuentas
					query = """
						SELECT 
							CONCAT(c1.nombre, ' ', c1.apellido) AS 'Cliente',
							m1.importe AS 'Monto',
							cu2.numero_cuenta AS 'Número de Cuenta Destino',
							m1.detalle AS 'Detalle',
							m1.fecha AS 'Fecha'
						FROM 
							movimiento m1
						JOIN 
							movimiento_tipo mt1 ON m1.id_tipo_movimiento = mt1.id_tipo_movimiento
						JOIN 
							cuenta cu1 ON m1.id_cuenta = cu1.id_cuenta
						JOIN 
							cliente c1 ON cu1.id_cliente = c1.id_cliente

						JOIN 
							movimiento m2 ON m1.fecha = m2.fecha AND m1.importe = m2.importe
						JOIN 
							movimiento_tipo mt2 ON m2.id_tipo_movimiento = mt2.id_tipo_movimiento
						JOIN 
							cuenta cu2 ON m2.id_cuenta = cu2.id_cuenta

						WHERE 
							mt1.descripcion = 'Transferencia'
							AND m1.detalle LIKE 'Transferencia a%'
							AND mt2.descripcion = 'Transferencia'
							AND m2.detalle LIKE 'Transferencia recibida de%'
							AND m1.id_movimiento <> m2.id_movimiento
							AND m1.fecha BETWEEN ? AND ?
						ORDER BY m1.fecha DESC
						""";
					break;
					
				case "prestamos":
					// Query que trae los datos de prestamos aprobados
					query = """
						SELECT 
							CONCAT(cl.nombre, ' ', cl.apellido) AS 'Cliente',
							c.numero_cuenta AS 'Número de Cuenta',
							p.importe_solicitado AS 'Monto Solicitado',
							p.cantidad_cuotas AS 'Cantidad Cuotas',
							p.importe_por_cuota AS 'Cuota Mensual',
							p.fecha_aprobacion AS 'Fecha Aprobación'
						FROM prestamo p
						JOIN cuenta c ON p.id_cuenta = c.id_cuenta
						JOIN cliente cl ON p.id_cliente = cl.id_cliente
						WHERE p.fecha_aprobacion IS NOT NULL
						  AND p.eliminado = 0
						  AND p.fecha_aprobacion BETWEEN ? AND ?
						ORDER BY p.fecha_aprobacion DESC
						""";
					break;
					
				case "clientes":
					// Query para traer datos de los clientes activos
					query = """
						SELECT 
							CONCAT(cl.nombre, ' ', cl.apellido) AS 'Cliente',
							c.numero_cuenta AS 'Número de Cuenta',
							ct.nombre AS 'Tipo de Cuenta',
							cl.dni AS 'DNI',
							c.saldo AS 'Saldo Actual',
							c.fecha_creacion AS 'Fecha Creación'
						FROM cliente cl
						JOIN cuenta c ON cl.id_cliente = c.id_cliente
						JOIN cuenta_tipo ct ON c.id_tipo_cuenta = ct.id
						WHERE c.activa = 1 
						  AND c.fecha_creacion BETWEEN ? AND ?
						  AND cl.eliminado = 0
						ORDER BY c.fecha_creacion DESC
						""";
					break;
			}

			pst = conexion.prepareStatement(query);
			pst.setDate(1, new java.sql.Date(fechaInicio.getTime()));
			pst.setDate(2, new java.sql.Date(fechaFin.getTime()));

			rs = pst.executeQuery();

			// Procesa los resultados y crea Map con los datos dinamicos
			while (rs.next()) {
				Map<String, Object> fila = new HashMap<>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					String nombreColumna = rs.getMetaData().getColumnLabel(i);
					Object valor = rs.getObject(i);
					fila.put(nombreColumna, valor);
				}
				datos.add(fila);
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

		return datos;
	}

}