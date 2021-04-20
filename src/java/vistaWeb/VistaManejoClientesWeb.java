package vistaWeb;

import Modelo.Funciones;
import Modelo.Pais;
import Modelo.Persona;
import Modelo.ProgramException;
import Modelo.TipoDocumento;
import controlador.ControladorManejoClientes;
import controlador.Interfaces.IVistaManejoClientes;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class VistaManejoClientesWeb implements IVistaManejoClientes{
    
    /*Estado*/
    private ControladorManejoClientes controlador;
    private String destino;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession sesion;
    private PrintWriter out;
    /*Estado*/

    /*Constructores*/
     public VistaManejoClientesWeb(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.response = response;
        this.out = response.getWriter();
        this.sesion = request.getSession();
        controlador = new ControladorManejoClientes(this);
    }
    /*Constructores*/
    
    /*Comportamiento*/
    public void procesarRequest(HttpServletRequest request, HttpServletResponse response){
        String accion = request.getParameter("accion");
        switch(accion){
            case "comboTiposDocumento":
                cargarTiposDocumento();
            break;
//            case "comboPaises":
//                cargarPaises();
//            break;           
//            case "generarUsuario":
//                this.generarUsuarioSistema();
//            break;
            case "formAltaCliente"://este es el del form pero ahora se tiene que suplantar por el del button
                altaCliente(request, response);
            break;
            case "mostrarTablaClientesInicio":
                cargarTablaClientesBajaInicio();
            break;
            case "buscarClientesBaja":
                mostrarClientesFiltradosTabla(request, response);
            break;
            case "borrarClientes":
                borrarClientes(request, response);
            break;
            
            case "formModificacionCliente":
            
            break;
        }
    }
    
    private void cargarTiposDocumento(){
        controlador.cargarTiposDocumento();
    }
    
    private void cargarPaises(){
        controlador.cargarPaises();
    }
    
    private void generarUsuarioSistema(){
        controlador.generarUsuarioSistema();
    }
    
    private void cargarTablaClientesBajaInicio() {
        controlador.cargarTablaClientesBajaInicio();
    }
    
    private void altaCliente(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        String tipoCliente = request.getParameter("tipoCliente");
        String nroDocumento = request.getParameter("nroDocumento");
        String nombreCompleto = request.getParameter("nombreCompleto");
        String codPais = request.getParameter("codPais");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String tipoDocumento = request.getParameter("tipoDocumento");
        
        if(tipoCliente.equals("Principal")){
            String chkServActivo = request.getParameter("servicioActivo");
            boolean chkActivo = false;
            if(chkServActivo.equals("Seleccionado")){//PRINCIPAL Y ACTIVO
                chkActivo = true;
            }
            controlador.altaPrincipal(nroDocumento, nombreCompleto, codPais, email, telefono ,chkActivo, tipoDocumento);
        }else if (tipoCliente.equals("Secundario")){
            String nroDocumentoPrincipal = request.getParameter("nroDocCliPrin");
            controlador.altaSecundario(nombreCompleto, codPais, email, telefono, nroDocumento, nroDocumentoPrincipal);
        }
    } 
    @Override
    public void mostrarPaises(ArrayList<Pais> paises) {
        try {
            String componente = Funciones.lista(false, "selPaisesCliente", paises, "changeItemSelected()");
            out.write(componente + "\n\n");
        } catch (ProgramException ex) {
            mensajeError("cliente_Alta.jsp","Error al mostrar los paises.");
        }
    }
    @Override
    public void mostrarTiposDocumento(ArrayList<TipoDocumento> tiposDocumento) {
        try{
            String componente = Funciones.lista(false, "selTiposDoc", tiposDocumento, "changeItemSelected()");
            out.write(componente + "\n\n");
        }catch(ProgramException ex){
            mensajeError("cliente_Alta.jsp","Error al mostrar los tipos de documento.");
        }
    }
    @Override
    public void mostrarUsuarioSistema(String usuario) {
        try{
        out.write("<input type='text' id='txtbxUsuarioSistemaClienteAlta' class='nb-input' disabled='true' value='"+usuario+"' name='txtbxUsuarioSistemaClienteAlta' />"+"\n\n");
        //METODO EN DESUSO
        }catch(Exception ex){
            mensajeError("cliente_Alta.jsp","Error al mostrar el usuario de sistema autogenerado.");
        }
    }

    @Override
    public void mensajeError(String nombreJSP, String texto) {
    destino = nombreJSP+"?msg=" + texto;
    try{
        response.sendRedirect(destino);
    }catch(IOException ex){
        System.out.println(texto);
    }        
    }

    @Override
    public void mensajeExito(String nombreJSP, String texto) {
        destino = nombreJSP+"?msg=" + texto;
        try{
            response.sendRedirect(destino);
        }catch(IOException ex){
            System.out.println(texto);
        }      
    }
    
    //muestro tabla de todos los clientes (principales y secundarios) para seleccionar y dar de baja
    @Override
    public void mostrarTablaClientesBajaInicio(ArrayList<Persona> principalesYSecundarios) {
        String componente = Funciones.tablaClientes(principalesYSecundarios, false);
        out.write(componente + "\n\n");
    }

    private void mostrarClientesFiltradosTabla(HttpServletRequest request, HttpServletResponse response) {
        
        this.request = request;
        this.response = response;
        
        int nroClienteBaja = -1;
        String emailClienteBaja = request.getParameter("emailCliente");
        String nombreCompletoClienteBaja = request.getParameter("nombreCompletoCliente");
        
        if(!request.getParameter("nroCliente").equals("")){//si se ingresó un nro cliente
            nroClienteBaja = Integer.parseInt(request.getParameter("nroCliente"));
            controlador.mostrarClientesFiltradosTabla(nroClienteBaja, emailClienteBaja, nombreCompletoClienteBaja);
        }else{//si no se ingresó un nro de cliente            
            controlador.mostrarClientesFiltradosTabla(nroClienteBaja, emailClienteBaja, nombreCompletoClienteBaja);
        }
    }

    private void borrarClientes(HttpServletRequest request, HttpServletResponse response) {
        String listaNombresDeUsuariosDeClientes[] = request.getParameterValues("listaClientes");
        this.request = request;
        this.response = response;
        
        controlador.borrarClientesSeleccionados(listaNombresDeUsuariosDeClientes);
        
    }

    @Override
    public void mensajeErrorBajaClientes(String mensajeErrorAlBorrar) {
        out.write(mensajeErrorAlBorrar);
    }

    @Override
    public void mostrarMensajeExitoClienteBorrado(String mensajeExitoAlBorrar) {
        out.write(mensajeExitoAlBorrar);
    }

    @Override
    public void mensajeNoSeleccionasteClientes(String mensajeNoSeleccion) {
        out.write(mensajeNoSeleccion);
    }

    @Override
    public void mensajeAltaClienteOK(String altaOk) {
        out.write(altaOk);
    }

    @Override
    public void mensajeAltaClienteError(String altaError) {
        out.write(altaError);
    }
/*Comportamiento*/
    
 /*Getters y Setters*/
    @Override
    public HttpSession getSession() {
        return sesion;
    }
/*Getters y Setters*/




    










    
}
