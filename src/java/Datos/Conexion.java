package Datos;

import java.sql.Connection;
import java.sql.Statement;

public class Conexion {
/*Estado*/
    private Statement stmt;
    private Connection conexion; 
/*Estado*/

/*Constructores*/
public Conexion(Statement stmt, Connection conexion){
    this.stmt = stmt;
    this.conexion = conexion;
}
/*Constructores*/

/*Comportamiento*/

/*Comportamiento*/

/*Getters y Setters*/
 public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
/*Getters y Setters*/

   
}
