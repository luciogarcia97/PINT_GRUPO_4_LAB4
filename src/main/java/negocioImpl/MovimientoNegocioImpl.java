package negocioImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import dao.MovimientoDao;
import daoImpl.MovimientoDaoImpl;
import entidades.Movimiento;
import negocio.MovimientoNegocio;

public class MovimientoNegocioImpl implements MovimientoNegocio {
    
    private MovimientoDao movimientoDao;
    
    public MovimientoNegocioImpl() {
        super();
        this.movimientoDao = new MovimientoDaoImpl();
    }
    
    public MovimientoNegocioImpl(MovimientoDao movimientoDao) {
        this.movimientoDao = movimientoDao;
    }

    @Override
    public boolean insertarMovimiento(Movimiento movimiento) {
        return movimientoDao.insertarMovimiento(movimiento);
    }

    @Override
    public List<Movimiento> obtenerMovimientosPorCuenta(int idCuenta) {
        return movimientoDao.obtenerMovimientosPorCuenta(idCuenta);
    }

    @Override
    public List<Movimiento> obtenerMovimientosPorCuentaYFechas(int idCuenta, LocalDate fechaDesde, LocalDate fechaHasta) {
        return movimientoDao.obtenerMovimientosPorCuentaYFechas(idCuenta, fechaDesde, fechaHasta);
    }

    @Override
    public List<Movimiento> obtenerMovimientosPorCuentaYTipo(int idCuenta, int idTipoMovimiento) {
        return movimientoDao.obtenerMovimientosPorCuentaYTipo(idCuenta, idTipoMovimiento);
    }

    @Override
    public List<Movimiento> obtenerTodosLosMovimientos() {
        return movimientoDao.obtenerTodosLosMovimientos();
    }

    @Override
    public int contarMovimientosPorCuenta(int idCuenta) {
        return movimientoDao.contarMovimientosPorCuenta(idCuenta);
    }
    
    @Override
    public boolean registrarMovimientoTransferencia(int idCuentaOrigen, int idCuentaDestino, BigDecimal monto, String detalle) {
    	// Registro el movimiento negativo en el origen
        Movimiento movimientoOrigen = new Movimiento();
        movimientoOrigen.setIdCuenta(idCuentaOrigen);
        movimientoOrigen.setIdTipoMovimiento(4);
        movimientoOrigen.setFecha(LocalDate.now());
        movimientoOrigen.setDetalle("Transferencia enviada - " + detalle);
        movimientoOrigen.setImporte(monto.negate());
        
        boolean origenExitoso = movimientoDao.insertarMovimiento(movimientoOrigen);
        
        // Registro el movimiento positivo en el destino
        Movimiento movimientoDestino = new Movimiento();
        movimientoDestino.setIdCuenta(idCuentaDestino);
        movimientoDestino.setIdTipoMovimiento(4);
        movimientoDestino.setFecha(LocalDate.now());
        movimientoDestino.setDetalle("Transferencia recibida - " + detalle);
        movimientoDestino.setImporte(monto);
        
        boolean destinoExitoso = movimientoDao.insertarMovimiento(movimientoDestino);
        
        return origenExitoso && destinoExitoso;
    }
    
    @Override
    public boolean registrarMovimientoAltaPrestamo(int idCuenta, BigDecimal monto, int idPrestamo) {
        Movimiento movimiento = new Movimiento();
        movimiento.setIdCuenta(idCuenta);
        movimiento.setIdTipoMovimiento(2);
        movimiento.setFecha(LocalDate.now());
        movimiento.setDetalle("Depósito por préstamo aprobado");
        movimiento.setImporte(monto);
        movimiento.setIdPrestamo(idPrestamo);
        
        return movimientoDao.insertarMovimiento(movimiento);
    }
    
    @Override
    public boolean registrarMovimientoPagoCuota(int idCuenta, BigDecimal monto, int idPrestamo) {
        Movimiento movimiento = new Movimiento();
        movimiento.setIdCuenta(idCuenta);
        movimiento.setIdTipoMovimiento(3);
        movimiento.setFecha(LocalDate.now());
        movimiento.setDetalle("Pago de cuota de préstamo");
        movimiento.setImporte(monto.negate());
        movimiento.setIdPrestamo(idPrestamo);
        
        return movimientoDao.insertarMovimiento(movimiento);
    }
    
    @Override
    public boolean registrarMovimientoAltaCuenta(int idCuenta, BigDecimal montoInicial) {
        Movimiento movimiento = new Movimiento();
        movimiento.setIdCuenta(idCuenta);
        movimiento.setIdTipoMovimiento(1);
        movimiento.setFecha(LocalDate.now());
        movimiento.setDetalle("Depósito inicial por apertura de cuenta");
        movimiento.setImporte(montoInicial);
        
        return movimientoDao.insertarMovimiento(movimiento);
    }
}