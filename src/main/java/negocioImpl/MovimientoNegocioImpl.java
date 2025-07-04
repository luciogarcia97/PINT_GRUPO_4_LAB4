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

}