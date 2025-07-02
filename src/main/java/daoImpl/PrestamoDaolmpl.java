package daoImpl;

import dao.Conexion;
import dao.PrestamoDao;

import entidades.Prestamo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;


public class PrestamoDaolmpl implements PrestamoDao {

	  public boolean insertar(Prestamo prestamo) {
	        boolean estado = false;
	        try {
	        	Connection cn = Conexion.getConexion().getSQLConexion();
	            String query = "INSERT INTO prestamo (id_cliente, id_cuenta, fecha_solicitud, importe_solicitado, plazo_pago_meses, importe_por_cuota, cantidad_cuotas, estado, eliminado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	            PreparedStatement ps = cn.prepareStatement(query);
	            ps.setInt(1, prestamo.getId_cliente());
	            ps.setInt(2, prestamo.getId_cuenta());
	            ps.setDate(3, Date.valueOf(prestamo.getFecha_solicitud()));
	            ps.setDouble(4, prestamo.getImporte_solicitado());
	            ps.setInt(5, prestamo.getPlazo_pago_mes());
	            ps.setDouble(6, prestamo.getImporte_por_cuota());
	            ps.setInt(7, prestamo.getCantidad_cuotas());
	            ps.setString(8, "pendiente");
	            ps.setBoolean(9, false);

	            estado = ps.executeUpdate() > 0;
	            if (estado) {
	                cn.commit(); 
	            }
	            ps.close();
	            cn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return estado;
	    }
	  
	  @Override
		public List<Prestamo> obtenerPrestamos() {
			List<Prestamo> listaPrestamos = new ArrayList<>();
			PreparedStatement pst = null;
			ResultSet rs = null;
			Connection conexion = Conexion.getConexion().getSQLConexion();

			try {
				String query = "SELECT id_prestamo, id_cliente, id_cuenta, fecha_solicitud, importe_solicitado, plazo_pago_meses, importe_por_cuota, cantidad_cuotas, estado, eliminado FROM prestamo";
				pst = conexion.prepareStatement(query);
				rs = pst.executeQuery();

				while (rs.next()) {

					Prestamo prestamo = new Prestamo();
					prestamo.setId_prestamo(rs.getInt("id_prestamo"));
					prestamo.setId_cliente(rs.getInt("id_cliente"));
					prestamo.setId_cuenta(rs.getInt("id_cuenta"));
					prestamo.setFecha_solicitud(LocalDate.now());
					prestamo.setImporte_solicitado(rs.getInt("importe_solicitado"));
					prestamo.setPlazo_pago_mes(rs.getInt("plazo_pago_meses"));
					prestamo.setImporte_por_cuota(rs.getInt("importe_por_cuota"));
					prestamo.setCantidad_cuotas(rs.getInt("cantidad_cuotas"));
					prestamo.setEstado(rs.getString("estado"));
					prestamo.setEliminado(rs.getBoolean("eliminado"));
					listaPrestamos.add(prestamo);
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

			return listaPrestamos;
		}

	 

	  @Override
	  public boolean denegarPrestamo(int idPrestamo) {
		  PreparedStatement pst = null;
			Connection conexion = Conexion.getConexion().getSQLConexion();
			boolean resultado = false;		
			try {
				String query = "UPDATE prestamo SET estado = ? WHERE id_prestamo = ?";
				pst = conexion.prepareStatement(query);
				pst.setString(1, "denegado");
				pst.setInt(2, idPrestamo);
				
				

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
	  public boolean aceptarPrestamo(int idPrestamo) {
		  PreparedStatement pst = null;
			Connection conexion = Conexion.getConexion().getSQLConexion();
			boolean resultado = false;		
			try {
				String query = "UPDATE prestamo SET estado = ? WHERE id_prestamo = ?";
				pst = conexion.prepareStatement(query);
				pst.setString(1, "aceptado");
				pst.setInt(2, idPrestamo);
				
				

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

	
}
