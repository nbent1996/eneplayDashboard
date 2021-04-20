package Datos;

import Modelo.Imagen;
import java.sql.*;
import java.util.ArrayList;
import javax.naming.NamingException;

public class Database {

    /*Atributos*/
    private static Database instancia;
    private static Connection conexion;
    private static Statement stmt;
    private String modo = "glassfish"; /*local para testing local -- glassfish para testear desde capa de presentación*/
    
    /*UTILIZAR ESTAS 3 LINEAS PARA TESTING CON JUNIT*/
     //private static String user = "root";
     //private static String pass = "48283674";
     //private static String url = "jdbc:mysql://localhost:3306/alfacomPlatform"+"?user="+user+"&password="+pass+"&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    /*UTILIZAR ESTAS 3 LINEAS PARA TESTING CON JUNIT*/
    
     /*UTILIZAR ESTAS 3 LINEAS PARA PRODUCCION(QUE SE PUEDA CONSUMIR EL WEBSERVCE)*/
     private static String user = "root";
     private static String pass = "alfacom48282020!";
     private static String url = "jdbc:mysql://alfacomplatform.cx3teiukxfae.us-east-1.rds.amazonaws.com:3306/alfacomPlatform"+"?user="+user+"&password="+pass+"&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
     /*UTILIZAR ESTAS 3 LINEAS PARA PRODUCCION(QUE SE PUEDA CONSUMIR EL WEBSERVCE)*/
     
    /*Atributos*/
 /*Constructores*/

 /*Constructores*/
 /*Comportamiento*/
    public static Database getInstancia() {
        if (instancia == null) {
            instancia = new Database();
        }
        return instancia;
    }

    public void conectar(String url) throws SQLException, NamingException {
        try {
            switch(this.modo){
                case "local":
                    conexion = DriverManager.getConnection(url);
                break;
                case "glassfish":
                    javax.naming.InitialContext ctx = new javax.naming.InitialContext();
                    javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("jdbc/alfacom");
                    conexion = ds.getConnection();
                    break;

            }

            conexion.setAutoCommit(false);
            stmt = conexion.createStatement();
        } catch (SQLException ex) {
            int codigo = ex.getErrorCode();
            String errorTexto = "Codigo de Error: " + codigo + " // Mensaje: " + ex.getMessage();
            System.out.println(errorTexto);
            if (codigo == 0) {
                throw new SQLException(errorTexto);
            }
        }
    }

    public void desconectar() {
        try {
            if (conexion != null) {
                conexion.close();
                conexion=null;
                stmt=null;
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int actualizar(String sql) throws SQLException, NamingException {
        try {
            conectar(url);
            return stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        }
    }

    public boolean actualizarMultiple(ArrayList<String> sql, String modoQuery) throws SQLException, NamingException {
        conectar(url);
        String error = "";
        int idGenerado = -1;
        conexion.setAutoCommit(false);
        for (int i = 0; i <= sql.size() - 1; i++) {
            String sentencia = sql.get(i);
            try {
                if (modoQuery.equals("INSERT")) {
                    if (!sentencia.contains("?") && !"".equals(sentencia)) {
                        PreparedStatement psConId = conexion.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
                        psConId.executeUpdate();
                        ResultSet generatedKeys = psConId.getGeneratedKeys();
                        generatedKeys.next();
                        idGenerado = generatedKeys.getInt(1);
                    } else {
                        PreparedStatement ps = conexion.prepareStatement(sentencia);
                        ps.setInt(1, idGenerado);
                        ps.executeUpdate();
                    }
                }
                if (modoQuery.equals("UPDATE") || modoQuery.equals("DELETE")) {
                    stmt.executeUpdate(sentencia);
                }
            } catch (SQLException ex) {
                conexion.rollback();
                conectar(url);
                throw ex;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw ex;
            }
        }
        conexion.commit();
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
        return true;

    }
    public int insertarImagen(String sentencia, Imagen img) throws SQLException, Exception{
        conectar(url);
        conexion.setAutoCommit(false);
        PreparedStatement psConId = conexion.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
        psConId.setBytes(1, img.getImagen());
        psConId.executeUpdate();
        ResultSet generatedKeys = psConId.getGeneratedKeys();
        generatedKeys.next();
        int idGenerado = generatedKeys.getInt(1);
        conexion.commit();
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
        psConId.close();
        return idGenerado;
    }

    public boolean actualizarMultiple(ArrayList<String> sql, String modoQuery, String clave) throws SQLException, NamingException {
        conectar(url);
        String error = "";
        conexion.setAutoCommit(false);
        for (int i = 0; i <= sql.size() - 1; i++) {
            String sentencia = sql.get(i);
            try {
                if (modoQuery.equals("INSERT")) {
                    if (!sentencia.contains("?")) {
                        PreparedStatement psConId = conexion.prepareStatement(sentencia);
                        psConId.executeUpdate();
                    } else {
                        PreparedStatement ps = conexion.prepareStatement(sentencia);
                        ps.setString(1, clave);
                        ps.executeUpdate();
                    }
                }
                if (modoQuery.equals("UPDATE") || modoQuery.equals("DELETE")) {
                    stmt.executeUpdate(sentencia);
                }

            } catch (SQLException ex) {
                conexion.rollback();
                conectar(url);
                throw ex;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw ex;
            }

        }
        conexion.commit();
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
        return true;
    }

    public ResultSet consultar(String sql) throws Exception, SQLException {
        try {
            conectar(url);
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            throw ex;
        }
        
    }

    /*Comportamiento*/
 /*Setters y Getters*/
    public static Connection getConexion() {
        return conexion;
    }

    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPass() {
        return pass;
    }
    /*Setters y Getters*/


}
