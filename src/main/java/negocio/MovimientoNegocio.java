package negocio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import entidades.Movimiento;

public interface MovimientoNegocio {
    
    public boolean insertarMovimiento(Movimiento movimiento);
    
    public List<Movimiento> obtenerMovimientosPorCuenta(int idCuenta);
    
    public List<Movimiento> obtenerMovimientosPorCuentaYFechas(int idCuenta, LocalDate fechaDesde, LocalDate fechaHasta);
    
    public List<Movimiento> obtenerMovimientosPorCuentaYTipo(int idCuenta, int idTipoMovimiento);
    
    public List<Movimiento> obtenerTodosLosMovimientos();
    
    public int contarMovimientosPorCuenta(int idCuenta);
    
    boolean registrarMovimientoTransferencia(int idCuentaOrigen, int idCuentaDestino, BigDecimal monto, String detalle);
    
    boolean registrarMovimientoAltaPrestamo(int idCuenta, BigDecimal monto, int idPrestamo);
    
    boolean registrarMovimientoPagoCuota(int idCuenta, BigDecimal monto, int idPrestamo);
    
    boolean registrarMovimientoAltaCuenta(int idCuenta, BigDecimal montoInicial);
        
}
