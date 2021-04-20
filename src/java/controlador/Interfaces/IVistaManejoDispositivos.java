package controlador.Interfaces;

import Modelo.Categoria;
import Modelo.Dispositivo;
import Modelo.Principal;
import Modelo.TipoDispositivo;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

public interface IVistaManejoDispositivos {
    public void mensajeError(String nombreJSP, String texto);
    public void mensajeExito(String nombreJSP, String texto);
    public void mostrarTiposDispositivos(ArrayList<TipoDispositivo> items);
    public void mostrarCategorias(ArrayList<Categoria> items);
    public void generarTablaDispositivos(String idTabla, ArrayList<Dispositivo> items);
    public void mensajeErrorBajaDispositivos(String errorBorradoDisp);
    public void mostrarMensajeExitoDispositivoBorrado(String exitoAlBorrarDisp);
    public void mensajeNoSeleccionasteDispositivos(String noSelecDisp);
    public void noSeIngresoDocumentoCliente(String noIngresoNroDoc);
    public void mostrarClienteEncontradoAltaDisp(Principal nombreCompletoCliente);
    public void errorBuscarCliente(String errorAlbuscar);
    public void mensajeAltaDispositivoOk(String mensajeOk);
    public void mensajeErrorValidacionesAltaDispositivo(String mensajeErrorValidaciones);
    public void mensajeErrorSqlAltaDispositivo(String mensajeErrorSql);
    public HttpSession getSession();

}
