package vistaWeb;

import Modelo.Categoria;
import Modelo.Dispositivo;
import Modelo.Funciones;
import Modelo.Principal;
import Modelo.ProgramException;
import Modelo.TipoDispositivo;
import controlador.ControladorManejoDispositivos;
import controlador.Interfaces.IVistaManejoDispositivos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class VistaManejoDispositivosWeb implements IVistaManejoDispositivos {

    /*Estado*/
    private ControladorManejoDispositivos controlador;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String destino;
    private PrintWriter out;
    private HttpSession sesion;

    /*Estado*/

 /*Constructores*/
    public VistaManejoDispositivosWeb(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.response = response;
        this.out = response.getWriter();
        this.sesion = request.getSession();
        controlador = new ControladorManejoDispositivos(this);
    }

    /*Constructores*/

 /*Comportamiento*/
    public void procesarRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accion = request.getParameter("accion");
        switch (accion) {
//            case "comboCategorias":
//                this.cargarCategorias();
//            break;
            case "generarPlanillaExcelDispositivos":
                generarTablaDispositivos(request, response);
                break;
            case "comboTiposDispositivo":
                cargarTiposDispositivos(response);
                break;
//            case "formAltaDispositivo":
//                 altaDispositivo(request, response);
//            break;
            case "generarTablaDispositivosBaja"://se ejecuta cuando se inicia el jsp de baja
                generarTablaDispositivos(request, response);
                break;
            case "buscarDispositivos"://se ejecuta cuando se busca por filtros en baja
                generarTablaDispositivos(request, response);
                break;
            case "borrarDispositivos":
                borrarDispositivos(request, response);
                break;
            case "buscarCliente":
                buscarClienteAsociarDispositivoAlta(request, response);
                break;
            case "altaDispositivo":
                altaDispositivo(request, response);
                break;

        }
    }
    private void buscarClienteAsociarDispositivoAlta(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        //this.response = response;
        String nroDocCliente = request.getParameter("nroDocumento");
        controlador.buscarClienteParaAsociarDispositivo(nroDocCliente);
    }
    private void generarTablaDispositivos(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        String nroSerie = request.getParameter("nroSerie");
        String estado = request.getParameter("estado");
        if (nroSerie == null) {
            nroSerie = "";
        }
        if (estado == null) {
            estado = "";
        }
        String filtro = controlador.getFiltroProcesado(nroSerie, estado);
        controlador.generarTablaDispositivos(filtro);
    }
    private void cargarTiposDispositivos(HttpServletResponse response) throws IOException {
        //String cat = request.getParameter("categoria");
        //this.out = response.getWriter();
        controlador.cargarTiposDispositivos();
    }
    private void altaDispositivo(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        //false ya que la session ya va a estar creada en el login
        Principal clientePrincipalSeleccionado = (Principal) request.getSession(false).getAttribute("clienteSeleccionado");//controlar que pasa si es null, o ver si hacerlo en el controlador
        //String nroDocumentoPrincipalAsociado = request.getParameter("spanClienteAsociado"); SE DEBE RESOLVER ESTO
        //String nroDocumentoPrincipalAsociado = "30654195"; // por ahora se hardcodea un cliente principal
        String nroSerie = request.getParameter("nroSerie");
        String estado = request.getParameter("estado");
        String tipoDispositivo = request.getParameter("tipoDispositivo");
        controlador.altaDispositivo(nroSerie, estado, tipoDispositivo, clientePrincipalSeleccionado);
    }
    @Override
    public void mostrarTiposDispositivos(ArrayList<TipoDispositivo> items) {
        try {
            String componente = Funciones.lista(false, "selTiposDispositivo", items, "changeItemSelected()");
            out.write(componente + "\n\n");
        } catch (ProgramException ex) {
            mensajeError("dispositivo_Alta.jsp", "Error en la carga de tipos de dispositivos.");
        }
    }
    @Override
    public void mostrarCategorias(ArrayList<Categoria> items) {
        try {
            String componente = Funciones.lista(false, "selCategoria", items, "changeItemSelected()");
            out.write(componente + "\n\n");
        } catch (ProgramException ex) {
            mensajeError("dispositivo_Alta.jsp", "Error en la carga de categorias.");
        }
    }
    @Override
    public void mensajeError(String nombreJSP, String texto) {
        destino = nombreJSP + "?msg=" + texto;
        try {
            response.sendRedirect(destino);
        } catch (IOException ex) {
            System.out.println(texto);
        }
    }
    @Override
    public void mensajeExito(String nombreJSP, String texto) {
        destino = nombreJSP + "?msg=" + texto;
        try {
            response.sendRedirect(destino);
        } catch (IOException ex) {
            System.out.println(texto);
        }
    }
    //    private void cargarCategorias(){
