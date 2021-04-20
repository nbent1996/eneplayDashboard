package Datos;
import Modelo.LogSistema;
import java.sql.SQLException;
import java.util.ArrayList;
public interface IOperaciones <T, P> {
    public LogSistema guardar (T cAnterior, T c) throws Exception, SQLException;
    public LogSistema insertar (T c)throws Exception, SQLException;
    public LogSistema modificar (T cAnterior, T c)throws Exception, SQLException;
    public LogSistema borrar(T c)throws Exception, SQLException;
    public ArrayList<T> obtenerTodos()throws Exception, SQLException;
    public ArrayList<T> buscar(String filtro, String extras)throws Exception, SQLException;
    public LogSistema borradoMultiplePorIds(ArrayList<P> listaIds) throws Exception, SQLException;
    public LogSistema registroConsola(String usuarioSistema, ArrayList<String> listaSQL, String operacion, String textoError) throws Exception, SQLException;
}
