package Datos;

import Modelo.Empresa;
import Modelo.LogSistema;
import Modelo.Operador;
import Modelo.Paquete;
import Modelo.Principal;
import Modelo.QueryEjecutada;
import Modelo.Suscripcion;
import Resources.DTOs.DTOFechas;
import Resources.DTOs.Fecha;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OpSuscripcion implements IOperaciones<Suscripcion, Integer> {
/*Estado*/
private static Database database;
private OpLogSistema logging;
private Operador usuarioSistema;
/*Estado*/

/*Constructores*/
public OpSuscripcion(Operador usuarioSistema){
    this.database = Database.getInstancia();
    this.usuarioSistema = usuarioSistema;
    this.logging = new OpLogSistema(this.usuarioSistema);
}
/*Constructores*/

/*Comportamiento*/
 @Override
    public LogSistema guardar(Suscripcion cAnterior, Suscripcion c) throws Exception, SQLException {
        if(cAnterior == null){
            return insertar(c);
        }else{
            return modificar(cAnterior, c);
        }
    }

    @Override
    public LogSistema insertar(Suscripcion c) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        String activaStr = "S";
        if(!c.getActiva()){
            activaStr = "N";
        }
        listaSQL.add("INSERT INTO Suscripciones (fechaInicio, tiempoContrato, fechaFin, activa, nroDocumentoTitular, identificacionTributaria) values "
                + "('"+c.getFechaInicio().getFechaAStr(1)+"','"+c.getTiempoContrato()+"','"+c.getFechaFin().getFechaAStr(1)+"','"+activaStr+"','"+c.getClientePrincipal().getNroDocumento()+"', '"+usuarioSistema.getEmpresaAsociada().getIdentificacionTributaria()+"')");
        if(c.getListaPaquetes()!=null && !c.getListaPaquetes().isEmpty()){
        for(Paquete p: c.getListaPaquetes()){
                listaSQL.add("INSERT INTO PoseeSP(idSuscripcion, idPaquete) values(?, "+p.getIdPaquete()+")");
        }
        }
        try{
            database.actualizarMultiple(listaSQL, "INSERT");
        }catch(SQLException ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Alta", ex.getMessage());
            throw ex;
        }catch(Exception ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Alta", ex.getMessage());
            throw ex;
        }
        return registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Alta", "NOERROR");
    }

    @Override
    public LogSistema modificar(Suscripcion cAnterior, Suscripcion c) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        String activaStr = "N";
        if(c.getActiva())
            activaStr="S";
        listaSQL.add("UPDATE Suscripciones SET fechaInicio='"+c.getFechaInicio().getFechaAStr(1)+"', tiempoContrato='"+c.getTiempoContrato()+"', fechaFin='"+c.getFechaFin().getFechaAStr(1)+"', activa='"+activaStr+"' WHERE idSuscripcion='"+cAnterior.getIdSuscripcion()+"'");
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
    public LogSistema borrar(Suscripcion c) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        listaSQL.add("UPDATE Suscripciones set eliminado='Y' WHERE idSuscripcion='"+c.getIdSuscripcion()+"'");
        try{
            database.actualizarMultiple(listaSQL,"UPDATE");
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
    public ArrayList<Suscripcion> obtenerTodos() throws Exception, SQLException {
        return buscar(null,null);
    }

    @Override
    public ArrayList<Suscripcion> buscar(String filtro, String extras) throws Exception, SQLException {
        ArrayList<Suscripcion> lista = new ArrayList<>();
        int idSuscripcion = -1;
        DTOFechas fechaInicio, fechaFin;
        int anioInicio, mesInicio, diaInicio, anioFin, mesFin, diaFin;
        String nroDocumentoTitular = "";
        String identificacionTributaria = "";
        float tiempoContrato;
        boolean activa;
        ArrayList<String> listaSQL = new ArrayList<>();
        String sql ="";
        sql = "SELECT day(fechaInicio) as 'diaInicio', month(fechaInicio) as 'mesInicio', year(fechaInicio) as 'anioInicio', "
                   + " tiempoContrato, activa, idSuscripcion, "
                +    " day(fechaFin) as 'diaFin', month(fechaFin) as 'mesFin', year(fechaFin) as 'anioFin', nroDocumentoTitular, identificacionTributaria from Suscripciones ";
        if(filtro!=null){
            sql+= filtro;
            sql+=" AND identificacionTributaria='"+usuarioSistema.getEmpresaAsociada().getIdentificacionTributaria()+"' AND Suscripciones.eliminado='N' ";
        }else{
            sql+=" WHERE identificacionTributaria='"+usuarioSistema.getEmpresaAsociada().getIdentificacionTributaria()+"' AND Suscripciones.eliminado='N' ";
        }
        listaSQL.add(sql);
        try{
            ResultSet rs = database.consultar(sql);
            while(rs.next()){
                idSuscripcion = rs.getInt("idSuscripcion");
                diaInicio = rs.getInt("diaInicio");
                mesInicio = rs.getInt("mesInicio");
                anioInicio = rs.getInt("anioInicio");
                diaFin = rs.getInt("diaFin");
                mesFin = rs.getInt("mesFin");
                anioFin = rs.getInt("anioFin");
                tiempoContrato = rs.getFloat("tiempoContrato");
                String activaStr = rs.getString("activa");
                activa = false;
                if(activaStr.equals("S")){
                    activa = true;
                }
                fechaInicio = new DTOFechas(new Fecha(diaInicio, mesInicio, anioInicio));
                fechaFin = new DTOFechas(new Fecha(diaFin, mesFin, anioFin));
                /*Lista de paquetes*/
                ArrayList<Paquete> listaPaquetes = new ArrayList<>();
                ResultSet rsListaPaquetes = database.consultar("SELECT Paquetes.idPaquete, Paquetes.nombrePaquete FROM Paquetes, PoseeSP WHERE Paquetes.idPaquete = PoseeSP.idPaquete AND PoseeSP.idSuscripcion='"+idSuscripcion+"' AND PoseeSP.eliminado='N' ");      
                int idPaquete = -1;
                String nombre = "";
                while(rsListaPaquetes.next()){
                    idPaquete = rsListaPaquetes.getInt("Paquetes.idPaquete");
                    nombre = rsListaPaquetes.getString("Paquetes.nombrePaquete");
                    listaPaquetes.add(new Paquete(idPaquete, nombre));
                }
                rsListaPaquetes.close();
                /*Lista de paquetes*/
                
                /*Cliente asociado*/
                nroDocumentoTitular = rs.getString("nroDocumentoTitular");
                /*Cliente asociado*/
                identificacionTributaria = rs.getString("identificacionTributaria");
                Suscripcion s = new Suscripcion(idSuscripcion, fechaInicio, tiempoContrato, fechaFin, activa, listaPaquetes, new Principal(nroDocumentoTitular), new Empresa(identificacionTributaria));      
                lista.add(s);
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
    public LogSistema borradoMultiplePorIds(ArrayList<Integer> listaIds) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        /*Armando listado de IDS para la Query*/
        String listaIdsStr = "";
        for(Integer i: listaIds){
            listaIdsStr += i + " , ";
        }
        listaIdsStr = listaIdsStr.substring(0, (listaIdsStr.length()-2));
        /*Armando listado de IDS para la Query*/
        
        listaSQL.add("UPDATE GeneraSF set eliminado='Y' WHERE idSuscripcion in("+listaIdsStr+")");
        listaSQL.add("UPDATE Suscripciones set eliminado='Y' WHERE idSuscripcion in("+listaIdsStr+")");
        /*Validaciones*/
        if(listaIds.isEmpty()){
            return registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Baja", "ERROR: Lista de IDs llegó vacia al metodo borradoMultiplePorIds");
        }
        /*Validaciones*/
        try{
            database.actualizarMultiple(listaSQL,"UPDATE");
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
