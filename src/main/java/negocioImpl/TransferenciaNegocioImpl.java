package negocioImpl;

import java.util.List;

import dao.TransferenciaDao;
import daoImpl.TransferenciaDaoImpl;
import entidades.Movimiento;
import entidades.Transferencia;
import negocio.TransferenciaNegocio;

public class TransferenciaNegocioImpl implements TransferenciaNegocio {
	
	private TransferenciaDao transferenciaDao;
	
	  public TransferenciaNegocioImpl() {
	        this.transferenciaDao = new TransferenciaDaoImpl(); // ← Agregar esta línea
	    }

	@Override
	public boolean registrar_transferencia(int id_cuenta_origen,int id_cuenta_destino,int id_movimiento) {
		
		return transferenciaDao.registrar_transferencia(id_cuenta_origen,id_cuenta_destino,id_movimiento);
	}

	@Override
	public List<Transferencia> mostrar_historial_transferencia(int id_Cuenta) {
		
		return transferenciaDao.mostrar_historial_transferencia(id_Cuenta);
	}

	@Override
	public Movimiento obtener_movimiento_idCuentaOrigen(int id_cuenta_origen) {
		
		return transferenciaDao.obtener_movimiento_idCuentaOrigen(id_cuenta_origen);
	}

}
