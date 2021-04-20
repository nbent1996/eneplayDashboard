package Modelo;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;



public class Funciones {
    
    /*HTML*/
        public static String lista(boolean multiple,String id ,ArrayList opciones, String onChange) throws ProgramException{
        String lista = "<select " + " class='comboBox nb-input' onchange='"+onChange+"' name='"+id+"' " + (multiple?" multiple ":"") +  " id='" + id+ "'>";
        String optionId = "";
        String value ="";
        boolean primero = true;
        
        for(Object obj:opciones){
            
            if(obj instanceof Pais){
                Pais p = (Pais) obj;
                optionId = p.getCodigo();
                value = p.getNombre();        
            }
            if(obj instanceof TipoUsuario){
                TipoUsuario t = (TipoUsuario) obj;
                optionId = value = t.getNombre();
            }
            if(obj instanceof TipoDispositivo){
                TipoDispositivo tD = (TipoDispositivo) obj;
                optionId = tD.toString(1);
                value = tD.toString(3);
            }
            if(obj instanceof Categoria){
                Categoria c = (Categoria) obj;
                optionId = value = c.toString(1);
            }
            if(obj instanceof TipoDocumento){
                TipoDocumento tipoD = (TipoDocumento) obj;
                optionId = tipoD.toString(1); 
                value= tipoD.toString(2);
            }
            if(primero){
                lista+= "<option value='" + optionId + "' name= 'itemSeleccionado' >" + value + "</option>";
                primero = false;
            }else{
                lista+= "<option value='" + optionId + "'>" + value + "</option>";
            }
        }
        lista+="</select>";
        return lista;
    
    }
        public static String tablaPaquetes(String idTabla, ArrayList<Paquete> paquetes, Moneda moneda, boolean exportarPlanilla) throws ProgramException{
            String retorno = "";
            retorno+="<table id='"+idTabla+"' class='w3-table-all'>\n";
            /*Cabezales*/
            retorno+="<tr>\n";
                retorno+="<th>Id Paquete</th>\n";
                retorno+="<th>Nombre</th>\n";
                retorno+="<th>Costo bruto</th>\n";
                retorno+="<th>Dispositivos que contiene</th>\n";
                if(!exportarPlanilla){
                retorno+="<th>Seleccionado</th>\n";
                }
            retorno+="</tr>\n";
            /*Contenido*/
            for(Paquete p : paquetes){
                String listado = "";
                if(p.getListaTieneTP()!=null){
                listado="<ul>\n";
                for(TieneTP tp : p.getListaTieneTP()){
                    listado+="<li>" + tp.toString(1) + "</li>\n";
                }
                listado+="</ul>\n";
                }
                retorno+="<tr>\n";
                    retorno+="<td>"+p.getIdPaquete()+"</td>\n";
                    retorno+="<td>"+p.getNombre()+"</td>\n";
                    retorno+="<td>"+moneda.getSimbolo()+" "+p.getCostoBruto()+"</td>\n";
                    retorno+="<td>" +listado+ "</td>\n";
                    if(!exportarPlanilla){
                    retorno+="<td><input type='checkbox' class='w3-check' value='"+p.getIdPaquete()+"' name='"+p.getIdPaquete()+"'></td>\n";
                    }
                retorno+="</tr>\n";
            }
            retorno+="</table>";
            
            return retorno;
        }
        public static String tablaTiposDispositivosConCantidad(String idTabla, ArrayList<TipoDispositivo> items) throws ProgramException{
            String retorno = "";
            retorno+="<table id='"+idTabla+"' class='w3-table-all'>\n";
            /*Cabezales*/
            retorno+="<tr>\n";
                retorno+="<th>Id Tipo Dispositivo</th>\n";
                retorno+="<th>Modelo</th>\n";
                retorno+="<th>Nombre</th>\n";
                retorno+="<th>Tipo de comunicaci&oacute;n</th>\n";
                retorno+="<th>Categoria</th>\n";
                retorno+="<th>Cantidad</th>\n";
                retorno+="<th>Seleccionado</th>\n";
            retorno+="</tr>\n";
            boolean esImpar = true;
            /*Contenido*/
            for(TipoDispositivo td : items){
                retorno += "<tr>\n";
                retorno += "<td>" + td.getIdTipoDispositivo() + "</td>\n";
                retorno += "<td>" + td.getModelo() + "</td>\n";
                retorno += "<td>" + td.getNombre() + "</td>\n";
                retorno += "<td>" + td.getTipoComunicacion() + "</td>\n";
                retorno += "<td>" + td.getCategoria().getNombreCategoria() + "</td>\n";
                if (esImpar) {
                    retorno += "<td><input type='number' class='nb-table-input nb-inputCantidadTabla nb-input-white' id='txtbxCant" + td.getIdTipoDispositivo() + "' name='txtbxCant" + td.getIdTipoDispositivo() + "'></td>\n";
                } else {
                    retorno += "<td><input type='number' class='nb-table-input nb-inputCantidadTabla' id='txtbxCant" + td.getIdTipoDispositivo() + "' name='txtbxCant" + td.getIdTipoDispositivo() + "'></td>\n";
                }
                retorno += "<td><input type='checkbox' class='w3-check' value='" + td.getIdTipoDispositivo() + "' name='" + td.getIdTipoDispositivo() + "'></td>\n";
                retorno += "</tr>\n";
                esImpar = !esImpar;
            }
            retorno+="</table>";
            return retorno;
        }
        public static String tablaSuscripciones(String idTabla, ArrayList<Suscripcion> items, boolean exportarPlanilla) throws ProgramException{
            String retorno = "";
            String style="";
            if(exportarPlanilla){
                style=" border='1' ";
            }
            retorno+="<table id='"+idTabla+"' class='w3-table-all' "+style+">\n";
            /*Cabezales*/
            retorno+="<tr>\n";
                retorno+="<th>Id Suscripci&oacute;n</th>\n";
                retorno+="<th>Fecha de inicio</th>\n";
                retorno+="<th>Tiempo de contrato</th>\n";
                retorno+="<th>Fecha de Fin</th>\n";
                retorno+="<th>Activa</th>\n";
                retorno+="<th>Paquetes que contiene</th>\n";
                if(!exportarPlanilla){
                retorno+="<th>Seleccionado</th>\n";
                }
            retorno+="</tr>\n";
            /*Contenido*/
            for(Suscripcion s: items){
                String listado="";
                if(s.getListaPaquetes()!=null){
                listado = "<ul>\n";
                for(Paquete p : s.getListaPaquetes()){
                    listado+="<li>"+ p.toString(1)+"</li>";
                }
                listado+="</ul>\n";
                }
                String fechaFin = s.getFechaFin().getFechaAStr(2);
                if(fechaFin.equals("1/1/1970")){
                    fechaFin = "No definida";
                }
                retorno+="<tr>\n";
                    retorno+="<td>"+s.getIdSuscripcion()+"</td>\n";
                    retorno+="<td>"+s.getFechaInicio().getFechaAStr(2)+"</td>\n";
                    retorno+="<td>"+s.getTiempoContratoStr()+"</td>\n";
                    retorno+="<td>"+fechaFin+"</td>\n";
                    retorno+="<td>"+s.getActivaStr()+"</td>\n";
                    retorno+="<td>"+listado+"</td>\n";
                    if(!exportarPlanilla){
                    retorno+="<td><input type='checkbox' class='w3-check' value='"+s.getIdSuscripcion()+"' name='"+s.getIdSuscripcion()+"'></td>\n";
                    }
                    retorno+="</tr>\n";
                }
                retorno+="</table>";    
            return retorno;
        }
        public static String tablaDispositivos(String idTabla, ArrayList<Dispositivo> items, boolean exportarPlanilla) throws ProgramException{
            String retorno = "";
            String style = "";
            if(exportarPlanilla){
               style=" border='1' ";
            }
            retorno+="<table id='"+idTabla+"' class='w3-table-all' "+style+" >\n";
            /*Cabezales*/
            retorno+="<tr>\n";
                retorno+="<th>Nro de Serie</th>\n";
                retorno+="<th>Estado</th>\n";
                retorno+="<th>Tipo de Dispositivo</th>\n";
                retorno+="<th>Cliente asociado</th>\n";
                if(!exportarPlanilla){
                retorno+="<th>Seleccionar</th>\n";
                }
            retorno+="</tr>\n";
            /*Contenido*/
            for(Dispositivo d: items){
                    Cliente cli = d.getClienteAsociado();
                    retorno+="<tr>\n";
                    retorno+="<td>"+d.getNroSerie()+"</td>\n";
                    retorno+="<td>"+d.getEstado()+"</td>\n";
                    retorno+="<td>"+d.getTipoDispositivo().toString(3)+"</td>\n";
                    if(cli!=null && cli.getNombreCompleto()!=null){
                        retorno+="<td>"+d.getClienteAsociado().toString(1)+"</td>\n";
                    }else{
                        retorno+="<td>No asignado</td>\n";
                    }
                    if(!exportarPlanilla){
                    retorno+="<td><input type='checkbox' class='w3-check' value='"+d.getNroSerie()+"' name='"+d.getNroSerie()+"'></td>\n";
                    }
                    retorno+="</tr>\n";
                }
                retorno+="</table>";    
            return retorno;
        }
        public static String tablaUsuarios(ArrayList<Persona> opciones, String boton) {
        String tabla = "";       
        for (Persona obj : opciones) {
            tabla += "<tr><td>" + obj.getUsuarioSistema() + "</td>"
                    + "<td>" + obj.getNombreCompleto()+ "</td>"                    
                    + "<td><input type='checkbox' class='w3-check' value='" + obj.getUsuarioSistema() + "' name='" + obj.getUsuarioSistema() + "' </td></tr>";             
            
        }
        return tabla;
    }
        public static String tablaClientes(ArrayList<Persona> opciones, boolean exportarPlanilla) {
        String tabla = "";
        
        for (Persona obj : opciones) {
            tabla += "<tr><td>" + obj.retornarNroCliente() + "</td>" //poli
                    + "<td>" + obj.getNombreCompleto()+ "</td>" +
                      "<td>" + obj.retornarEmail()+ "</td>" + //poli
                      "<td>" + obj.retornarTipoCli()+ "</td>"; //poli  
                   
            if (!exportarPlanilla) {
                tabla += "<td><input type='checkbox' class='w3-check' value='" + obj.getUsuarioSistema() + "' name='" + obj.getUsuarioSistema() + "' </td></tr>";//fijarse acá, porque pongo el value de la PK de persona aunque esa PK para un cliente fue autogenerado y nunca se muestra         
            }
        }
        return tabla;
    }
        public static String tablaClientesCompleta(String idTabla, ArrayList<Persona> items, String tipo,boolean exportarPlanilla){
            String retorno = "";
            String style = "";
            if(exportarPlanilla){
                style=" border='1' ";
            }
            retorno+="<table id='"+idTabla+"' class='w3-table-all' "+style+">";
            if(tipo.equals("Principal")){
                /*Cabezales*/
                retorno += "<tr>\n";
                retorno += "<th>Usuario sistema</th>\n";
                retorno += "<th>Nombre Completo</th>\n";
                retorno += "<th>Nro Documento</th>\n";
                retorno += "<th>Tipo Documento</th>\n";
                retorno += "<th>Servicio activo</th>\n";
                retorno += "<th>Nro Cliente</th>\n";
                retorno += "<th>E-mail</th>\n";
                retorno += "<th>Telefono</th>\n";
                retorno += "</tr>\n";
                /*Contenido*/
                for(Persona per : items){
                    Principal p = (Principal)per;
                    retorno+="<tr>\n";
                    retorno+="<td>"+p.getUsuarioSistema()+"</td>\n";
                    retorno+="<td>"+p.getNombreCompleto()+"</td>\n";
                    retorno+="<td>"+p.getNroDocumento()+"</td>\n";
                    retorno+="<td>"+p.getTipoDocumento().getCodDocumento()+"</td>\n";
                    retorno+="<td>"+p.getServicioActivoStr()+"</td>\n";
                    retorno+="<td>"+p.getNroCliente()+"</td>\n";
                    retorno+="<td>"+p.getEmail()+"</td>\n";
                    retorno+="<td>"+p.getTelefono()+"</td>\n";
                    retorno+="</tr>\n";  
                }
            retorno+="</table>";

            }else if(tipo.equals("Secundario")){
                            /*Cabezales*/
                retorno += "<tr>\n";
                retorno += "<th>Usuario sistema</th>\n";
                retorno += "<th>Nombre Completo</th>\n";
                retorno += "<th>Documento titular</th>\n";
                retorno += "<th>Nro Cliente</th>\n";
                retorno += "<th>E-mail</th>\n";
                retorno += "<th>Telefono</th>\n";
                retorno += "</tr>\n";
                /*Contenido*/
                for(Persona per : items){
                    Secundario p = (Secundario)per;
                    retorno+="<tr>\n";
                    retorno+="<td>"+p.getUsuarioSistema()+"</td>\n";
                    retorno+="<td>"+p.getNombreCompleto()+"</td>\n";
                    retorno+="<td>"+p.getPrincipalAsociado().getNroDocumento()+"</td>\n";
                    retorno+="<td>"+p.getNroCliente()+"</td>\n";
                    retorno+="<td>"+p.getEmail()+"</td>\n";
                    retorno+="<td>"+p.getTelefono()+"</td>\n";
                    retorno+="</tr>\n";  
                }
            retorno+="</table>";
            }    
            return retorno;
        }
        public static String tablaTiposDispositivos(String idTabla, ArrayList<TipoDispositivo> items, boolean exportarPlanilla) throws ProgramException{
            String retorno = "";
            String style="";
            if(exportarPlanilla){
                style=" border='1' ";
            }
            retorno+="<table id='"+idTabla+"' class='w3-table-all' "+style+">\n";
            /*Cabezales*/
            retorno+="<tr>\n";
                retorno+="<th>Id Tipo Dispositivo</th>\n";
                retorno+="<th>Modelo</th>\n";
                retorno+="<th>Nombre</th>\n";
                retorno+="<th>Tipo de comunicaci&oacute;n</th>\n";
                retorno+="<th>Categoria</th>\n";
                if(!exportarPlanilla){
                    retorno+="<th>Seleccionado</th>\n";                    
                }
            retorno+="</tr>\n";
            /*Contenido*/
            for(TipoDispositivo td : items){
                retorno += "<tr>\n";
                retorno += "<td>" + td.getIdTipoDispositivo() + "</td>\n";
                retorno += "<td>" + td.getModelo() + "</td>\n";
                retorno += "<td>" + td.getNombre() + "</td>\n";
                retorno += "<td>" + td.getTipoComunicacion() + "</td>\n";
                retorno += "<td>" + td.getCategoria().getNombreCategoria() + "</td>\n";
                if (!exportarPlanilla) {
                    retorno += "<td><input type='checkbox' class='w3-check' value='" + td.getIdTipoDispositivo() + "' name='" + td.getIdTipoDispositivo() + "'></td>\n";
                }
                retorno += "</tr>\n";
            }
            retorno+="</table>";
            return retorno;                          
        }
        
        
    /*HTML*/   
        
