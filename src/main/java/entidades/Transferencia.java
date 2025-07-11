package entidades;

import java.time.LocalDate;

public class Transferencia {

	private int id_tansferencia;
	
	private int id_movimiento;
	
	private int cuenta_origen;
	
	private int cuenta_destino;
	
	private LocalDate fecha;
	
	private String detalle;
	
	private double importe;

	public Transferencia(int id_tansferencia, int id_movimiento, int cuenta_origen, int cuenta_destino, LocalDate fecha,
			String detalle, double importe) {
		super();
		this.id_tansferencia = id_tansferencia;
		this.id_movimiento = id_movimiento;
		this.cuenta_origen = cuenta_origen;
		this.cuenta_destino = cuenta_destino;
		this.fecha = fecha;
		this.detalle = detalle;
		this.importe = importe;
	}
	
	public Transferencia() {}

	public int getId_tansferencia() {
		return id_tansferencia;
	}

	public void setId_tansferencia(int id_tansferencia) {
		this.id_tansferencia = id_tansferencia;
	}

	public int getId_movimiento() {
		return id_movimiento;
	}

	public void setId_movimiento(int id_movimiento) {
		this.id_movimiento = id_movimiento;
	}

	public int getCuenta_origen() {
		return cuenta_origen;
	}

	public void setCuenta_origen(int cuenta_origen) {
		this.cuenta_origen = cuenta_origen;
	}

	public int getCuenta_destino() {
		return cuenta_destino;
	}

	public void setCuenta_destino(int cuenta_destino) {
		this.cuenta_destino = cuenta_destino;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	@Override
	public String toString() {
		return "Transferencia [id_tansferencia=" + id_tansferencia + ", id_movimiento=" + id_movimiento
				+ ", cuenta_origen=" + cuenta_origen + ", cuenta_destino=" + cuenta_destino + ", fecha=" + fecha
				+ ", detalle=" + detalle + ", importe=" + importe + "]";
	}

	
}
