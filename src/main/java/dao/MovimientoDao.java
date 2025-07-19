package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import entidades.Movimiento;

public interface MovimientoDao {
    
    public boolean insertarMovimiento(Movimiento movimiento);

    public List<Movimiento> obtenerMovimientosPorCuenta(int idCuenta);
    
    public List<Movimiento> obtenerMovimientosPorCuentaYFechas(int idCuenta, LocalDate fechaDesde, LocalDate fechaHasta);
    
    public List<Movimiento> obtenerMovimientosPorCuentaYTipo(int idCuenta, int idTipoMovimiento);
    
    public List<Movimiento> obtenerTodosLosMovimientos();

    public Movimiento mapearMovimiento(ResultSet rs) throws SQLException;

    public int contarMovimientosPorCuenta(int idCuenta);
}