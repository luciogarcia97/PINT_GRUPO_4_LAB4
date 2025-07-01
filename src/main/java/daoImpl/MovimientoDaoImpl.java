package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.Conexion;
import dao.MovimientoDao;
import entidades.Movimiento;

public class MovimientoDaoImpl implements MovimientoDao {

    @Override
    public boolean insertarMovimiento(Movimiento movimiento) {
        PreparedStatement pst = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        boolean resultado = false;

        try {
            String query = "INSERT INTO movimiento (id_cuenta, id_tipo_movimiento, fecha, detalle, importe, id_prestamo) VALUES (?, ?, ?, ?, ?, ?)";
            
            pst = conexion.prepareStatement(query);
            pst.setInt(1, movimiento.getIdCuenta());
            pst.setInt(2, movimiento.getIdTipoMovimiento());
            pst.setDate(3, java.sql.Date.valueOf(movimiento.getFecha()));
            pst.setString(4, movimiento.getDetalle());
            pst.setBigDecimal(5, movimiento.getImporte());
            
            if (movimiento.getIdPrestamo() != null) {
                pst.setInt(6, movimiento.getIdPrestamo());
            } else {
                pst.setNull(6, java.sql.Types.INTEGER);
            }

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
    public List<Movimiento> obtenerMovimientosPorCuenta(int idCuenta) {
        List<Movimiento> movimientos = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();

        try {
            String query = "SELECT * FROM movimiento WHERE id_cuenta = ? ORDER BY fecha DESC, id_movimiento DESC";
            pst = conexion.prepareStatement(query);
            pst.setInt(1, idCuenta);
            rs = pst.executeQuery();

            while (rs.next()) {
            	Movimiento aux = mapearMovimiento(rs);
                movimientos.add(aux);
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

        return movimientos;
    }

    @Override
    public List<Movimiento> obtenerMovimientosPorCuentaYFechas(int idCuenta, LocalDate fechaDesde, LocalDate fechaHasta) {
        List<Movimiento> movimientos = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();

        try {
            String query = "SELECT * FROM movimiento WHERE id_cuenta = ? AND fecha BETWEEN ? AND ? ORDER BY fecha DESC, id_movimiento DESC";
            pst = conexion.prepareStatement(query);
            pst.setInt(1, idCuenta);
            pst.setDate(2, java.sql.Date.valueOf(fechaDesde));
            pst.setDate(3, java.sql.Date.valueOf(fechaHasta));
            rs = pst.executeQuery();

            while (rs.next()) {
            	Movimiento aux = mapearMovimiento(rs);
                movimientos.add(aux);
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

        return movimientos;
    }

    @Override
    public List<Movimiento> obtenerMovimientosPorCuentaYTipo(int idCuenta, int idTipoMovimiento) {
        List<Movimiento> movimientos = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();

        try {
            String query = "SELECT * FROM movimiento WHERE id_cuenta = ? AND id_tipo_movimiento = ? ORDER BY fecha DESC, id_movimiento DESC";
            pst = conexion.prepareStatement(query);
            pst.setInt(1, idCuenta);
            pst.setInt(2, idTipoMovimiento);
            rs = pst.executeQuery();

            while (rs.next()) {
            	Movimiento aux = mapearMovimiento(rs);
                movimientos.add(aux);
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

        return movimientos;
    }

    @Override
    public List<Movimiento> obtenerTodosLosMovimientos() {
        List<Movimiento> movimientos = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();

        try {
            String query = "SELECT * FROM movimiento ORDER BY fecha DESC, id_movimiento DESC";
            pst = conexion.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
            	Movimiento aux = mapearMovimiento(rs);
                movimientos.add(aux);
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

        return movimientos;
    }

    @Override
    public Movimiento mapearMovimiento(ResultSet rs) throws SQLException {
        Movimiento movimiento = new Movimiento();
        movimiento.setIdMovimiento(rs.getInt("id_movimiento"));
        movimiento.setIdCuenta(rs.getInt("id_cuenta"));
        movimiento.setIdTipoMovimiento(rs.getInt("id_tipo_movimiento"));
        movimiento.setFecha(rs.getDate("fecha").toLocalDate());
        movimiento.setDetalle(rs.getString("detalle"));
        movimiento.setImporte(rs.getBigDecimal("importe"));
        
        int idPrestamo = rs.getInt("id_prestamo");
        if (!rs.wasNull()) {
            movimiento.setIdPrestamo(idPrestamo);
        }
        
        return movimiento;
    }

    @Override
    public int contarMovimientosPorCuenta(int idCuenta) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        int cantidad = 0;

        try {
            String query = "SELECT COUNT(*) as cantidad FROM movimiento WHERE id_cuenta = ?";
            pst = conexion.prepareStatement(query);
            pst.setInt(1, idCuenta);
            rs = pst.executeQuery();

            if (rs.next()) {
                cantidad = rs.getInt("cantidad");
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

        return cantidad;
    }
}