package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entidades.TipoCuenta;

public class TipoCuentaDao {
    
    public List<TipoCuenta> obtenerTiposCuenta() {
        List<TipoCuenta> listaTipos = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        
        try {
            String query = "SELECT id, nombre FROM cuenta_tipo ORDER BY id";
            pst = conexion.prepareStatement(query);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                TipoCuenta tipo = new TipoCuenta();
                tipo.setId(rs.getInt("id"));
                tipo.setNombre(rs.getString("nombre"));
                listaTipos.add(tipo);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return listaTipos;
    }
    
    public TipoCuenta obtenerTipoPorId(int id) {
        TipoCuenta tipo = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conexion = Conexion.getConexion().getSQLConexion();
        
        try {
            String query = "SELECT id, nombre FROM cuenta_tipo WHERE id = ?";
            pst = conexion.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                tipo = new TipoCuenta();
                tipo.setId(rs.getInt("id"));
                tipo.setNombre(rs.getString("nombre"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return tipo;
    }
}