package Datos;

import Modelo.LogSistema;
import Modelo.Operador;
import Modelo.QueryEjecutada;
import java.sql.SQLException;
import java.util.ArrayList;

public class OpLogSistema implements IOperaciones<LogSistema, Integer>{


/*Estado*/
    private Database database;
    private Operador usuarioSistema;
/*Estado*/

/*Constructores*/
public OpLogSistema(Operador usuarioSistema){
    database = Database.getInstancia();
    this.usuarioSistema = usuarioSistema;
}
/*Constructores*/

/*Comportamiento*/
    @Override
    public LogSistema guardar(LogSistema cAnterior, LogSistema c) throws Exception, SQLException {
            return insertar(c);
    }

    @Override
    public LogSistema insertar(LogSistema c) throws Exception, SQLException {
        ArrayList<String> listaSQL  = new ArrayList();
        String textoError = c.getTextoError().replace("'","");
        String sql = "INSERT INTO LogsSistema (usuarioSistema, operacion, textoError) values ('"+this.usuarioSistema.getUsuarioSistema()+"','"+c.getOperacion()+"','"+textoError+"')";
        listaSQL.add(sql);
        for (QueryEjecutada q: c.getListaQuerys()){
            String query = q.getTextoQuery();
            query = query.replace("'","");
            listaSQL.add("INSERT INTO QuerysEjecutadas (idLog, textoQuery) values (?, '"+query+"')");
        }
        database.actualizarMultiple(listaSQL, "INSERT");
        return new LogSistema(-1);
    }

    @Override
    public LogSistema modificar(LogSistema cAnterior, LogSistema c) throws Exception, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LogSistema borrar(LogSistema c) throws Exception, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<LogSistema> obtenerTodos() throws Exception, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<LogSistema> buscar(String filtro, String extras) throws Exception, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LogSistema borradoMultiplePorIds(ArrayList<Integer> listaIds) throws Exception, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LogSistema registroConsola(String usuarioSistema, ArrayList<String> listaSQL, String operacion, String textoError) {
        throw new UnsupportedOperationException("Not supported yet."); //Objeto no Auditado
    }
/*Comportamiento*/

/*Getters y Setters*/

/*Getters y Setters*/
}
