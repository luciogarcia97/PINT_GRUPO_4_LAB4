package entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Cuenta {
	private int idCuenta;
    private int idCliente;
    private LocalDate fechaCreacion;
    private int idTipoCuenta;
    private String numeroCuenta;
    private String cbu;
    private BigDecimal saldo;
    private boolean activa;
    
    public Cuenta() {
        this.saldo = new BigDecimal("10000.00");
        this.activa = true;
        this.fechaCreacion = LocalDate.now();
    }
	
    public Cuenta(int idCliente, int idTipoCuenta, String numeroCuenta, String cbu) {
        this();
        this.idCliente = idCliente;
        this.idTipoCuenta = idTipoCuenta;
        this.numeroCuenta = numeroCuenta;
        this.cbu = cbu;
    }

	public int getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(int idCuenta) {
		this.idCuenta = idCuenta;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public int getIdTipoCuenta() {
		return idTipoCuenta;
	}

	public void setIdTipoCuenta(int idTipoCuenta) {
		this.idTipoCuenta = idTipoCuenta;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getCbu() {
		return cbu;
	}

	public void setCbu(String cbu) {
		this.cbu = cbu;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	@Override
	public String toString() {
		return "Cuenta{" +
                "idCuenta=" + idCuenta +
                ", idCliente=" + idCliente +
                ", fechaCreacion=" + fechaCreacion +
                ", idTipoCuenta=" + idTipoCuenta +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", cbu='" + cbu + '\'' +
                ", saldo=" + saldo +
                ", activa=" + activa +
                '}';
	}
    
    
}
