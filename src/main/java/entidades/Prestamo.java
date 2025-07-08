package entidades;

import java.time.LocalDate;

public class Prestamo {
	private int id_prestamo;
	private int id_cliente;
	private int id_cuenta;
	private LocalDate fecha_solicitud;
	private LocalDate fecha_aprobacion;
	private double importe_solicitado;
	private int plazo_pago_mes;
	private double importe_por_cuota;
	private int cantidad_cuotas;	
	private String estado;
	private boolean eliminado;
	
	
	
	public int getId_prestamo() {
		return id_prestamo;
	}
	public void setId_prestamo(int id_prestamo) {
		this.id_prestamo = id_prestamo;
	}
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public int getId_cuenta() {
		return id_cuenta;
	}
	public void setId_cuenta(int id_cuenta) {
		this.id_cuenta = id_cuenta;
	}
	public LocalDate getFecha_solicitud() {
		return fecha_solicitud;
	}
	public void setFecha_solicitud(LocalDate localDate) {
		this.fecha_solicitud = localDate;
	}
	public LocalDate getFecha_aprobacion() {
		return fecha_aprobacion;
	}
	public void setFecha_aprobacion(LocalDate fecha_aprobacion) {
		this.fecha_aprobacion = fecha_aprobacion;
	}
	public double getImporte_solicitado() {
		return importe_solicitado;
	}
	public void setImporte_solicitado(double monto) {
		this.importe_solicitado = monto;
	}
	public int getPlazo_pago_mes() {
		return plazo_pago_mes;
	}
	public void setPlazo_pago_mes(int plazo_pago_mes) {
		this.plazo_pago_mes = plazo_pago_mes;
	}
	public double getImporte_por_cuota() {
		return importe_por_cuota;
	}
	public void setImporte_por_cuota(double d) {
		this.importe_por_cuota = d;
	}
	public int getCantidad_cuotas() {
		return cantidad_cuotas;
	}
	public void setCantidad_cuotas(int cantidad_cuotas) {
		this.cantidad_cuotas = cantidad_cuotas;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public boolean isEliminado() {
		return eliminado;
	}
	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}
	
	
	
}
