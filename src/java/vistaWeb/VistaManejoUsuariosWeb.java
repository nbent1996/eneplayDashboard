package vistaWeb;

import Modelo.Funciones;
import Modelo.Pais;
import Modelo.Persona;
import Modelo.ProgramException;
import Modelo.TipoUsuario;
import controlador.ControladorManejoUsuarios;
import controlador.Interfaces.IVistaManejoUsuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class VistaManejoUsuariosWeb implements IVistaManejoUsuarios {

    /*Estado*/
    private ControladorManejoUsuarios controlador;
    private String destino;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter out;
    private HttpSession sesion;

    /*Estado*/
 /*Constructores*/
    public VistaManejoUsuariosWeb(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.response = response;
        this.out = response.getWriter();
        this.sesion = request.getSession();
        controlador = new ControladorManejoUsuarios(this);
    }

    /*Constructores*/

 /*Comportamiento*/
    public void procesarRequest(HttpServletRequest request, HttpServletResponse response) {
        String accion = request.getParameter("accion");
        switch (accion) {

//            case "comboPaises":
//                cargarPaises();
//            break;
            case "comboTipos":
                cargarTiposUsuario();
                break;
            case "formAltaUsuarios":
                altaUsuario(request, response);
                break;
            case "formModificacion":
                modificacionUsuario(request, response);
                break;
            case "borrarUsuarios":
                borrarUsuarios(request, response);
                break;
            case "buscarUsuariosBaja":
                mostrarUsuariosFiltradosTabla(request, response);
                break;
            case "mostrarTablaUsuariosInicio":
                cargarTablaUsuariosBajaInicio();
                break;
        }
    }

    private void cargarPaises() {
        controlador.cargarPaises();
    }

    private void cargarTiposUsuario() {
        controlador.cargarTiposUsuario();
    }

    private void cargarTablaUsuariosBajaInicio() {
        controlador.cargarTablaUsuariosBajaInicio();
    }

    private void altaUsuario(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        String usuarioAltaUsr = request.getParameter("usuarioAlta");
        String nombreCompletoAltaUsr = request.getParameter("nombreCompletoAlta");
        String codPaisAltaUsr = request.getParameter("codPaisAlta");
        String tipoUsuarioAltaUsr = request.getParameter("tipoUsuarioAlta");
        String generoUsuarioAltaUsr = request.getParameter("generoUsuarioAlta");
        controlador.altaUsuario(usuarioAltaUsr, nombreCompletoAltaUsr, codPaisAltaUsr, generoUsuarioAltaUsr, tipoUsuarioAltaUsr);
    }

    private void borrarUsuarios(HttpServletRequest request, HttpServletResponse response) {
        String listaNombresDeUsuarios[] = request.getParameterValues("listaUsuarios"); // lista de nombres de usuarios (PK) (sacados del value de los checkboxes)
        this.request = request;
        this.response = response;
        controlador.borrarUsuariosSeleccionados(listaNombresDeUsuarios);
    }

    private void mostrarUsuariosFiltradosTabla(HttpServletRequest request, HttpServletResponse response) {
        String nombreUsuarioBaja = request.getParameter("nombreUsuario");
        String nombreCompletoUsuarioBaja = request.getParameter("nombreCompleto");
        this.request = request;
        this.response = response;
        controlador.mostrarUsuariosFiltradosTabla(nombreUsuarioBaja, nombreCompletoUsuarioBaja);
    }

    private void modificacionUsuario(HttpServletRequest request, HttpServletResponse response) {
        String usuarioModUsr = request.getParameter("usuarioMod");
        String nombreCompletoModUsr = request.getParameter("nombreCompletoMod");
        String nombreEmpresaModUsr = request.getParameter("nombreEmpresaMod");
        String nombrePaisModUsr = request.getParameter("nombrePaisMod");
        String passwordModUsr = request.getParameter("passwordMod");
        controlador.modificarUsuario(usuarioModUsr, nombreCompletoModUsr, nombreEmpresaModUsr, nombrePaisModUsr, passwordModUsr);
        //(pueden ser nulos ya que no es necesario llenar todos los campos (se puede modificar un campo solo))   
    }

    @Override
    public void mostrarTiposUsuario(ArrayList<TipoUsuario> tiposUsuarios) {
        String componente;
        try {
            componente = Funciones.lista(false, "selTiposUsuarios", tiposUsuarios, "changeItemSelected()");
            out.write(componente + "\n\n");
        } catch (ProgramException ex) {
            mensajeError("usuario_Alta.jsp", "Error al mostrar los tipos de usuario.");
        }

    }

    @Override
    public void mostrarPaises(ArrayList<Pais> paises) {
        try {
            String componente = Funciones.lista(false, "selPaisesUsuario", paises, "changeItemSelected()");
            out.write(componente + "\n\n");
        } catch (ProgramException ex) {
            mensajeError("usuario_Alta.jsp", "Error al mostrar los paises.");
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
    public void mensajeExito(String nombreJSP, String texto) {//aca tiene que ir al mensaje modal en vez de desplegar el mensaje en la variable msg, para eso hay que hacer todo por ajax y no por form
        destino = nombreJSP + "?msg=" + texto;
        try {
            response.sendRedirect(destino);
        } catch (IOException ex) {
            System.out.println(texto);
        }
    }

    /*--*/

    //muestro tabla de los usuarios encontrados al filtrar, con checkbox para seleccionar y borrar
    @Override
    public void mostrarTablaConUsuariosABorrar(ArrayList<Persona> listaUsuarios) {
        String componente = Funciones.tablaUsuarios(listaUsuarios, "chkBajaUsuario");
        out.write(componente + "\n\n");
    }

    //mensaje de exito al borrar las personas seleccionadas de los checkboxes
    @Override
    public void mostrarMensajeExitoPersonaBorrada(String exitoAlBorrarUsuario) {
        out.write(exitoAlBorrarUsuario);
    }

    //mensaje de error de cuando no se seleccionó ningun usuario en los checkboxes
    @Override
    public void mensajeNoSeleccionasteUsuarios(String noHayUsuariosSeleccionados) {
        out.write(noHayUsuariosSeleccionados);
    }

    //mensaje de error amigable cuando da una excepción el borrado de usuarios
    @Override
    public void mensajeErrorBajaUsuarios(String mensajeError) {
        out.write(mensajeError);
    }
    //muestro tabla de todos los usuarios para seleccionar y dar de baja

    @Override
    public void mostrarTablaUsuariosBaja(ArrayList<Persona> usuarios) {
        String componente = Funciones.tablaUsuarios(usuarios, "chkBajaUsuario"); //muestro tabla de los usuarios encontrados con checkbox para seleccionar y borrar
        out.write(componente + "\n\n");
    }

    @Override
    public void mensajeAltaUsuarioOK(String altaOK) {
        out.write(altaOK);
    }

    @Override
    public void mensajeAltaUsuarioError(String altaError) {
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