//        this.controlador.cargarCategorias();
//    }
    @Override
    public void generarTablaDispositivos(String idTabla, ArrayList<Dispositivo> items) {
        try {
            String componente = Funciones.tablaDispositivos(idTabla, items, false);
            out.write(componente + "\n\n");
        } catch (ProgramException ex) {
            mensajeError("dispositivo_BajaModificacion.jsp", "Error al generar la tabla de Dispositivos.");
        }
    }
    private void borrarDispositivos(HttpServletRequest request, HttpServletResponse response) {
        String listaNroSerieDispositivos[] = request.getParameterValues("listaDispositivos");
        this.request = request;
        this.response = response;

        controlador.borrarDispositivosSeleccionados(listaNroSerieDispositivos);

    }
    @Override
    public void mensajeErrorBajaDispositivos(String errorBorradoDisp) {
        out.write(errorBorradoDisp);
    }
    @Override
    public void mostrarMensajeExitoDispositivoBorrado(String exitoAlBorrarDisp) {
        out.write(exitoAlBorrarDisp);
    }
    @Override
    public void mensajeNoSeleccionasteDispositivos(String noSelecDisp) {
        out.write(noSelecDisp);
    }
    @Override
    public void noSeIngresoDocumentoCliente(String noIngresoNroDoc) {
        out.write(noIngresoNroDoc);
    }
    @Override
    public void mostrarClienteEncontradoAltaDisp(Principal nombreCompletoCliente) {
        //guardo al cliente en la session creada anteriormente para obtenerlo luego en el alta de dispositivo
        request.getSession(false).setAttribute("clienteSeleccionado", nombreCompletoCliente);
        out.write(nombreCompletoCliente.getNombreCompleto());
    }
    @Override
    public void errorBuscarCliente(String errorAlbuscar) {
        out.write(errorAlbuscar);
    }
    @Override
    public void mensajeAltaDispositivoOk(String mensajeOk) {
        //cada vez que se da de alta un dispositivo sea con error o correctamente se borra el cliente de la session, para que no lo tome en la siguiente alta
        request.getSession().removeAttribute("clienteSeleccionado");
        out.write(mensajeOk);
    }
    @Override
    public void mensajeErrorValidacionesAltaDispositivo(String mensajeErrorValidaciones) {
        //cada vez que se da de alta un dispositivo sea con error o correctamente se borra el cliente de la session, para que no lo tome en la siguiente alta
        request.getSession().removeAttribute("clienteSeleccionado");
        out.write(mensajeErrorValidaciones);
    }
    @Override
    public void mensajeErrorSqlAltaDispositivo(String mensajeErrorSql) {
        //cada vez que se da de alta un dispositivo sea con error o correctamente se borra el cliente de la session, para que no lo tome en la siguiente alta
        request.getSession().removeAttribute("clienteSeleccionado");
        out.write(mensajeErrorSql);
    }

    /*Comportamiento*/
 /*Getters y Setters*/
    @Override
    public HttpSession getSession() {
        return sesion;
    }
    /*Getters y Setters*/


    


    
    
}
