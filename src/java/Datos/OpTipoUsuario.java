package Datos;

import Modelo.LogSistema;
import Modelo.Operador;
import Modelo.Privilegio;
import Modelo.QueryEjecutada;
import Modelo.TipoUsuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class OpTipoUsuario implements IOperaciones<TipoUsuario, String> {

  
/*Estado*/
private static Database database;
private OpLogSistema logging;
private Operador usuarioSistema;
/*Estado*/

/*Constructores*/
public OpTipoUsuario(Operador usuarioSistema){
    this.database = Database.getInstancia();
    this.usuarioSistema = usuarioSistema;
    this.logging = new OpLogSistema(this.usuarioSistema);
}
/*Constructores*/

/*Comportamiento*/
  @Override
    public LogSistema guardar(TipoUsuario cAnterior, TipoUsuario c) throws Exception, SQLException {
        if(cAnterior == null){
            return insertar(c);
        }else{
            return modificar(cAnterior, c);
        }
    }

    @Override
    public LogSistema insertar(TipoUsuario c) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        listaSQL.add("INSERT INTO TiposUsuarios (nombre) values ('"+c.getNombre()+"')");
        if(!c.getListaPrivilegios().isEmpty()){
            for(Privilegio p : c.getListaPrivilegios()){
                listaSQL.add("INSERT INTO TieneTUP (nombreTipoUsuario, nombrePrivilegio) values ('"+c.getNombre()+"','"+p.getNombrePrivilegio()+"')");
            }
        }
        try{
        database.actualizarMultiple(listaSQL, "UPDATE");
        }catch(SQLException ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Alta", ex.getMessage());
            throw ex;
        }catch(Exception ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Alta", ex.getMessage());
            throw ex;
        }
        return registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Alta", "NOERROR");
    }

    @Override
    public LogSistema modificar(TipoUsuario cAnterior, TipoUsuario c) throws Exception, SQLException {
        //Solo modifica la lista de privilegios de ese tipo de usuario y no el nombre del tipo de usuario.
        ArrayList<String> listaSQL = new ArrayList<>();
        if(listasSonIguales(cAnterior.getListaPrivilegios(), c.getListaPrivilegios())){
            return new LogSistema( null,null, "NOERROR");
        }
//        if(!c.getNombre().equals(cAnterior.getNombre())){
//        listaSQL.add("UPDATE TiposUsuarios SET nombre='"+c.getNombre()+"' WHERE nombre='"+cAnterior.getNombre()+"' AND nombre not in(select nombre from TiposUsuario)");
//        }
        
        listaSQL.add("DELETE FROM TieneTUP WHERE nombreTipoUsuario='"+c.getNombre()+"' ");
        for(Privilegio p: c.getListaPrivilegios()){
            listaSQL.add("INSERT INTO TieneTUP (nombreTipoUsuario, nombrePrivilegio) VALUES ('"+c.getNombre()+"','"+p.getNombrePrivilegio()+"')");
        }
        try{
        database.actualizarMultiple(listaSQL, "UPDATE");
        }catch(SQLException ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Modificación", ex.getMessage());
            throw ex;
        }catch(Exception ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Modificación", ex.getMessage());
            throw ex;
        }
        return registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Modificación", "NOERROR");   
    }

    @Override
    public LogSistema borrar(TipoUsuario c) throws Exception, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<TipoUsuario> obtenerTodos() throws Exception, SQLException {
        return buscar(null,null);
    }

    @Override
    public ArrayList<TipoUsuario> buscar(String filtro, String extras) throws Exception, SQLException {
          ArrayList<TipoUsuario> lista = new ArrayList<>();
        String nombreTipoUsuario = "";
        String sql = "SELECT * FROM TiposUsuarios ";
        if(filtro!=null){
            sql+=filtro;
            sql+=" and eliminado='N' order by nombre ";
        }else{
            sql+=" where eliminado='N' order by nombre ";
        }
        ArrayList<String> listaSQL = new ArrayList<>();
        listaSQL.add(sql);
        try{
        ResultSet rs = database.consultar(sql);
        while(rs.next()){
            nombreTipoUsuario = rs.getString("nombre");
            ArrayList<Privilegio> listaPrivilegios = new ArrayList<>();
            String sqlPriv ="SELECT nombrePrivilegio from TieneTUP WHERE nombreTipoUsuario='"+nombreTipoUsuario+"' AND eliminado='N' "; 
            listaSQL.add(sqlPriv);
            ResultSet rsPrivilegios = database.consultar(sqlPriv);
            while(rsPrivilegios.next()){
                listaPrivilegios.add(new Privilegio(rsPrivilegios.getString("nombrePrivilegio")));
            }
            lista.add(new TipoUsuario(nombreTipoUsuario, listaPrivilegios));
        }
        rs.close();
        }catch(SQLException ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Búsqueda", ex.getMessage());
            throw ex;   
        }catch(Exception ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Búsqueda", ex.getMessage());
            throw ex;
        }
        registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Búsqueda", "NOERROR");
        return lista;
    }

    @Override
    public LogSistema borradoMultiplePorIds(ArrayList<String> listaIds) throws Exception, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LogSistema registroConsola(String usuarioSistema, ArrayList<String> listaSQL, String operacion, String textoError) throws Exception, SQLException {
        LogSistema log = new LogSistema(usuarioSistema, operacion, textoError, new ArrayList<>());
        System.out.println("----------------------------------");
        System.out.println("Usuario: " + usuarioSistema + "\nOperación: " + operacion + "\nTexto Error: " + textoError);
        System.out.println("Listado de Sentencias SQL:");
        for (String sentencia : listaSQL) {
            log.getListaQuerys().add(new QueryEjecutada(sentencia));
            System.out.println(sentencia);
        }
        logging.insertar(log);
        System.out.println("----------------------------------");
        /*Evidencia en consola*/
        return log;
    }
    public  boolean listasSonIguales(ArrayList<Privilegio> a, ArrayList<Privilegio> b){     
    // comprobar que tienen el mismo tamaño y que no son nulos
    if ((a.size() != b.size()) || (a == null && b!= null) || (a != null && b== null)){
        return false;
    }

    if (a == null && b == null) return true;

    // ordenar las ArrayList y comprobar que son iguales          
    Collections.sort(a);
    Collections.sort(b);
    return a.equals(b);
}
/*Comportamiento*/

/*Getters y Setters*/

/*Getters y Setters*/
}
