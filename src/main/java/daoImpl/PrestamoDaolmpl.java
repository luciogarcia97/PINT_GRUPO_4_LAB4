package daoImpl;

import dao.Conexion;
import dao.PrestamoDao;

import entidades.Prestamo;
import entidades.PrestamoCuota;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import com.mysql.jdbc.CallableStatement;

public class PrestamoDaolmpl implements PrestamoDao {	

	@Override
	public boolean insertar(Prestamo prestamo) {
		boolean estado = false;
		Connection cn = null;
		PreparedStatement ps = null;

		try {
			cn = Conexion.getConexion().getSQLConexion();
			String query = "INSERT INTO prestamo (id_cliente, id_cuenta, fecha_solicitud, importe_solicitado, plazo_pago_meses, importe_por_cuota, cantidad_cuotas, estado, eliminado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = cn.prepareStatement(query);
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
			} else {
				cn.rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (cn != null)
					cn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				if (ps != null)
					ps.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return estado;
	}	
	
	@Override
	public List<Prestamo> obtenerPrestamos() {
	    List<Prestamo> listaPrestamos = new ArrayList<>();
	    Connection cn = null;
	    PreparedStatement pst = null;
	    ResultSet rs = null;

	    try {
	        cn = Conexion.getConexion().getSQLConexion();
	        String query = "SELECT id_prestamo, id_cliente, id_cuenta, fecha_solicitud, importe_solicitado, plazo_pago_meses, importe_por_cuota, cantidad_cuotas, estado, eliminado FROM prestamo";
	        pst = cn.prepareStatement(query);
	        rs = pst.executeQuery();

	        while (rs.next()) {
	            Prestamo prestamo = new Prestamo();
	            prestamo.setId_prestamo(rs.getInt("id_prestamo"));
	            prestamo.setId_cliente(rs.getInt("id_cliente"));
	            prestamo.setId_cuenta(rs.getInt("id_cuenta"));
	            prestamo.setFecha_solicitud(rs.getDate("fecha_solicitud").toLocalDate()); 
	            prestamo.setImporte_solicitado(rs.getDouble("importe_solicitado"));
	            prestamo.setPlazo_pago_mes(rs.getInt("plazo_pago_meses"));
	            prestamo.setImporte_por_cuota(rs.getDouble("importe_por_cuota"));
	            prestamo.setCantidad_cuotas(rs.getInt("cantidad_cuotas"));
	            prestamo.setEstado(rs.getString("estado"));
	            prestamo.setEliminado(rs.getBoolean("eliminado"));
	            listaPrestamos.add(prestamo);
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

	    return listaPrestamos;
	}
	
	@Override
	public boolean denegarPrestamo(int idPrestamo) {
	    boolean resultado = false;
	    Connection cn = null;
	    PreparedStatement pst = null;

	    try {
	        cn = Conexion.getConexion().getSQLConexion();
	        String query = "UPDATE prestamo SET estado = ? WHERE id_prestamo = ?";
	        pst = cn.prepareStatement(query);
	        pst.setString(1, "denegado");
	        pst.setInt(2, idPrestamo);

	        if (pst.executeUpdate() > 0) {
	            cn.commit();
	            resultado = true;
	        } else {
	            cn.rollback();
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            if (cn != null) cn.rollback();
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
	public boolean aceptarPrestamo(int idPrestamo) {
	    Connection cn = null;
	    CallableStatement cs = null;
	    boolean resultado = false;

	    try {
	        cn = Conexion.getConexion().getSQLConexion();
	        String call = "{CALL aceptar_prestamo_valida_cliente_activo(?, ?)}";
	        cs = (CallableStatement) cn.prepareCall(call);
	        cs.setInt(1, idPrestamo);
	        cs.registerOutParameter(2, java.sql.Types.INTEGER);

	        cs.execute();
	        int estado = cs.getInt(2); // 0 = no aceptado, 1 = aceptado
	        System.out.println("Estado devuelto por el procedimiento: " + estado);
	        if (estado == 1) {
	            cn.commit();
	            resultado = true;
	        } else {
	            cn.rollback(); 
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            if (cn != null) cn.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    } finally {
	        try {
	            if (cs != null) cs.close();	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return resultado;
	}	
	
	@Override
	public Prestamo obtenerPrestamoID(int idPrestamo) {
	    Prestamo prestamo = null;
	    Connection cn = null;
	    PreparedStatement pst = null;
	    ResultSet rs = null;

	    try {
	        cn = Conexion.getConexion().getSQLConexion();
	        String query = "SELECT id_prestamo, id_cliente, id_cuenta, fecha_solicitud, importe_solicitado, plazo_pago_meses, importe_por_cuota, cantidad_cuotas, estado, eliminado FROM prestamo WHERE id_prestamo = ?";
	        pst = cn.prepareStatement(query);
	        pst.setInt(1, idPrestamo);

	        rs = pst.executeQuery();

	        if (rs.next()) {
	            prestamo = new Prestamo();
	            prestamo.setId_prestamo(rs.getInt("id_prestamo"));
	            prestamo.setId_cliente(rs.getInt("id_cliente"));
	            prestamo.setId_cuenta(rs.getInt("id_cuenta"));
	            prestamo.setFecha_solicitud(rs.getDate("fecha_solicitud").toLocalDate());
	            prestamo.setImporte_solicitado(rs.getInt("importe_solicitado"));
	            prestamo.setPlazo_pago_mes(rs.getInt("plazo_pago_meses"));
	            prestamo.setImporte_por_cuota(rs.getInt("importe_por_cuota"));
	            prestamo.setCantidad_cuotas(rs.getInt("cantidad_cuotas"));
	            prestamo.setEstado(rs.getString("estado"));
	            prestamo.setEliminado(rs.getBoolean("eliminado"));
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

	    return prestamo;
	}
	
	
	
	

	@Override
	public boolean impactar_prestamo_cuenta(int idCuenta, double dinero) {
		PreparedStatement pst = null;
		Connection cn = null;
		
		boolean resultado = false;
		try {
			cn = Conexion.getConexion().getSQLConexion();
			String query = "UPDATE cuenta SET saldo = saldo + ? WHERE id_cuenta = ?";
			pst = cn.prepareStatement(query);
			pst.setDouble(1, dinero);
			pst.setInt(2, idCuenta);

			if (pst.executeUpdate() > 0) {
				cn.commit();
				resultado = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				cn.rollback();
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
	public List<PrestamoCuota> obtenerCuotas(int idPrestamo) {
		List<PrestamoCuota> cuotas = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection cn = null;

		try {
			cn = Conexion.getConexion().getSQLConexion();
			String query = "SELECT id_prestamo_cuota,id_prestamo,numero_cuota,monto,"
					      + "fecha_vencimiento,fecha_pago, pagada "
					      + "FROM prestamo_cuota WHERE id_prestamo = ? and pagada = 0";
			
			pst = cn.prepareStatement(query);
			pst.setInt(1, idPrestamo);

			rs = pst.executeQuery();

			while (rs.next()) {
				PrestamoCuota cuota = new PrestamoCuota();

				cuota.setIdCuota(rs.getInt("id_prestamo_cuota"));
				cuota.setIdPrestamo(rs.getInt("id_prestamo"));
				cuota.setNumCuota(rs.getInt("numero_cuota"));
				cuota.setMonto(rs.getDouble("monto"));
				cuota.setFechaVencimiento(rs.getObject("fecha_vencimiento", LocalDate.class));
				cuota.setFechaPago(rs.getObject("fecha_pago", LocalDate.class));
				cuota.setPagado(rs.getBoolean("pagada"));

				cuotas.add(cuota);

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

		return cuotas;
	}

	@Override
	public Prestamo obtenerPrestamoIDCuenta(int idCliente) {

		Prestamo prestamo = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection cn = null;

		try {
			cn = Conexion.getConexion().getSQLConexion();
			String query = "SELECT id_prestamo, id_cliente, id_cuenta, fecha_solicitud, importe_solicitado, plazo_pago_meses, importe_por_cuota, cantidad_cuotas, estado, eliminado FROM prestamo WHERE id_cliente = ? AND eliminado =0 ";
			
			pst = cn.prepareStatement(query);
			pst.setInt(1, idCliente);

			rs = pst.executeQuery();

			if (rs.next()) {
				prestamo = new Prestamo();
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

		return prestamo;
	}

	@Override
	public boolean pagarCuota(int idCuota, int idCuenta, double monto) {
		PreparedStatement pst = null;
		Connection cn = null;
		boolean resultado = false;
		
		try {
			cn = Conexion.getConexion().getSQLConexion();
			String query = "CALL sp_pagar_cuota(?, ?, ?)";
			pst = cn.prepareStatement(query);
			pst.setInt(1, idCuota);
			pst.setInt(2, idCuenta);
			pst.setDouble(3, monto);

			pst.executeUpdate();
			cn.commit();
			resultado = true;

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				cn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (pst != null)
					pst.close();
				if (cn != null) {
					cn.commit();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return resultado;
	}
	
	@Override
	public Prestamo obtenerPrestamoPorIdCuota(int idCuota) {
		Prestamo prestamo = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection cn = null;

		try {
			cn = Conexion.getConexion().getSQLConexion();
			String query = "select id_prestamo from prestamo_cuota pc where pc.id_prestamo_cuota = ?";
			pst = cn.prepareStatement(query);
			pst.setInt(1, idCuota);

			rs = pst.executeQuery();

			if (rs.next()) {
				prestamo = obtenerPrestamoID(rs.getInt("id_prestamo"));
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

		return prestamo;
	}
	
	@Override
	public boolean generarCuotasPrestamo(int idPrestamo, int cantidadCuotas, double montoPorCuota) {
	    PreparedStatement pst = null;
	    boolean resultado = false;
	    Connection cn = null;
	    
	    try {
	    	cn = Conexion.getConexion().getSQLConexion();
	        String query = "INSERT INTO prestamo_cuota (id_prestamo, numero_cuota, monto, fecha_vencimiento, pagada) VALUES (?, ?, ?, ?, 0)";
	        pst = cn.prepareStatement(query);
	        
	        // Hago un insert en lote
	        for (int i = 1; i <= cantidadCuotas; i++) {
	            pst.setInt(1, idPrestamo);
	            pst.setInt(2, i);
	            pst.setDouble(3, montoPorCuota);
	            
	            // Fecha de vencimiento: cada mes a partir de hoy
	            LocalDate fechaVencimiento = LocalDate.now().plusMonths(i);
	            pst.setDate(4, Date.valueOf(fechaVencimiento));
	            
	            pst.addBatch();
	        }
	        
	        int[] resultados = pst.executeBatch(); // Ejecutar todas las inserciones
	        
	        // Verificar que todas las cuotas se insertaron correctamente
	        if (resultados.length == cantidadCuotas) {
	            cn.commit();
	            resultado = true;
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            cn.rollback();
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
	
	public List<Prestamo> obtenerPrestamosConCuotasPendientes(int idCliente){
		List<Prestamo> prestamos = new ArrayList<>();
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    Connection cn = null;
	    
	    try {
	        cn = Conexion.getConexion().getSQLConexion();
	        String query = """
	            select 
					p.id_prestamo, 
					p.id_cliente,
					p.id_cuenta,
					p.fecha_solicitud,
					p.fecha_aprobacion,
					p.importe_solicitado, 
					p.plazo_pago_meses, p.importe_por_cuota, p.cantidad_cuotas, 
					p.estado,
					p.eliminado
				from prestamo p 
				inner join prestamo_cuota pc on p.id_prestamo = pc.id_prestamo 
				where p.id_cliente = ? 
				      and p.fecha_aprobacion is not null
				      and p.eliminado = 0 
				      and pc.pagada = 0
				  group by id_prestamo
				    order by p.fecha_aprobacion;
		    """;
	            
	        pst = cn.prepareStatement(query);
	        pst.setInt(1, idCliente);
	        rs = pst.executeQuery();
	        
	        while (rs.next()) {
	            Prestamo prestamo = new Prestamo();
	            prestamo.setId_prestamo(rs.getInt("id_prestamo"));
	            prestamo.setId_cliente(rs.getInt("id_cliente"));
	            prestamo.setId_cuenta(rs.getInt("id_cuenta"));
	            prestamo.setFecha_solicitud(rs.getObject("fecha_solicitud", LocalDate.class));
	            prestamo.setFecha_aprobacion(rs.getObject("fecha_aprobacion", LocalDate.class));
	            prestamo.setImporte_solicitado(rs.getDouble("importe_solicitado"));
	            prestamo.setPlazo_pago_mes(rs.getInt("plazo_pago_meses"));
	            prestamo.setImporte_por_cuota(rs.getDouble("importe_por_cuota"));
	            prestamo.setCantidad_cuotas(rs.getInt("cantidad_cuotas"));
	            prestamo.setEstado(rs.getString("estado"));
	            prestamo.setEliminado(rs.getBoolean("eliminado"));
	            
	            prestamos.add(prestamo);
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
	    
	    return prestamos;
	}

}
