package controlador.Interfaces;

import Modelo.Moneda;
import Modelo.Paquete;
import Modelo.TipoDispositivo;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

public interface IVistaManejoPaquetes {
    public void mensajeError(String nombreJSP, String texto);
    public void mensajeExito(String nombreJSP, String texto);
    public void generarTablaTiposDispositivos(String idTabla, ArrayList<TipoDispositivo> items);
    public void generarTablaPaquetes(String idTabla, ArrayList<Paquete> items, Moneda moneda);
    public void mensajeErrorBajaPaquetes(String mensajeErrorBaja);
    public void mostrarMensajeExitoPaqueteBorrado(String mensajeExitoBaja);
    public void mensajeNoSeleccionastePaquetes(String mensajeNoSelecPaquetes);
    public void errorEnValidacionesAltaPaquete(String mensajeErrorValidaciones);
    public void errorEnBaseDeDatosAltaPaquete(String mensajeErrorBaseDeDatos);
    public void exitoAlGuardarPaquete(String mensajeExitoAltaPaquete);
    public void errorLargoListasCantidadYDispositivosAltaPaquete(String mensajeErrorListasCantYTipos);
    public HttpSession getSession();

}
