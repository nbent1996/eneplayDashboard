package controlador.Interfaces;

import Modelo.Moneda;
import Modelo.Paquete;
import Modelo.Principal;
import Modelo.Suscripcion;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

public interface IVistaManejoSuscripciones {
    public void mensajeError(String nombreJSP, String texto);
    public void mensajeExito(String nombreJSP, String texto);
    public void generarTablaPaquetes(String idTabla, ArrayList<Paquete> items, Moneda moneda);
    public void generarTablaSuscripciones(String idTabla, ArrayList<Suscripcion> items);
    public void mensajeErrorBajaSuscripciones(String mensajeErrorAlBorrar);
    public void mostrarMensajeExitoSuscripcionBorrada(String mensajeExitoAlBorrar);
    public void mensajeNoSeleccionasteSuscripciones(String mensajeNoSeleccion);
    public void errorBuscarCliente(String errorBuscarCliente);
    public void mostrarClienteEncontradoAltaSuscripcion(Principal clientePrincipalEncontrado);
    public void noSeIngresoDocumentoCliente(String noIngresoDocumentoDeCliente);
    public void errorSqlPaquetesSeleccionadosAltaSuscripcion(String errorPaquetesSeleccionados);
    public void exitoAlCrearSuscripcion(String exitoAltaSuscripcion);
    public void errorValidacionesDeSuscripcion(String errorValidarSuscripcion);
    public void errorSqlInsertarSuscripcion(String errorInsertarSuscripcion);
    public void mensajeSeleccionarPaquetesAltaSuscripcion(String debesSeleccionarPaquetes);
    public void mensajeSeleccionarClienteAltaSuscripcion(String debesSeleccionarCliente);
    public HttpSession getSession();
}
