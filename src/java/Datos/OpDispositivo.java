package Datos;

import Modelo.Cliente;
import Modelo.Dispositivo;
import Modelo.Empresa;
import Modelo.LogSistema;
import Modelo.Operador;
import Modelo.Principal;
import Modelo.QueryEjecutada;
import Modelo.TipoDispositivo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OpDispositivo implements IOperaciones<Dispositivo, String> {
/*Estado*/
private static Database database;
private OpLogSistema logging;
private Operador usuarioSistema;
/*Estado*/

/*Constructores*/
public OpDispositivo(Operador usuarioSistema){
    this.database = Database.getInstancia();
    this.usuarioSistema = usuarioSistema;
    this.logging = new OpLogSistema(this.usuarioSistema);
}
/*Constructores*/

/*Comportamiento*/
  @Override
    public LogSistema guardar(Dispositivo cAnterior, Dispositivo c) throws Exception, SQLException {
        if(cAnterior == null){
            return insertar(c);
        }else{
            return modificar(cAnterior, c);
        }
    }

    @Override
    public LogSistema insertar(Dispositivo c) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        if (c.getClienteAsociado() == null) {
            listaSQL.add("INSERT INTO Dispositivos (nroSerie, estado, idTipoDispositivo, identificacionTributaria) values ('" + c.getNroSerie() + "','" + c.getEstado() + "','" + c.getTipoDispositivo().getIdTipoDispositivo() + "','" + c.getEmpresaAsociada().getIdentificacionTributaria() + "') ");
        } else {
            listaSQL.add("INSERT INTO Dispositivos (nroSerie, estado, idTipoDispositivo, identificacionTributaria, nroCliente) values ('" + c.getNroSerie() + "','" + c.getEstado() + "','" + c.getTipoDispositivo().getIdTipoDispositivo() + "','" + c.getEmpresaAsociada().getIdentificacionTributaria() + "','" + c.getClienteAsociado().getNroCliente() + "') ");
        }
        try {
            /*Validar que el nroSerie no exista ya en la base de datos*/
            ResultSet validarConsistencia = database.consultar("SELECT * FROM Dispositivos WHERE nroSerie='" + c.getNroSerie() + "' ");
            if (validarConsistencia.next()) {
                validarConsistencia.close();
                registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Alta", "El Número de serie ingresado ya existe en el sistema.");
                throw new Exception("El Número de serie ingresado ya existe en el sistema.");
            }
            validarConsistencia.close();
            /*Validar que el nroSerie no exista ya en la base de datos*/
            database.actualizarMultiple(listaSQL, "UPDATE");
        } catch (SQLException ex) {
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Alta", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Alta", ex.getMessage());
            throw ex;
        }
        return registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Alta", "NOERROR");
    }

    @Override
    public LogSistema modificar(Dispositivo cAnterior, Dispositivo c) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        if(c.getClienteAsociado()==null){
            listaSQL.add("UPDATE Dispositivos SET estado='"+c.getEstado()+"', idTipoDispositivo='"+c.getTipoDispositivo().getIdTipoDispositivo()+"' WHERE nroSerie ='"+c.getNroSerie()+"' ");
        }else{
            listaSQL.add("UPDATE Dispositivos SET estado='"+c.getEstado()+"', idTipoDispositivo='"+c.getTipoDispositivo().getIdTipoDispositivo()+"', nroCliente='"+c.getClienteAsociado().getNroCliente()+"' WHERE nroSerie ='"+c.getNroSerie()+"' ");

        }
        try{
            database.actualizarMultiple(listaSQL, "UPDATE");
        }catch(SQLException ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Modificación", ex.getMessage());
            throw ex;
        }catch(Exception ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Modificación", ex.getMessage());
            throw ex;
        }
        return registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Modificación", "NOERROR");
    }

    @Override
    public LogSistema borrar(Dispositivo c) throws Exception, SQLException {
       ArrayList<String> listaSQL = new ArrayList<>();
       listaSQL.add("UPDATE Dispositivos SET eliminado='Y' WHERE nroSerie='"+c.getNroSerie()+"' ");
       try{
            database.actualizarMultiple(listaSQL, "UPDATE");
        }catch(SQLException ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Baja", ex.getMessage());
            throw ex;
        }catch(Exception ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Baja", ex.getMessage());
            throw ex;
        }
        return registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Baja", "NOERROR");
    }

    @Override
    public ArrayList<Dispositivo> obtenerTodos() throws Exception, SQLException {
        return buscar(null, null);
    }

    @Override
    public ArrayList<Dispositivo> buscar(String filtro, String extras) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        ArrayList<Dispositivo> lista = new ArrayList<>();
        String nroSerie="", estado="";
        TipoDispositivo tipo=null;
        Empresa empresaAsociada;
        Cliente clienteAsociado;
        String sql = "SELECT dis.nroSerie, dis.estado, dis.idTipoDispositivo, dis.identificacionTributaria, pri.nroCliente, per.nombreCompleto \n" +
                     "from Principales pri RIGHT JOIN Dispositivos dis ON pri.nroCliente = dis.nroCliente LEFT JOIN Personas per ON pri.usuarioSistema = per.usuarioSistema ";
        if(filtro!=null){
            sql+=filtro;
            sql+= " AND dis.identificacionTributaria='"+this.usuarioSistema.getEmpresaAsociada().getIdentificacionTributaria()+"' AND dis.eliminado = 'N' ";
        }else{
            sql+= " WHERE dis.identificacionTributaria='"+this.usuarioSistema.getEmpresaAsociada().getIdentificacionTributaria()+"' AND dis.eliminado = 'N' ";
        }
        
        listaSQL.add(sql);
        try{
            ResultSet rs = database.consultar(sql);
            while(rs.next()){
                nroSerie = rs.getString("dis.nroSerie");
                estado = rs.getString("dis.estado");
                int idTipoDispositivo = rs.getInt("dis.idTipoDispositivo");
                ResultSet rsTipoDispositivo = database.consultar("SELECT nombre, modelo, tipoComunicacion FROM TiposDispositivos WHERE idTipoDispositivo='"+idTipoDispositivo+"' ");
                while(rsTipoDispositivo.next()){
                    tipo = new TipoDispositivo(idTipoDispositivo, rsTipoDispositivo.getString("modelo"), rsTipoDispositivo.getString("nombre"), rsTipoDispositivo.getString("tipoComunicacion"));
                }
                rsTipoDispositivo.close();
                empresaAsociada = new Empresa(rs.getString("dis.identificacionTributaria"));
                clienteAsociado = new Principal(rs.getInt("pri.nroCliente"), rs.getString("per.nombreCompleto"),empresaAsociada);
                lista.add(new Dispositivo(nroSerie, estado, tipo, empresaAsociada, clienteAsociado));
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
    public LogSistema borradoMultiplePorIds(ArrayList<String> listaIds) throws Exception, SQLException {
       ArrayList<String> listaSQL = new ArrayList<>();
       /*Armando listado de IDS para la Query*/
        String listaIdsStr = "";
        for(String i: listaIds){
            listaIdsStr +="' " + i + "' , ";
        }
        listaIdsStr = listaIdsStr.substring(0, (listaIdsStr.length()-2));
       /*Armando listado de IDS para la Query*/
       listaSQL.add("UPDATE Dispositivos SET eliminado='Y' WHERE nroSerie in ("+listaIdsStr+")");
       
       try{
            database.actualizarMultiple(listaSQL, "UPDATE");
        }catch(SQLException ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Baja", ex.getMessage());
            throw ex;
        }catch(Exception ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Baja", ex.getMessage());
            throw ex;
        }
        return registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Baja", "NOERROR");
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
