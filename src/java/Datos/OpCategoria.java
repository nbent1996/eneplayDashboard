package Datos;

import Modelo.Categoria;
import Modelo.LogSistema;
import Modelo.Operador;
import Modelo.QueryEjecutada;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OpCategoria implements IOperaciones<Categoria, Integer>{
/*Estado*/
private static Database database;
private OpLogSistema logging;
private Operador usuarioSistema;
/*Estado*/

/*Constructores*/
public OpCategoria(Operador usuarioSistema){
    this.database = Database.getInstancia();
    this.usuarioSistema = usuarioSistema;
    this.logging = new OpLogSistema(this.usuarioSistema);

}
/*Constructores*/

/*Comportamiento*/
 @Override
    public LogSistema guardar(Categoria cAnterior, Categoria c) throws Exception, SQLException {
        if(cAnterior == null){
            return insertar(c);
        }else{
            return modificar(cAnterior, c);
        }
    }

    @Override
    public LogSistema insertar(Categoria c) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        LogSistema log = null;
        listaSQL.add("INSERT INTO Categorias (nombreCategoria) values ('"+c.getNombreCategoria()+"')");
        try{
        database.actualizarMultiple(listaSQL, "UPDATE");
        }catch(SQLException ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Alta", ex.getMessage());
            throw ex;
        }catch(Exception ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Alta", ex.getMessage());
            throw ex;
        }
        log = registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Alta", "NOERROR");
        return log;
    }

    @Override
    public LogSistema modificar(Categoria cAnterior, Categoria c) throws Exception, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LogSistema borrar(Categoria c) throws Exception, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Categoria> obtenerTodos() throws Exception, SQLException {
        return buscar(null, null);
    }

    @Override
    public ArrayList<Categoria> buscar(String filtro, String extras) throws Exception, SQLException {
        ArrayList<Categoria> lista = new ArrayList<>();
        String nombreCategoria = "";
        String sql = "SELECT * FROM Categorias ";
        if(filtro!=null){
            sql+=filtro;
            sql+=" and eliminado='N' order by nombreCategoria ";
        }else{
            sql+=" where eliminado='N' order by nombreCategoria ";
        }
        ArrayList<String> listaSQL = new ArrayList<>();
        listaSQL.add(sql);
        try{
        ResultSet rs = database.consultar(sql);
        while(rs.next()){
            nombreCategoria = rs.getString("nombreCategoria");
            lista.add(new Categoria(nombreCategoria));
        }
        rs.close();
        }catch(SQLException ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Búsqueda", ex.getMessage());
            throw ex;   
        }catch(Exception ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Búsqueda", ex.getMessage());
            throw ex;
        }
        registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Búsqueda", "NOERROR");
        return lista;
    }

    @Override
    public LogSistema borradoMultiplePorIds(ArrayList<Integer> listaIds) throws Exception, SQLException {
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
/*Comportamiento*/

/*Getters y Setters*/

/*Getters y Setters*/

   
}
