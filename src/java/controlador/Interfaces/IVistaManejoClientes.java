package controlador.Interfaces;

import Modelo.Pais;
import Modelo.Persona;
import Modelo.TipoDocumento;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

public interface IVistaManejoClientes {
    public void mensajeError(String nombreJSP, String texto);
    public void mensajeExito(String nombreJSP, String texto);
    public void mostrarPaises(ArrayList<Pais> paises);
    public void mostrarTiposDocumento(ArrayList<TipoDocumento> tiposDocumento);
    public void mostrarUsuarioSistema(String usuario);
    public void mostrarTablaClientesBajaInicio(ArrayList<Persona> principalesYSecundarios);
    public void mensajeErrorBajaClientes(String mensajeErrorAlBorrar);
    public void mostrarMensajeExitoClienteBorrado(String mensajeExitoAlBorrar);
    public void mensajeNoSeleccionasteClientes(String mensajeNoSeleccion);
    public void mensajeAltaClienteOK(String altaOk);
    public void mensajeAltaClienteError(String altaError);
    public HttpSession getSession();

}
