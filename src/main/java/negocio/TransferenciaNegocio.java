package negocio;

import java.util.List;

import entidades.Movimiento;
import entidades.Transferencia;

public interface TransferenciaNegocio {
	
	boolean registrar_transferencia(int id_cuenta_origen,int id_cuenta_destino,int id_movimiento);
	
	List<Transferencia> mostrar_historial_transferencia(int id_Cuenta);
	
	Movimiento obtener_movimiento_idCuentaOrigen(int id_cuenta_origen);

}
