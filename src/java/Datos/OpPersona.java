package Datos;

import Modelo.Empresa;
import Modelo.Funciones;
import Modelo.Idioma;
import Modelo.LogSistema;
import Modelo.Operador;
import Modelo.Pais;
import Modelo.Persona;
import Modelo.Principal;
import Modelo.QueryEjecutada;
import Modelo.Secundario;
import Modelo.TipoDocumento;
import Modelo.TipoUsuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OpPersona implements IOperaciones<Persona, String> {

    
/*Estado*/
private static Database database;
private OpLogSistema logging;
private Operador usuarioSistema;
/*Estado*/

/*Constructores*/
public OpPersona(Operador usuarioSistema){
    this.database = Database.getInstancia();
    this.usuarioSistema = usuarioSistema;
    this.logging = new OpLogSistema(this.usuarioSistema);
}
/*Constructores*/

/*Comportamiento*/
@Override
    public LogSistema guardar(Persona cAnterior, Persona c) throws Exception, SQLException {
    /*Validaciones en comun para Insertar y Modificar*/
    ResultSet rs = null;
    Principal p = null;
    Principal pAnterior = null;
    Secundario s = null;
    Secundario sAnterior = null;
    String telefonoA = "", telefonoB="", emailA = "", emailB="";
    String usuarioSistema = "", usuarioSistemaAnterior="";
    if (c instanceof Principal) {
        p = (Principal) c;
        if(cAnterior!=null){
            pAnterior = (Principal) cAnterior;
            telefonoB = pAnterior.getTelefono();
            emailB = pAnterior.getEmail();
            usuarioSistemaAnterior=pAnterior.getUsuarioSistema();
        }
        telefonoA = p.getTelefono();
        emailA = p.getEmail();
        usuarioSistema=p.getUsuarioSistema();
        
    }
    if (c instanceof Secundario) {
        s = (Secundario) c;
        if(cAnterior!=null){
            sAnterior = (Secundario) cAnterior;
            telefonoB = sAnterior.getTelefono();
            emailB = sAnterior.getEmail();
        }
        telefonoA = s.getTelefono();
        emailA = s.getEmail();
    }
    /*Validando clave primaria usuarioSistema*/
    if(!usuarioSistema.equals("")){
        String sqlUsuarioSistema = "SELECT * FROM Personas WHERE Personas.usuarioSistema='"+usuarioSistema+"' ";
        if(!usuarioSistemaAnterior.equals("")){
            sqlUsuarioSistema+=" AND Personas.usuarioSistema!='"+usuarioSistemaAnterior+"' ";
        }
        rs = database.consultar(sqlUsuarioSistema);
        if(rs.next()){
            throw new Exception("El campo usuario ya existe en la base de datos y no se puede repetir.");
        }
    }
    /*Validando Campo Unico Telefono*/
    if (!telefonoA.equals("")) {
        String sqlTelefono = "SELECT * FROM Clientes WHERE Clientes.telefono='" + telefonoA + "' ";
        if(!telefonoB.equals("")){
            sqlTelefono+=" AND Clientes.telefono!='"+telefonoB+"' ";
        }
        rs = database.consultar(sqlTelefono);
        if (rs.next()) {
            throw new Exception("El campo teléfono ya existe en la base de datos y no se puede repetir.");
        }
    }
    /*Validando Campo Unico Email*/
    if (!emailA.equals("")) {
        String sqlEmail = "SELECT * FROM Clientes WHERE Clientes.email='"+emailA+"' ";
        if(!emailB.equals("")){
            sqlEmail+=" AND Clientes.email!='"+emailB+"' ";
        }
        rs = database.consultar(sqlEmail);
        if(rs.next()){
            throw new Exception("El campo email ya existe en la base de datos y no se puede repetir.");
        }
    }
        /*Validaciones en comun para Insertar y Modificar*/ 
        if(cAnterior == null){
            return insertar(c);
        }else{
            return modificar(cAnterior, c);
        }
    }

    @Override
    public LogSistema insertar(Persona c) throws Exception, SQLException {
        ArrayList<String> listaSQLSinAI = new ArrayList<>(); /*SIN AUTOINCREMENTAL*/
        ArrayList<String> listaSQLConAI = new ArrayList<>(); /*CON AUTOINCREMENTAL*/
        ArrayList<String> listaCompleta = new ArrayList<>(); /*LISTA CON TODAS LAS SQL PARA LOGGING*/

        String sqlA, sqlB1, sqlB2, sqlC1, sqlC2, sqlD;
        String usuarioSistema = "";
        if(c.getUsuarioSistema().equals("")){
            usuarioSistema = Funciones.generarCadenaAleatoria(10);
            c.setUsuarioSistema(usuarioSistema);
        }
        switch(c.getClass().getName()){
            case "Modelo.Principal":
            Principal principal = (Principal) c;
            sqlA = " INSERT INTO Personas (usuarioSistema, nombreCompleto, codigo, identificacionTributaria) values "
                + "('"+c.getUsuarioSistema()+"','"+c.getNombreCompleto()+"','"+c.getPaisResidencia().getCodigo()+"','"+c.getEmpresaAsociada().getIdentificacionTributaria()+"')";
            sqlB1= " INSERT INTO Clientes (email,usuarioSistema, telefono) values ('"+principal.getEmail()+"','"+c.getUsuarioSistema()+"', '"+principal.getTelefono()+"') ";
            sqlB2 = "INSERT INTO Principales (nroDocumento, servicioActivo, usuarioSistema, nroCliente, codDocumento) values "
                    + "('"+principal.getNroDocumento()+"','N','"+principal.getUsuarioSistema()+"',?,'"+principal.getTipoDocumento().getCodDocumento()+"')";
            listaSQLSinAI.add(sqlA);
            listaSQLConAI.add(sqlB1);
            listaSQLConAI.add(sqlB2);
            listaCompleta.add(sqlA);
            listaCompleta.add(sqlB1);
            listaCompleta.add(sqlB2);
            break;
            case "Modelo.Secundario":
            Secundario secundario = (Secundario) c;
            sqlA = " INSERT INTO Personas (usuarioSistema, nombreCompleto, codigo, identificacionTributaria) values "
                + "('"+c.getUsuarioSistema()+"','"+c.getNombreCompleto()+"','"+c.getPaisResidencia().getCodigo()+"','"+c.getEmpresaAsociada().getIdentificacionTributaria()+"')";
            sqlC1 = " INSERT INTO Clientes (email, usuarioSistema, telefono) values ('"+secundario.getEmail()+"','"+secundario.getUsuarioSistema()+"', '"+secundario.getTelefono()+"' )";
            sqlC2 = " INSERT INTO Secundarios (usuarioSistema, nroCliente, nroDocumento) values ('"+secundario.getUsuarioSistema()+"',?,'"+secundario.getPrincipalAsociado().getNroDocumento()+"')";
            listaSQLSinAI.add(sqlA);
            listaSQLConAI.add(sqlC1);
            listaSQLConAI.add(sqlC2);
            listaCompleta.add(sqlA);
            listaCompleta.add(sqlC1);
            listaCompleta.add(sqlC2);
            break;
            case "Modelo.Operador":
            Operador operador = (Operador) c;
            sqlA = " INSERT INTO Personas (usuarioSistema, nombreCompleto, codigo, identificacionTributaria) values "
                + "('"+c.getUsuarioSistema()+"','"+c.getNombreCompleto()+"','"+c.getPaisResidencia().getCodigo()+"','"+c.getEmpresaAsociada().getIdentificacionTributaria()+"')";
            sqlD = " INSERT INTO OperadoresDashboard (usuarioSistema, clave, nombre, genero) values "
                    + "('"+operador.getUsuarioSistema()+"',SHA('"+operador.getClave()+"'),'"+operador.getTipoUsuario().getNombre()+"', '"+operador.getGenero()+"') ";
            listaSQLSinAI.add(sqlA);
            listaSQLSinAI.add(sqlD);
            listaCompleta.add(sqlA);
            listaCompleta.add(sqlD);
            break;
        }
        try{
        if(!listaSQLSinAI.isEmpty()){
        database.actualizarMultiple(listaSQLSinAI, "UPDATE");
        }
        if(!listaSQLConAI.isEmpty()){
        database.actualizarMultiple(listaSQLConAI, "INSERT");
        }
        
        }catch(SQLException ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaCompleta, "Alta", ex.getMessage());
            throw ex;
        }catch(Exception ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaCompleta, "Alta", ex.getMessage());
            throw ex;
        }
        return registroConsola(this.usuarioSistema.getUsuarioSistema(), listaCompleta, "Alta", "NOERROR");
    }

    @Override
    public LogSistema modificar(Persona cAnterior, Persona c) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        String sqlA, sqlB, sqlC, sqlD;
        try {
            ResultSet validarDependencias = null;
            /*Validar consistencia de los datos tabla Personas*/
            if(!cAnterior.getUsuarioSistema().equals(c.getUsuarioSistema())){ //Si el usuario cambió se valida la no existencia en la base
            validarDependencias = database.consultar("SELECT * FROM Personas WHERE Personas.usuarioSistema='" + c.getUsuarioSistema() + "' ");
            if (validarDependencias.next()) {
                validarDependencias.close();
                registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Modificación", "El usuario que usted desea asignar ya está en uso en el sistema.");
                throw new Exception("El usuario que usted desea asignar ya está en uso en el sistema.");
            }
            validarDependencias.close();
            }
            /*Validar consistencia de los datos tabla Personas*/
            switch (c.getClass().getName()) {
                case "Modelo.Operador":
                    Operador operador = (Operador) c;
                    sqlA = "UPDATE Personas, OperadoresDashboard SET Personas.usuarioSistema='" + c.getUsuarioSistema() + "', Personas.nombreCompleto='" + c.getNombreCompleto() + "' , OperadoresDashboard.usuarioSistema='" + operador.getUsuarioSistema() + "', OperadoresDashboard.clave = SHA('"+operador.getClave()+"'), OperadoresDashboard.genero ='"+operador.getGenero()+"' ,OperadoresDashboard.nombre='"+operador.getTipoUsuario().getNombre()+"'  WHERE Personas.usuarioSistema = OperadoresDashboard.usuarioSistema AND Personas.usuarioSistema='" + cAnterior.getUsuarioSistema() + "'";
                    /*No se valida la no repetición del usuarioSistema porque ya fue validado desde la tabla Personas*/
                    listaSQL.add(sqlA);
                    break;
                case "Modelo.Principal":
                    Principal principal = (Principal) c;
                    String servicioActivo = "N";
                    if (principal.getServicioActivo()) {
                        servicioActivo = "S";
                    }
                    sqlA = "UPDATE Personas, Clientes, Principales SET Personas.nombreCompleto='" + c.getNombreCompleto() + "' ,Clientes.email ='"+principal.getEmail()+"', Principales.servicioActivo='" + servicioActivo + "', telefono='"+principal.getTelefono()+"' where Personas.usuarioSistema = Clientes.usuarioSistema AND Clientes.usuarioSistema = Principales.usuarioSistema AND Principales.nroDocumento='" + principal.getNroDocumento() + "' and Principales.usuarioSistema='" + principal.getUsuarioSistema() + "'";
                    listaSQL.add(sqlA);
                    break;

                case "Modelo.Secundario":
                    Secundario secundario = (Secundario) c;
                    Secundario sAnterior = (Secundario) cAnterior;
                    sqlA = "UPDATE Personas, Clientes, Secundarios SET Personas.nombreCompleto='"+secundario.getNombreCompleto()+"', Clientes.email='"+secundario.getEmail()+"', Secundarios.nroDocumento='"+secundario.getPrincipalAsociado().getNroDocumento()+"', telefono='"+secundario.getTelefono()+"' where Personas.usuarioSistema = Clientes.usuarioSistema AND Clientes.nroCliente = Secundarios.nroCliente AND Secundarios.usuarioSistema='"+secundario.getUsuarioSistema()+"' ";
                    /*No se valida la no repetición del usuarioSistema porque ya fue validado desde la tabla Personas*/
                    listaSQL.add(sqlA);
                    break;

        }
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
    public LogSistema borrar(Persona c) throws Exception, SQLException {
        ArrayList<String> listaSQL = new ArrayList<>();
        String sqlA, sqlB, sqlC, sqlD, sqlE;
        switch(c.getClass().getName()){
            case "Modelo.Operador":
                Operador operador = (Operador) c;
                sqlA = "UPDATE Personas SET eliminado='Y' WHERE usuarioSistema='"+c.getUsuarioSistema()+"'";
                sqlB = "UPDATE OperadoresDashboard SET eliminado='Y' WHERE usuarioSistema='"+operador.getUsuarioSistema()+"'";
                listaSQL.add(sqlA);
                listaSQL.add(sqlB);
            break;
            case "Modelo.Principal":
                sqlD = "UPDATE Personas, Clientes, Principales SET Personas.eliminado='Y', Clientes.eliminado='Y', Principales.eliminado='Y' WHERE Personas.usuarioSistema=Clientes.usuarioSistema AND Clientes.usuarioSistema=Principales.usuarioSistema AND Personas.usuarioSistema='"+c.getUsuarioSistema()+"' ";
                sqlE = "UPDATE Personas, Clientes, Secundarios SET Personas.eliminado='Y', Clientes.eliminado='Y', Secundarios.eliminado='Y' WHERE Personas.usuarioSistema=Clientes.usuarioSistema AND Clientes.usuarioSistema=Secundarios.usuarioSistema AND Personas.usuarioSistema='"+c.getUsuarioSistema()+"' ";
                listaSQL.add(sqlD);
                listaSQL.add(sqlE);
            break;
            
            case "Modelo.Secundario":
                sqlD = "UPDATE Personas, Clientes, Principales SET Personas.eliminado='Y', Clientes.eliminado='Y', Principales.eliminado='Y' WHERE Personas.usuarioSistema=Clientes.usuarioSistema AND Clientes.usuarioSistema=Principales.usuarioSistema AND Personas.usuarioSistema='"+c.getUsuarioSistema()+"' ";
                sqlE = "UPDATE Personas, Clientes, Secundarios SET Personas.eliminado='Y', Clientes.eliminado='Y', Secundarios.eliminado='Y' WHERE Personas.usuarioSistema=Clientes.usuarioSistema AND Clientes.usuarioSistema=Secundarios.usuarioSistema AND Personas.usuarioSistema='"+c.getUsuarioSistema()+"' ";
                listaSQL.add(sqlD);
                listaSQL.add(sqlE);
            break;

        }
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
    public ArrayList<Persona> obtenerTodos() throws Exception, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Persona> buscar(String filtro, String extras) throws Exception, SQLException {
        /*En el String extras se deberá almacenar el tipo de persona sobre el cual se quiere hacer búsqueda, OPCIONES POSIBLES: Modelo.Operador, Modelo.Principal, Modelo.Secundario*/
        ArrayList<Persona> personas = new ArrayList<>();
        String usuarioSistema, nombreCompleto, codigo, identificacionTributaria; /*Tabla Persona*/
        String nombreTipoUsuario, genero; /*Tabla OperadoresDashboard*/
        String nroCliente, email, telefono; /*Tabla Cliente*/
        String nroDocumento, servicioActivo, codDocumento; /*Tabla Principales*/
        String nroDocumentoPrincipal; /*Tabla Secundarios*/
        String nombreEmpresa, razonSocial, idioma, pais;
        Float impuestos;
        String sqlA ="", sqlB="", sqlC="";
        ResultSet rs = null;
        ArrayList<String> listaSQL = new ArrayList<>();
        try{
            
            switch (extras) {
                case "Modelo.Operador":
                    sqlA = "SELECT Personas.usuarioSistema, Personas.nombreCompleto, Personas.codigo, Personas.identificacionTributaria, OperadoresDashboard.nombre, OperadoresDashboard.genero, Empresas.nombre, Empresas.razonSocial, Empresas.impuestos, Empresas.nombreIdioma, Empresas.codigo from Personas, OperadoresDashboard, Empresas ";
                    if (filtro != null) {
                        sqlA += filtro;
                        sqlA += " AND Personas.usuarioSistema = OperadoresDashboard.usuarioSistema AND Personas.eliminado='N' AND OperadoresDashboard.usuarioSistema!='loginUser' AND Personas.identificacionTributaria=Empresas.identificacionTributaria AND OperadoresDashboard.eliminado='N' ";
                    } else {
                        sqlA += " WHERE Personas.usuarioSistema = OperadoresDashboard.usuarioSistema  AND Personas.eliminado='N' AND OperadoresDashboard.usuarioSistema!='loginUser' AND Personas.identificacionTributaria=Empresas.identificacionTributaria AND OperadoresDashboard.eliminado='N' ";
                    }
                    rs = database.consultar(sqlA);
                    while(rs.next()){
                        usuarioSistema = rs.getString("usuarioSistema");
                        nombreCompleto = rs.getString("nombreCompleto");
                        codigo = rs.getString("codigo");
                        identificacionTributaria = rs.getString("identificacionTributaria");
                        /*Atributos empresa*/
                        nombreEmpresa = rs.getString("Empresas.nombre");
                        razonSocial = rs.getString("Empresas.razonSocial");
                        idioma = rs.getString("Empresas.nombreIdioma");
                        pais = rs.getString("Empresas.codigo");
                        impuestos = rs.getFloat("Empresas.impuestos");
                        /*Atributos empresa*/
                        Empresa emp = new Empresa(identificacionTributaria, nombreEmpresa, razonSocial, impuestos, new Idioma(idioma), new Pais(pais));
                        nombreTipoUsuario = rs.getString("nombre");
                        genero = rs.getString("genero");
                        personas.add(new Operador("", usuarioSistema, nombreCompleto, emp,new Pais(codigo),new TipoUsuario(nombreTipoUsuario), genero));
                    }
                    rs.close();
                    listaSQL.add(sqlA);
                    break;
                case "Modelo.Principal":
                    sqlB = "SELECT Personas.usuarioSistema, Personas.nombreCompleto, Personas.codigo, Personas.identificacionTributaria, Principales.nroDocumento, Principales.nroCliente, Principales.servicioActivo, Principales.codDocumento, Clientes.email, Clientes.telefono from Personas, Principales, Clientes ";
                    if (filtro != null) {
                        sqlB += filtro;
                        sqlB += " AND Personas.identificacionTributaria='"+this.usuarioSistema.getEmpresaAsociada().getIdentificacionTributaria()+"' AND Personas.usuarioSistema = Principales.usuarioSistema AND Principales.nroCliente = Clientes.nroCliente AND Personas.eliminado='N' AND Principales.eliminado='N' ";
                    } else {
                        sqlB += " WHERE Personas.identificacionTributaria='"+this.usuarioSistema.getEmpresaAsociada().getIdentificacionTributaria()+"' AND Personas.usuarioSistema = Principales.usuarioSistema AND Principales.nroCliente = Clientes.nroCliente AND Personas.eliminado='N' AND Principales.eliminado='N' ";
                    }
                    rs=database.consultar(sqlB);
                    while(rs.next()){
                        usuarioSistema = rs.getString("usuarioSistema");
                        nombreCompleto = rs.getString("nombreCompleto");
                        codigo = rs.getString("codigo");
                        identificacionTributaria = rs.getString("identificacionTributaria");
                        nroDocumento = rs.getString("nroDocumento");
                        servicioActivo = rs.getString("servicioActivo");
                        codDocumento =  rs.getString("codDocumento");
                        nroCliente = rs.getString("nroCliente");
                        email = rs.getString("email");
                        telefono = rs.getString("Clientes.telefono");
                        int nroC = Integer.parseInt(nroCliente);
                        boolean servActivo = false;
                        if(servicioActivo.equals("S"))
                            servActivo = true;
                        personas.add(new Principal(usuarioSistema, nombreCompleto, new Empresa(identificacionTributaria), new Pais(codigo), nroC, email, nroDocumento, servActivo, new TipoDocumento(codDocumento),telefono));
                    }
                    rs.close();
                    listaSQL.add(sqlB);
                    break;
                case "Modelo.Secundario":
                    sqlC = "SELECT Personas.usuarioSistema, Personas.nombreCompleto, Personas.codigo, Personas.identificacionTributaria, Secundarios.nroDocumento, Secundarios.nroCliente, Clientes.email, Clientes.telefono from Personas, Clientes, Secundarios ";
                    if (filtro != null) {
                        sqlC += filtro;
                        sqlC += " AND Personas.identificacionTributaria='"+this.usuarioSistema.getEmpresaAsociada().getIdentificacionTributaria()+"' AND Personas.usuarioSistema = Clientes.usuarioSistema AND Clientes.nroCliente = Secundarios.nroCliente AND Personas.eliminado='N' AND Secundarios.eliminado='N' ";
                    } else {
                        sqlC += " WHERE Personas.identificacionTributaria='"+this.usuarioSistema.getEmpresaAsociada().getIdentificacionTributaria()+"' AND Personas.usuarioSistema = Clientes.usuarioSistema AND Clientes.nroCliente = Secundarios.nroCliente AND Personas.eliminado='N' AND Secundarios.eliminado='N' ";
                    }
                    rs=database.consultar(sqlC);
                    while(rs.next()){
                        usuarioSistema = rs.getString("usuarioSistema");
                        nombreCompleto = rs.getString("nombreCompleto");
                        codigo = rs.getString("codigo");
                        identificacionTributaria = rs.getString("identificacionTributaria");
                        nroDocumentoPrincipal = rs.getString("nroDocumento");
                        nroCliente = rs.getString("nroCliente");
                        email = rs.getString("Clientes.email");
                        telefono = rs.getString("Clientes.telefono");
                        int nroC = Integer.parseInt(nroCliente);
                        personas.add(new Secundario(usuarioSistema, nombreCompleto, new Empresa(identificacionTributaria), new Pais(codigo), nroC, email, new Principal(nroDocumentoPrincipal), telefono));
                    }
                    rs.close();
                    listaSQL.add(sqlC);
                    break;
            }
        }
        catch(SQLException ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Búsqueda", ex.getLocalizedMessage());
            throw ex;
        }
        catch(Exception ex){
            registroConsola(this.usuarioSistema.getUsuarioSistema(), listaSQL, "Búsqueda", ex.getLocalizedMessage());
            throw ex;
        }
        return personas;
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
    public String getNuevoUsuarioSistema() throws Exception, SQLException{
        boolean seguir = true;
        ResultSet rs = null;
        String usuarioSistema = "-1";
        while (seguir) {
            usuarioSistema = Funciones.generarCadenaAleatoria(10);
            rs = this.database.consultar("SELECT * FROM Personas WHERE usuarioSistema='" + usuarioSistema + "' ");
            if(!rs.next()){
                seguir=false;
            }
        } 
        return usuarioSistema; //Retornamos un usuarioSistema AUTOGENERADO que NO existe en la base.
    }

    
/*Comportamiento*/

/*Getters y Setters*/

/*Getters y Setters*/
}