    public static String sanitizarCampo(String campo){
        if(campo == null || campo.equals("")){
            return campo;
        }
        campo = campo.replace(";", "");
        campo = campo.replace(":", "");
        campo = campo.replace("'", "");
        campo = campo.replace("\"", "");
        campo = campo.replace("INSERT", "");
        campo = campo.replace("insert", "");
        campo = campo.replace("SELECT", "");
        campo = campo.replace("select", "");
        campo = campo.replace("UPDATE", "");
        campo = campo.replace("update", "");
        campo = campo.replace("DELETE", "");
        campo = campo.replace("delete", "");
        campo = campo.replace("DROP", "");
        campo = campo.replace("drop", "");
        campo = campo.replace("CREATE", "");
        campo = campo.replace("create", "");
        return campo;
    }      
    public static boolean isNumeric(String cadena) {

        boolean resultado = false;
        if(cadena.equals("")){
           return true;
        }
        
        try {
            for(char c: cadena.toCharArray()){
                Integer.parseInt(c+"");
            }
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
            
        }

        return resultado;
    }
    public static String generarCadenaAleatoria(int longitud) {
        //Banco de caracteres
        String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        // La cadena en donde iremos agregando un carácter aleatorio
        String cadena = "";
        for (int x = 0; x < longitud; x++) {
            int indiceAleatorio = ThreadLocalRandom.current().nextInt(0, banco.length()); //ThreadLocalRandom.current().nextInt() nos dá un número aleatorio en el rango puesto, el limite superior es exclusivo asi que se suma 1
            char caracterAleatorio = banco.charAt(indiceAleatorio);
            cadena += caracterAleatorio;
        }
        return cadena;
    }
    public static String FirstLetterUpperCase(String campo){
        if(campo == null || campo.equals("")){
            return campo;
        }
        return campo.substring(0, 1).toUpperCase() + campo.substring(1).toLowerCase();
    }
    public static byte[] getArrayBytes(String ruta) throws FileNotFoundException, IOException{
        BufferedImage img = ImageIO.read(new File(ruta));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = ruta.split("\\.").length;
        String ext = ruta.split("\\.")[len - 1];
        ImageIO.write(img, ext, baos);
        return baos.toByteArray();
    }
    public static String formatearTelefono(String campo, String prefijo){
        if(campo==null || campo.equals(""))
                return campo;
        if(campo.substring(0, 1).equals("0")){
            campo = campo.substring(1);
        }
        return prefijo+campo;
    }
    public static byte[] getBytesFromOutputStream(OutputStream f) throws FileNotFoundException, IOException{
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        FileInputStream fis = new FileInputStream("fichero.pdf");
        int read;
        while((read = fis.read(buffer)) != -1){
            os.write(buffer, 0, read);
        }
        fis.close();
        os.close();
        return os.toByteArray();
    }
    
    
}
