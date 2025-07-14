package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public static Conexion instancia;
    private Connection connection;
    
    private Conexion() {
        try {
        	
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(
<<<<<<< HEAD
            		"jdbc:mysql://localhost:3306/banco_db_sql2?useSSL=false","root","root");
=======
            		"jdbc:mysql://localhost:3306/banco_db?useSSL=false","root","root");
>>>>>>> d5ca29c8042c36174be3c7cfb1261b9ea98326be
            
            this.connection.setAutoCommit(false);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Conexion getConexion() {
        if(instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }
    
    public Connection getSQLConexion() {
        return this.connection;
    }
    
    public void cerrarConexion() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        instancia = null;
    }
}