package entidades;

import java.time.LocalDate;

public class PrestamoCuota {
	private int idCuota;
	private int idPrestamo;
	private int numCuota;
	private Double monto;
	private LocalDate fechaVencimiento;
	private LocalDate fechaPago;
	private boolean pagado;
	
	public PrestamoCuota(int idCuota, int idPrestamo, int numCuota, Double monto, LocalDate fechaVencimiento,
			LocalDate fechaPago, boolean pagado) {
		super();
		this.idCuota = idCuota;
		this.idPrestamo = idPrestamo;
		this.numCuota = numCuota;
		this.monto = monto;
		this.fechaVencimiento = fechaVencimiento;
		this.fechaPago = fechaPago;
		this.pagado = pagado;
	}
	
	public PrestamoCuota() {}

	public int getIdCuota() {
		return idCuota;
	}

	public void setIdCuota(int idCuota) {
		this.idCuota = idCuota;
	}

	public int getIdPrestamo() {
		return idPrestamo;
	}

	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public int getNumCuota() {
		return numCuota;
	}

	public void setNumCuota(int numCuota) {
		this.numCuota = numCuota;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public LocalDate getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	@Override
	public String toString() {
		return "PrestamoCuota [idCuota=" + idCuota + ", idPrestamo=" + idPrestamo + ", numCuota=" + numCuota
				+ ", monto=" + monto + ", fechaVencimiento=" + fechaVencimiento + ", fechaPago=" + fechaPago
				+ ", pagado=" + pagado + "]";
	}
	
	
	
	
	

}
