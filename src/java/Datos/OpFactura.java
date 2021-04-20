package Datos;

import Modelo.Cliente;
import Modelo.Empresa;
import Modelo.Factura;
import Modelo.LogSistema;
import Modelo.Moneda;
import Modelo.Operador;
import Modelo.Principal;
import Modelo.QueryEjecutada;
import Modelo.Suscripcion;
import Resources.DTOs.DTOFechas;
import Resources.DTOs.Fecha;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class OpFactura implements IOperaciones<Factura, Integer> {

    
/*Estado*/
private static Database database;
private OpLogSistema logging;
private Operador usuarioSistema;
/*Estado*/

/*Constructores*/
public OpFactura(Operador usuarioSistema){
    this.database = Database.getInstancia();    
    this.usuarioSistema = usuarioSistema;
    this.logging = new OpLogSistema(this.usuarioSistema);
}
/*Constructores*/

/*Comportamiento*/
@Override
    public LogSistema guardar(Factura cAnterior, Factura c) throws Exception, SQLException {
        if(cAnterior == null){
            return insertar(c);
        }else{
            return modificar(cAnterior, c);
        }
    }

    @Override
    public LogSistema insertar(Factura c) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        listaSQL.add("INSERT INTO Facturas (fechaVencimiento, periodoServicioInicio, periodoServicioFin, tipoRecibo, monto, codigo, nroCliente, identificacionTributaria) "
                + "values ('"+c.getFechaVencimiento().getFechaAStr(1)+"','"+c.getPeriodoServicioInicio().getFechaAStr(1)+"','"+c.getPeriodoServicioFin().getFechaAStr(1)+"','"+c.getTipoRecibo()+"',"
                        + "'"+c.getMonto()+"','"+c.getMonedaAsociada().getCodigo()+"','"+c.getClienteAsociado().getNroCliente()+"','"+c.getEmpresaAsociada().getIdentificacionTributaria()+"')");
        try{
            database.actualizarMultiple(listaSQL, "INSERT");
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
    public LogSistema modificar(Factura cAnterior, Factura c) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        listaSQL.add("UPDATE Facturas SET fechaPago='"+c.getFechaPago().getFechaAStr(1)+"', monto='"+c.getMonto()+"' WHERE idFactura='"+cAnterior.getIdFactura()+"'");
        /*Actualizando facturas asociadas a la suscripcion solo si es necesario*/
        if(!listasSonIguales(c.getListaSuscripciones(), cAnterior.getListaSuscripciones())){
            listaSQL.add("DELETE FROM GeneraSF WHERE idFactura='"+cAnterior.getIdFactura()+"'");
            for(Suscripcion s: c.getListaSuscripciones()){
                listaSQL.add("INSERT INTO GeneraSF (idSuscripcion, idFactura) values ('"+cAnterior.getIdFactura()+"','"+s.getIdSuscripcion()+"')");
            }
        
    }
        /*Actualizando facturas asociadas a la suscripcion solo si es necesario*/
        
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
    public LogSistema borrar(Factura c) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();     
        /*Borrar registros de tabla relacionaria entre la Suscripción y Factura (GeneraSF)*/
        if(!c.getListaSuscripciones().isEmpty()){
            for(Suscripcion s: c.getListaSuscripciones()){
                listaSQL.add("UPDATE GeneraSF SET eliminado='Y' WHERE idFactura='"+c.getIdFactura()+"'");
            }
        }
        /*Borrar registros de tabla relacionaria entre la Suscripción y Factura (GeneraSF)*/
        listaSQL.add("UPDATE Facturas SET eliminado='Y' WHERE idFactura='"+c.getIdFactura()+"'");
        try{
            database.actualizarMultiple(listaSQL,"UPDATE");
        }catch(SQLException ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Baja", ex.getMessage());
            throw ex;
        }catch(Exception ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Baja", ex.getMessage());
            throw ex;
        }
        return registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Baja", "NOERROR");    
    }

    @Override
    public ArrayList<Factura> obtenerTodos() throws Exception, SQLException {
        return buscar(null,null);
    }

    @Override
    public ArrayList<Factura> buscar(String filtro, String extras) throws Exception, SQLException {
        ArrayList<Factura> lista = new ArrayList<>();
        int idFactura = -1;
        DTOFechas fechaPago, fechaEmision, fechaVencimiento, periodoServicioInicio, periodoServicioFin;
        int diaPago, mesPago, anioPago, diaEmision, mesEmision, anioEmision, diaVencimiento, mesVencimiento, anioVencimiento, diaPSI, mesPSI, anioPSI, diaPSF, mesPSF, anioPSF;
        double monto;
        String tipoRecibo;
        Cliente clienteAsociado;
        Moneda monedaAsociada;
        Empresa empresaAsociada;
        String sql = "SELECT day(fechaPago) as 'diaPago', month(fechaPago) as 'mesPago', year(fechaPago) as 'anioPago',"
                + " day(fechaEmision) as 'diaEmision', month(fechaEmision) as 'mesEmision', year(fechaEmision) as 'anioEmision', "
                + " day(fechaVencimiento) as 'diaVencimiento', month(fechaVencimiento) as 'mesVencimiento', year(fechaVencimiento) as 'anioVencimiento', "
                + " day(periodoServicioInicio) as 'diaPSI', month(periodoServicioInicio) as 'mesPSI', year(periodoServicioInicio) as 'anioPSI', "
                + " day(periodoServicioFin) as 'diaPSF', month(periodoServicioFin) as 'mesPSF', year(periodoServicioFin) as 'anioPSF', "
                + " monto, tipoRecibo, nroCliente, codigo, identificacionTributaria, idFactura FROM Facturas ";
        ArrayList<String> listaSQL = new ArrayList<>();
        if(filtro!=null){
            sql+= filtro;
            sql+= " AND eliminado='N' ";
        }else{
            sql+=" WHERE eliminado='N' ";
        }
        listaSQL.add(sql);
        
        
        try{
            ResultSet rs = database.consultar(sql);
            while(rs.next()){
                idFactura = rs.getInt("idFactura");
                /**/
                diaPago = rs.getInt("diaPago");
                mesPago = rs.getInt("mesPago");
                anioPago = rs.getInt("anioPago");
                fechaPago = new DTOFechas(new Fecha(diaPago, mesPago, anioPago));
                /**/
                diaEmision = rs.getInt("diaEmision");
                mesEmision = rs.getInt("mesEmision");
                anioEmision = rs.getInt("anioEmision");
                fechaEmision = new DTOFechas(new Fecha(diaEmision, mesEmision, anioEmision));
                /**/
                diaVencimiento = rs.getInt("diaVencimiento");
                mesVencimiento = rs.getInt("mesVencimiento");
                anioVencimiento = rs.getInt("anioVencimiento");
                fechaVencimiento = new DTOFechas(new Fecha(diaVencimiento, mesVencimiento, anioVencimiento));
                /**/
                diaPSI = rs.getInt("diaPSI");
                mesPSI = rs.getInt("mesPSI");
                anioPSI = rs.getInt("anioPSI");
                periodoServicioInicio = new DTOFechas(new Fecha(diaPSI, mesPSI, anioPSI));
                /**/
                diaPSF = rs.getInt("diaPSF");
                mesPSF = rs.getInt("mesPSF");
                anioPSF = rs.getInt("anioPSF");
                periodoServicioFin = new DTOFechas(new Fecha(diaPSF, mesPSF, anioPSF));
                /**/
                monto = rs.getFloat("monto");
                tipoRecibo = rs.getString("tipoRecibo");
                clienteAsociado = new Principal(rs.getInt("nroCliente"));
                monedaAsociada = new Moneda(rs.getString("codigo"));
                empresaAsociada = new Empresa(rs.getString("identificacionTributaria"));
                lista.add(new Factura(idFactura, fechaPago, fechaEmision, fechaVencimiento, periodoServicioInicio, periodoServicioFin, monto, tipoRecibo, clienteAsociado, new ArrayList<>(), monedaAsociada, empresaAsociada));
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
        ArrayList<String> listaSQL = new ArrayList<>();
        /*Armando listado de IDS para la Query*/
        String listaIdsStr = "";
        for(Integer i: listaIds){
            listaIdsStr += i + " , ";
        }
        listaIdsStr = listaIdsStr.substring(0, (listaIdsStr.length()-2));
        /*Armando listado de IDS para la Query*/
        
        listaSQL.add("UPDATE GeneraSF set eliminado='Y' WHERE idFactura in("+listaIdsStr+")");
        listaSQL.add("UPDATE Facturas set eliminado='Y' WHERE idFactura in("+listaIdsStr+")");
        /*Validaciones*/
        if(listaIds.isEmpty()){
            registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Baja", "ERROR: Lista de IDs llegó vacia al metodo borradoMultiplePorIds");
        }
        /*Validaciones*/
        try{
            database.actualizarMultiple(listaSQL,"UPDATE");
        }catch(SQLException ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Baja", ex.getMessage());
            throw ex;
        }catch(Exception ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Baja", ex.getMessage());
            throw ex;
        }
        return registroConsola(this.usuarioSistema.getUsuarioSistema(),listaSQL, "Baja", "NOERROR");    
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

    public boolean listasSonIguales(ArrayList<Suscripcion> a, ArrayList<Suscripcion> b) {
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
