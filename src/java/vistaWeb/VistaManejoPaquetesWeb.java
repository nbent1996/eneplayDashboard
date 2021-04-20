package vistaWeb;
import Modelo.Funciones;
import Modelo.Moneda;
import Modelo.Paquete;
import Modelo.ProgramException;
import Modelo.TipoDispositivo;
import controlador.ControladorManejoPaquetes;
import controlador.Interfaces.IVistaManejoPaquetes;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class VistaManejoPaquetesWeb implements IVistaManejoPaquetes{
    /*Estado*/
    private ControladorManejoPaquetes controlador;
    private String destino;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;   
    private HttpSession sesion;
    /*Estado*/
    
    /*Constructores*/
    public VistaManejoPaquetesWeb(HttpServletRequest request, HttpServletResponse response) throws IOException{
        this.response = response;
        this.out = response.getWriter();
        this.sesion = request.getSession();
        controlador = new ControladorManejoPaquetes(this);
    }
    /*Constructores*/
    
    /*Comportamiento*/
    public void procesarRequest(HttpServletRequest request, HttpServletResponse response) {
        String accion = request.getParameter("accion");
        switch(accion){
            case "generarTablaTiposDispositivos":
                generarTablaTiposDispositivos();
            break;
            case "generarTablaPaquetesBaja":
                cargarTablaPaquetesBajaInicio();
                //generarTablaPaquetes(request, response);
            break;
            case "formAltaPaquete":
                altaPaquete(request, response);
            break;
            case "altaPaqueteConDispositivos"://cuando doy de alta los paquetes con los dispositivos seleccionados de los check
                altaPaqueteConDispositivos(request, response);
            break;
            case "borrarPaquetes":
                 borrarPaquetes(request, response);
            break;
            case "buscarPaquetes":
                 generarTablaPaquetesFiltrados(request, response);
            break;
        }
    }
    
    private void altaPaqueteConDispositivos(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;  
        String listaIdDispositivos[] = request.getParameterValues("listaDispositivos"); // lista de id de dispositivos seleccionados
        String nombrePaquete = request.getParameter("nombrePaquete");
        String costoBrutoPaquete = request.getParameter("costoPaquete");
        String listaCantidades[] = request.getParameterValues("listaCantidades");//lista de las cantidades ingresadas en cada dispositivo seleccionado 
//        ArrayList<String> listaCantidadesAux = new ArrayList();//me guardo todas las cantidades
        //acá puedo sacar cada una de las cantidades ingresadas, en la listaIdDispositivos está cada id de los dispositivos que se seleccionaron, y el id de 
        //los input number de las cantidades es txtbxCant + idDispositivo
//        if(!listaIdDispositivos[0].equals("")){//se seleccionó al menos un dispositivo
//            String cadena = listaIdDispositivos[0].toString();
//            String[]cadenaConvertida = cadena.split(",");
//            for (String unId : cadenaConvertida) {
//                String value = "txtbxCant"+unId;//formo el nombre del value del input number de cantidad
//                String cantidadIngresada = request.getParameter(value);//agarro el valor del value armado. Puede ser null
//                listaCantidadesAux.add(cantidadIngresada);//agrego el valor del value a la lista, luego en esta lista en el controlador matchearla con la lista de ids pasados
//            }
//            
//            
//        }        
        controlador.altaPaqueteConDispositivos(listaIdDispositivos, listaCantidades, nombrePaquete, costoBrutoPaquete);
    }
    
    private void generarTablaTiposDispositivos(){
        this.controlador.generarTablaTiposDispositivos();
    }
    private void cargarTablaPaquetesBajaInicio(){
        controlador.cargarTablaPaquetesBajaInicio();
    } 
    private void generarTablaPaquetesFiltrados(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        String idPaqueteStr = request.getParameter("idPaquete");
        int idPaquete = -1;
        if (idPaqueteStr != null && !idPaqueteStr.equals("")) {//controla que se haya ingresado un idPaquete en el filtro para parsearlo
            idPaquete = Integer.parseInt(idPaqueteStr);
        }
        String nombre = request.getParameter("nombrePaquete");
        String costoA = request.getParameter("costoA");
        String costoB = request.getParameter("costoB");
        if (nombre == null) {
            nombre = "";
        }
        if (costoA == null) {
            costoA = "";
        }
        if (costoB == null) {
            costoB = "";
        }
        String filtro = this.controlador.getFiltroProcesado(idPaquete, nombre, costoA, costoB);
        this.controlador.generarTablaPaquetes(filtro);
    }
    private void altaPaquete(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
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
    @Override
    public void generarTablaTiposDispositivos(String idTabla, ArrayList<TipoDispositivo> items) {
        try{
            String componente = Funciones.tablaTiposDispositivosConCantidad(idTabla, items);
            out.write(componente + "\n\n");
        }catch(ProgramException ex){
            mensajeError("paquete_Alta.jsp","Error al generar la tabla de Paquetes de dispositivos.");
        }
    }
    @Override
    public void generarTablaPaquetes(String idTabla, ArrayList<Paquete> items, Moneda moneda){
        try{
            String componente = Funciones.tablaPaquetes(idTabla, items, moneda, false);
            out.write(componente + "\n\n");
        }catch(ProgramException ex){
            mensajeError("paquete_BajaModificacion.jsp","Error al generar la tabla de Paquetes de dispositivos.");
        }
    }
       private void borrarPaquetes(HttpServletRequest request, HttpServletResponse response) {
        String listaIdPaquetes[] = request.getParameterValues("listaPaquetes"); // lista de id de paquetes seleccionados
        this.request = request;
        this.response = response;
        
        controlador.borrarPaquetesSeleccionados(listaIdPaquetes);
    }

    @Override
    public void mensajeErrorBajaPaquetes(String mensajeErrorBaja) {
        out.write(mensajeErrorBaja);
    }

    @Override
    public void mostrarMensajeExitoPaqueteBorrado(String mensajeExitoBaja) {
        out.write(mensajeExitoBaja);
    }

    @Override
    public void mensajeNoSeleccionastePaquetes(String mensajeNoSelecPaquetes) {
        out.write(mensajeNoSelecPaquetes);
    }

    @Override
    public void errorEnValidacionesAltaPaquete(String mensajeErrorValidaciones) {
        out.write(mensajeErrorValidaciones);
    }

    @Override
    public void errorEnBaseDeDatosAltaPaquete(String mensajeErrorBaseDeDatos) {
        out.write(mensajeErrorBaseDeDatos);
    }

    @Override
    public void exitoAlGuardarPaquete(String mensajeExitoAltaPaquete) {
        out.write(mensajeExitoAltaPaquete);
    }

    @Override
    public void errorLargoListasCantidadYDispositivosAltaPaquete(String mensajeErrorListasCantYTipos) {
        out.write(mensajeErrorListasCantYTipos);
    }
/*Comportamiento*/
/*Getters y Setters*/
    @Override
    public HttpSession getSession() {
        return sesion;
    }
/*Getters y Setters*/

}
