package controlador.Interfaces;

import Modelo.Pais;
import Modelo.Persona;
import Modelo.TipoUsuario;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
public interface IVistaManejoUsuarios {
    
    
    public void mensajeError(String nombreJSP, String texto);
    public void mensajeExito(String nombreJSP, String texto);
    public void mostrarTiposUsuario(ArrayList<TipoUsuario> tiposUsuarios);
    public void mostrarPaises(ArrayList<Pais> paises);
    /*--*/
    public void mostrarMensajeExitoPersonaBorrada(String exitoAlBorrarUsuario);
    public void mensajeNoSeleccionasteUsuarios(String noHayUsuariosSeleccionados);
    public void mostrarTablaConUsuariosABorrar(ArrayList<Persona> listaUsuarios);
    public void mensajeErrorBajaUsuarios(String mensajeError);
    public void mostrarTablaUsuariosBaja(ArrayList<Persona> usuarios);
    public void mensajeAltaUsuarioOK(String altaOK);
    public void mensajeAltaUsuarioError(String altaError);
    public HttpSession getSession();

    
}
