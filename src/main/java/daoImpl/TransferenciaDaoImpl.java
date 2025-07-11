package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.Conexion;
import dao.TransferenciaDao;

import entidades.Movimiento;
import entidades.Transferencia;

public class TransferenciaDaoImpl implements TransferenciaDao {

	@Override
	public boolean registrar_transferencia(int id_cuenta_origen ,int id_cuenta_destino,int id_movimiento) {
		
		PreparedStatement pst = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean resultado = false;

		try {
			String query = "INSERT INTO transferencia (id_movimiento, cuenta_origen, cuenta_destino) VALUES (?, ?, ?)";

			pst = conexion.prepareStatement(query);
			pst.setInt(1, id_movimiento);
			pst.setInt(2, id_cuenta_origen);
			pst.setInt(3, id_cuenta_destino);
		
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
	public List<Transferencia> mostrar_historial_transferencia(int id_Cliente) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		Transferencia transferencia = null;
		List<Transferencia> listaTransferencias= new ArrayList<Transferencia>();

		try {
			String query = "call sp_historial_transferencias_cliente(?)";
			pst = conexion.prepareStatement(query);
			pst.setInt(1, id_Cliente);
			rs = pst.executeQuery();

			while (rs.next()) {
				transferencia = new Transferencia();
				
				
				   transferencia.setId_tansferencia(rs.getInt("id_transferencia"));
		            transferencia.setId_movimiento(rs.getInt("id_movimiento"));
		            transferencia.setCuenta_origen(rs.getInt("cuenta_origen"));
		            transferencia.setCuenta_destino(rs.getInt("cuenta_destino"));
		            
	
		            LocalDate fecha = rs.getDate("fecha").toLocalDate();
		            if (fecha != null) {
		                transferencia.setFecha(fecha);
		            }
		            
		            transferencia.setDetalle(rs.getString("detalle"));
		            transferencia.setImporte(rs.getDouble("importe"));
		         
		           
		            
		            
		            listaTransferencias.add(transferencia);

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

		return listaTransferencias;
	
	}

	@Override
	public Movimiento obtener_movimiento_idCuentaOrigen(int id_cuenta_origen) {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		
		Movimiento movimiento= null;

		try {
			String query = "SELECT id_movimiento, id_cuenta, id_tipo_movimiento, fecha, detalle, importe FROM movimiento WHERE id_cuenta = ?  and id_tipo_movimiento = 4 ORDER BY id_movimiento DESC LIMIT 1";
			pst = conexion.prepareStatement(query);
			pst.setInt(1, id_cuenta_origen);
			rs = pst.executeQuery();

			if (rs.next()) {
				movimiento = new Movimiento();
				movimiento.setIdMovimiento(rs.getInt("id_movimiento"));
				movimiento.setIdCuenta(rs.getInt("id_cuenta"));
				movimiento.setIdTipoMovimiento(rs.getInt("id_tipo_movimiento"));
				movimiento.setFecha((LocalDate)rs.getDate("fecha").toLocalDate());
				movimiento.setDetalle(rs.getString("detalle"));
				movimiento.setImporte(rs.getBigDecimal("importe"));

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

		return movimiento;
	}

}
