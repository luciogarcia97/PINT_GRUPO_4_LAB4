package entidades;

public class Reporte {
	
	private Double sumaDepositos;
	private int transferenciasRealizadas;	
	private int prestamosOtorgados;
	private int clientesActivos;	
	
	public Reporte() {
		super();
	}

	public Reporte(Double sumaDepositos, int transferenciasRealizadas,int prestamosOtorgados,
			int clientesActivos) {
		super();
		this.sumaDepositos = sumaDepositos;
		this.transferenciasRealizadas = transferenciasRealizadas;		
		this.prestamosOtorgados = prestamosOtorgados;
		this.clientesActivos = clientesActivos;
	}

	public Double getSumaDepositos() {
		return sumaDepositos;
	}

	public void setSumaDepositos(Double sumaDepositos) {
		this.sumaDepositos = sumaDepositos;
	}

	public int getTransferenciasRealizadas() {
		return transferenciasRealizadas;
	}

	public void setTransferenciasRealizadas(int transferenciasRealizadas) {
		this.transferenciasRealizadas = transferenciasRealizadas;
	}

	public int getPrestamosOtorgados() {
		return prestamosOtorgados;
	}

	public void setPrestamosOtorgados(int prestamosOtorgados) {
		this.prestamosOtorgados = prestamosOtorgados;
	}

	public int getClientesActivos() {
		return clientesActivos;
	}

	public void setClientesActivos(int clientesActivos) {
		this.clientesActivos = clientesActivos;
	}
	

}
