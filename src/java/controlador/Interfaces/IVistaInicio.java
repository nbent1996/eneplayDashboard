package controlador.Interfaces;

import Modelo.Operador;
import Modelo.Paquete;
import Modelo.TipoDispositivo;
import java.util.ArrayList;

public interface IVistaInicio {
    public void mensajeError(String nombreJSP, String texto);
    public void mensajeExito(String nombreJSP, String texto);
    public Operador getOperadorLogueado();
    public void mostrarEstadisticas(ArrayList<Integer> items);
    public void mostrarTablaPaquetes(ArrayList<Paquete> items);
    public void mostrarTablaTiposDispositivos(ArrayList<TipoDispositivo> items);
}
