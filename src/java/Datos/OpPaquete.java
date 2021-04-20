package Datos;

import Modelo.Categoria;
import Modelo.Empresa;
import Modelo.LogSistema;
import Modelo.Operador;
import Modelo.Paquete;
import Modelo.QueryEjecutada;
import Modelo.TieneTP;
import Modelo.TipoDispositivo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class OpPaquete implements IOperaciones<Paquete, Integer> {

  
/*Estado*/
private static Database database;
private OpLogSistema logging;
private Operador usuarioSistema;
/*Estado*/

/*Constructores*/
public OpPaquete(Operador usuarioSistema){
    this.database = Database.getInstancia();
    this.usuarioSistema = usuarioSistema;
    this.logging = new OpLogSistema(this.usuarioSistema);
}
/*Constructores*/

/*Comportamiento*/
  @Override
    public LogSistema guardar(Paquete cAnterior, Paquete c) throws Exception, SQLException {
        if(cAnterior == null){
            return insertar(c);
        }else{
            return modificar(cAnterior, c);
        }
    }

    @Override
    public LogSistema insertar(Paquete c) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        String modo = "UPDATE"; //Para paquetes sin instancias de TieneTP
        listaSQL.add("INSERT INTO Paquetes (nombrePaquete, costoBruto, identificacionTributaria) values ('"+c.getNombre()+"', '"+c.getCostoBruto()+"','"+c.getEmpresaAsociada().getIdentificacionTributaria()+"') ");
        if(!c.getListaTieneTP().isEmpty()){
            modo="INSERT";
            for(TieneTP t: c.getListaTieneTP()){
                listaSQL.add("INSERT INTO TieneTP (idPaquete, idTipoDispositivo, cantidadDispositivos) values (?,'"+t.getTipoDispositivo().getIdTipoDispositivo()+"','"+t.getCantidadDispositivos()+"') ");
            }
        }
        try{
            database.actualizarMultiple(listaSQL, modo);
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
    public LogSistema modificar(Paquete cAnterior, Paquete c) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        listaSQL.add("UPDATE Paquetes SET nombrePaquete='"+c.getNombre()+"', costoBruto='"+c.getCostoBruto()+"', identificacionTributaria='"+c.getEmpresaAsociada().getIdentificacionTributaria()+"' WHERE idPaquete = '"+cAnterior.getIdPaquete()+"' AND eliminado = 'N' ");
        /*Actualizando Tipos de Dispositivos asociados al Paquete solo si es necesario*/
        if(!listasSonIguales(c.getListaTieneTP(), cAnterior.getListaTieneTP())){
            listaSQL.add("DELETE FROM TieneTP WHERE idPaquete='"+cAnterior.getIdPaquete()+"' ");
            for(TieneTP t: c.getListaTieneTP()){
                listaSQL.add("INSERT INTO TieneTP (idPaquete, idTipoDispositivo, cantidadDispositivos) values ('"+c.getIdPaquete()+"','"+t.getTipoDispositivo().getIdTipoDispositivo()+"','"+t.getCantidadDispositivos()+"') ");
            }
        }
        /*Actualizando Tipos de Dispositivos asociados al Paquete solo si es necesario*/
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
    public LogSistema borrar(Paquete c) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        /*Borrar registros de tabla relacionaria entre el Paquete y el Tipo de Dispositivo (TieneTP)*/
        if(!c.getListaTieneTP().isEmpty()){
            for(TieneTP t: c.getListaTieneTP()){
                listaSQL.add("UPDATE TieneTP SET eliminado='Y' WHERE idPaquete='"+c.getIdPaquete()+"' ");
            }
        }
        /*Borrar registros de tabla relacionaria entre el Paquete y el Tipo de Dispositivo (TieneTP)*/
        listaSQL.add("UPDATE Paquetes SET eliminado='Y' WHERE idPaquete='"+c.getIdPaquete()+"' ");
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
    public ArrayList<Paquete> obtenerTodos() throws Exception, SQLException {
        return buscar(null,null);
    }

    @Override
    public ArrayList<Paquete> buscar(String filtro, String extras) throws Exception, SQLException {
        ArrayList<Paquete> lista = new ArrayList<>();
        int idPaquete = -1, cantidadDispositivos;
        float costoBruto =-1;
        String nombrePaquete="";
        //TipoDispositivo
        int idTipoDispositivo=-1;
        String modelo = "", nombre="", tipoComunicacion="";
        Categoria cat = null;
        
        Empresa empresaAsociada = null;
        ArrayList<TieneTP> listaTieneTP = new ArrayList<>();
        String sql = "SELECT idPaquete,nombrePaquete, costoBruto, identificacionTributaria from Paquetes ";
        ArrayList<String> listaSQL = new ArrayList<>();
        if(filtro!=null){
            sql+=filtro;
            sql+=" AND identificacionTributaria='"+this.usuarioSistema.getEmpresaAsociada().getIdentificacionTributaria()+"' AND eliminado = 'N' ";
        }else{
            sql+=" WHERE identificacionTributaria='"+this.usuarioSistema.getEmpresaAsociada().getIdentificacionTributaria()+"' AND eliminado = 'N' ";
        }
        listaSQL.add(sql);
        
        try{
            ResultSet rs = database.consultar(sql);
            while(rs.next()){
                idPaquete = rs.getInt("idPaquete");
                nombrePaquete = rs.getString("nombrePaquete");
                costoBruto = rs.getFloat("costoBruto");
                empresaAsociada = new Empresa(rs.getString("identificacionTributaria"));
                ResultSet rsTieneTP = database.consultar("SELECT TieneTP.idPaquete, TieneTP.idTipoDispositivo, TieneTP.cantidadDispositivos, TiposDispositivos.modelo, TiposDispositivos.nombre, TiposDispositivos.tipoComunicacion, TiposDispositivos.nombreCategoria from TieneTP, TiposDispositivos WHERE TieneTP.idTipoDispositivo = TiposDispositivos.idTipoDispositivo AND idPaquete = '"+idPaquete+"' AND TiposDispositivos.eliminado='N' AND TieneTP.eliminado='N'");
                listaTieneTP = new ArrayList<>();
                /*Cargando lista de tiene TP al paquete de turno en el ciclo*/
                while(rsTieneTP.next()){
                    cantidadDispositivos = rsTieneTP.getInt("TieneTP.cantidadDispositivos");
                    idTipoDispositivo = rsTieneTP.getInt("TieneTP.idTipoDispositivo");
                    modelo = rsTieneTP.getString("TiposDispositivos.modelo");
                    nombre = rsTieneTP.getString("TiposDispositivos.nombre");
                    tipoComunicacion = rsTieneTP.getString("TiposDispositivos.tipoComunicacion");
                    cat = new Categoria(rsTieneTP.getString("TiposDispositivos.nombreCategoria"));
                    listaTieneTP.add(new TieneTP(cantidadDispositivos,new TipoDispositivo(idTipoDispositivo,modelo, nombre,tipoComunicacion, cat)));
                }
                rsTieneTP.close();
                /*Cargando lista de tiene TP al paquete de turno en el ciclo*/
                lista.add(new Paquete(idPaquete,nombrePaquete, costoBruto,empresaAsociada, listaTieneTP));
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
        for (Integer i : listaIds) {
            listaIdsStr += i + " , ";
        }
        listaIdsStr = listaIdsStr.substring(0, (listaIdsStr.length() - 2));
        /*Armando listado de IDS para la Query*/
        ResultSet validarConsistencia = null;
        /*Validar lista de IDs vacia*/
        if (listaIds.isEmpty()) {
            return registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Baja", "ERROR: Lista de IDs llegó vacia al metodo borradoMultiplePorIds");
        }
        /*Validar lista de IDs vacia*/

        listaSQL.add("UPDATE TieneTP set eliminado='Y' WHERE idPaquete in(" + listaIdsStr + ")");
        listaSQL.add("UPDATE Paquetes set eliminado='Y' WHERE idPaquete in(" + listaIdsStr + ")");
        try {
            /*Validar que este Paquete no tenga registros en la tabla TieneTP (Relación TipoDispositivo-Paquete)*/
//            validarConsistencia = database.consultar("SELECT * FROM TieneTP WHERE idPaquete in (" + listaIdsStr + ")");
//            if (validarConsistencia.next()) {
//                validarConsistencia.close();
//                registroConsola(this.usuarioSistema, listaSQL, "Baja", "Alguno de los Paquetes que usted desea borrar está relacionado con algún Tipo de Dispositivo.");
//                throw new Exception("Alguno de los Paquetes que usted desea borrar está relacionado con algún Tipo de Dispositivo.");
//            }
//            validarConsistencia.close();
            /*Validar que este Paquete no tenga registros en la tabla TieneTP (Relación TipoDispositivo-Paquete)*/
            database.actualizarMultiple(listaSQL, "UPDATE");
        } catch (SQLException ex) {
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Baja", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
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
    
    public boolean listasSonIguales(ArrayList<TieneTP> a, ArrayList<TieneTP> b) {
        // comprobar que tienen el mismo tamaño y que no son nulos
        if ((a.size() != b.size()) || (a == null && b != null) || (a != null && b == null)) {
            return false;
        }

        if (a == null && b == null) {
            return true;
        }

        // ordenar las ArrayList y comprobar que son iguales          
        Collections.sort(a);
        Collections.sort(b);
        return a.equals(b);
    }
/*Comportamiento*/

/*Getters y Setters*/

/*Getters y Setters*/
}
