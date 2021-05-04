package Datos;

import Modelo.Imagen;
import java.sql.*;
import java.util.ArrayList;
import javax.naming.NamingException;

public class Database {

    /*Atributos*/
    private static Database instancia;
    private String modo = "glassfish"; /*local para testing local -- glassfish para testear desde capa de presentación*/
    
    /*UTILIZAR ESTAS 3 LINEAS PARA TESTING CON JUNIT*/
     //private static String user = "root";
     //private static String pass = "48283674";
     //private static String url = "jdbc:mysql://localhost:3306/eneplayPlatform"+"?user="+user+"&password="+pass+"&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    /*UTILIZAR ESTAS 3 LINEAS PARA TESTING CON JUNIT*/
    
     /*UTILIZAR ESTAS 3 LINEAS PARA PRODUCCION(QUE SE PUEDA CONSUMIR EL WEBSERVCE)*/
     private static String user = "root";
     private static String pass = "YbambxtkHz";
     private static String url = "jdbc:mysql://eneplay.c3qiy0zwamlb.us-east-2.rds.amazonaws.com:3306/eneplayPlatform"+"?user="+user+"&password="+pass+"&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
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

    public Conexion conectar(String url) throws SQLException, NamingException {
        try {
            Connection c = null;
            Statement s = null;
            switch(this.modo){
                case "local":
                    c = DriverManager.getConnection(url);
                break;
                case "glassfish":
                    javax.naming.InitialContext ctx = new javax.naming.InitialContext();
                    javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("jdbc/eneplay");
                    c = ds.getConnection();
                    break;

            }

            c.setAutoCommit(false);
            s = c.createStatement();
            return new Conexion(s, c);
        } catch (SQLException ex) {
            int codigo = ex.getErrorCode();
            String errorTexto = "Codigo de Error: " + codigo + " // Mensaje: " + ex.getMessage();
            System.out.println(errorTexto);
            if (codigo == 0) {
                throw new SQLException(errorTexto);
            }
        }
        return null;
    }
    public boolean desconectar(Conexion conexion){
        try {
            if (conexion.getConexion() != null) {
                conexion.getConexion().close();
                conexion.setConexion(null);
                conexion.setStmt(null);
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return false;
    }

    public int actualizar(String sql) throws SQLException, NamingException {
        try {
            Conexion DTOConexion = conectar(url);
            int retorno = DTOConexion.getStmt().executeUpdate(sql);
            desconectar(DTOConexion);
            return retorno;
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public boolean actualizarMultiple(ArrayList<String> sql, String modoQuery) throws SQLException, NamingException {
        Conexion DTOConexion = conectar(url);
        String error = "";
        int idGenerado = -1;
        DTOConexion.getConexion().setAutoCommit(false);
        for (int i = 0; i <= sql.size() - 1; i++) {
            String sentencia = sql.get(i);
            try {
                if (modoQuery.equals("INSERT")) {
                    if (!sentencia.contains("?") && !"".equals(sentencia)) {
                        PreparedStatement psConId = DTOConexion.getConexion().prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
                        psConId.executeUpdate();
                        ResultSet generatedKeys = psConId.getGeneratedKeys();
                        generatedKeys.next();
                        idGenerado = generatedKeys.getInt(1);
                    } else {
                        PreparedStatement ps = DTOConexion.getConexion().prepareStatement(sentencia);
                        ps.setInt(1, idGenerado);
                        ps.executeUpdate();
                    }
                }
                if (modoQuery.equals("UPDATE") || modoQuery.equals("DELETE")) {
                    DTOConexion.getStmt().executeUpdate(sentencia);
                }
            } catch (SQLException ex) {
                DTOConexion.getConexion().rollback();
                //conectar(url);
                throw ex;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw ex;
            }
        }
        DTOConexion.getConexion().commit();
        return true;

    }
    public int insertarImagen(String sentencia, Imagen img) throws SQLException, Exception{
        Conexion DTOConexion = conectar(url);
        DTOConexion.getConexion().setAutoCommit(false);
        PreparedStatement psConId = DTOConexion.getConexion().prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
        psConId.setBytes(1, img.getImagen());
        psConId.executeUpdate();
        ResultSet generatedKeys = psConId.getGeneratedKeys();
        generatedKeys.next();
        int idGenerado = generatedKeys.getInt(1);
        DTOConexion.getConexion().commit();
        psConId.close();
        return idGenerado;
    }

    public boolean actualizarMultiple(ArrayList<String> sql, String modoQuery, String clave) throws SQLException, NamingException {
        Conexion DTOConexion = conectar(url);
        String error = "";
        DTOConexion.getConexion().setAutoCommit(false);
        for (int i = 0; i <= sql.size() - 1; i++) {
            String sentencia = sql.get(i);
            try {
                if (modoQuery.equals("INSERT")) {
                    if (!sentencia.contains("?")) {
                        PreparedStatement psConId = DTOConexion.getConexion().prepareStatement(sentencia);
                        psConId.executeUpdate();
                    } else {
                        PreparedStatement ps = DTOConexion.getConexion().prepareStatement(sentencia);
                        ps.setString(1, clave);
                        ps.executeUpdate();
                    }
                }
                if (modoQuery.equals("UPDATE") || modoQuery.equals("DELETE")) {
                    DTOConexion.getStmt().executeUpdate(sentencia);
                }

            } catch (SQLException ex) {
                DTOConexion.getConexion().rollback();
                //conectar(url);
                throw ex;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw ex;
            }

        }
        DTOConexion.getConexion().commit();
        return true;
    }

    public ResultSet consultar(String sql) throws Exception, SQLException {
        try {
            Conexion DTOConexion = conectar(url);
            ResultSet rs = DTOConexion.getStmt().executeQuery(sql);
            return rs;
        } catch (SQLException ex) {
            throw ex;
        }
        
    }

    /*Comportamiento*/
 /*Setters y Getters*/

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
