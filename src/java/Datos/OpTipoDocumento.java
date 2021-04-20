package Datos;

import Modelo.LogSistema;
import Modelo.Operador;
import Modelo.QueryEjecutada;
import Modelo.TipoDocumento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OpTipoDocumento implements IOperaciones<TipoDocumento, String> {
    /*Estado*/
    private static Database database;
    private OpLogSistema logging; 
    private Operador usuarioSistema;
    /*Estado*/
    
    /*Constructores*/
    public OpTipoDocumento(Operador usuarioSistema){
        this.database = Database.getInstancia();
    this.usuarioSistema = usuarioSistema;
    this.logging = new OpLogSistema(this.usuarioSistema);
    }
    /*Constructores*/
    
    /*Comportamiento*/
    @Override
    public LogSistema guardar(TipoDocumento cAnterior, TipoDocumento c) throws Exception, SQLException {
        if(cAnterior == null){
            return insertar(c);
        }else{
            return modificar(cAnterior, c);
        }
    }

    @Override
    public LogSistema insertar(TipoDocumento c) throws Exception, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LogSistema modificar(TipoDocumento cAnterior, TipoDocumento c) throws Exception, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LogSistema borrar(TipoDocumento c) throws Exception, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<TipoDocumento> obtenerTodos() throws Exception, SQLException {
        return buscar(null, null);
    }

    @Override
    public ArrayList<TipoDocumento> buscar(String filtro, String extras) throws Exception, SQLException {
        ArrayList<TipoDocumento> lista = new ArrayList <>();
        String codDocumento = "";
        String nombreDocumento = "";
        String sql = "SELECT * FROM TiposDocumentos";
        if(filtro!=null){
            sql+=filtro;
            sql+=" and eliminado='N' order by indice asc";
        }else{
            sql+=" where eliminado='N' order by indice asc";
        }
        ArrayList<String> listaSQL = new ArrayList<>();
        listaSQL.add(sql);
        try{
        ResultSet rs = database.consultar(sql);
        while(rs.next()){
            codDocumento = rs.getString("codDocumento");
            nombreDocumento = rs.getString("nombreDocumento");
            lista.add(new TipoDocumento(codDocumento, nombreDocumento));
        }
            rs.close();
        } catch (SQLException ex) {
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Búsqueda", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
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
    /*Comportamiento*/





    
}
